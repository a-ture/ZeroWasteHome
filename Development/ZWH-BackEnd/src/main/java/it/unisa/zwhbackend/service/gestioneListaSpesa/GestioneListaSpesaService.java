package it.unisa.zwhbackend.service.gestioneListaSpesa;

import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.enums.CategoriaAlimentare;
import it.unisa.zwhbackend.model.repository.ListaSpesaRepository;
import it.unisa.zwhbackend.model.repository.PossiedeInDispensaRepository;
import it.unisa.zwhbackend.model.repository.PossiedeInFrigoRepository;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio per la gestione delle liste della spesa.
 *
 * <p>Questo servizio fornisce le operazioni per creare, ottenere e generare una lista della spesa
 * per un utente, considerando le sue preferenze alimentari e i prodotti in frigo e dispensa.
 *
 * @author Giuseppe Russo
 */
@Service
public class GestioneListaSpesaService implements ListaSpesaService {

  private final ListaSpesaRepository ListaSpesaRepository;
  private final PossiedeInFrigoRepository PossiedeInFrigoRepository;
  private final PossiedeInDispensaRepository PossiedeInDispensaRepository;
  private final ProdottoRepository ProdottoRepository;

  /**
   * Costruttore che inizializza il servizio per la gestione delle liste della spesa, configurando i
   * repository necessari per l'accesso ai dati.
   *
   * @param shoppingListRepository Repository per l'entità ListaSpesa.
   * @param possiedeInFrigoRepository Repository per l'entità PossiedeInFrigo, che gestisce i dati
   *     dei prodotti presenti in frigo.
   * @param possiedeInDispensaRepository Repository per l'entità PossiedeInDispensa, che gestisce i
   *     dati dei prodotti presenti in dispensa.
   */
  @Autowired
  public GestioneListaSpesaService(
      ListaSpesaRepository shoppingListRepository,
      PossiedeInFrigoRepository possiedeInFrigoRepository,
      PossiedeInDispensaRepository possiedeInDispensaRepository,
      ProdottoRepository prodottoRepository) {
    this.ListaSpesaRepository = shoppingListRepository;
    this.PossiedeInFrigoRepository = possiedeInFrigoRepository;
    this.PossiedeInDispensaRepository = possiedeInDispensaRepository;
    this.ProdottoRepository = prodottoRepository;
  }

  /**
   * Crea e salva una nuova lista della spesa.
   *
   * @param utente Utente per cui si sta creando la lista della spesa.
   * @param products Lista dei prodotti da aggiungere alla lista della spesa.
   * @return La lista della spesa creata.
   */
  // @Transactional
  @Override
  public ListaSpesa createShoppingList(Utente utente, List<Prodotto> products) {
    // Salva ogni prodotto che non è ancora persistito
    List<Prodotto> persistedProducts =
        products.stream()
            .map(
                product -> {
                  if (product.getId() == null) {
                    return ProdottoRepository.save(product); // Salva il prodotto nel database
                  }
                  return product; // Ritorna il prodotto già persistito
                })
            .collect(Collectors.toList());

    if (utente.getListaSpesa() != null) {
      ListaSpesa oldShoppingList = utente.getListaSpesa();
      utente.setListaSpesa(null);
      oldShoppingList.setUtente(null);
      ListaSpesaRepository.save(oldShoppingList);
      ListaSpesaRepository.delete(oldShoppingList);
    }

    ListaSpesa shoppingList = new ListaSpesa();
    shoppingList.setUtente(utente);
    shoppingList.setProducts(persistedProducts); // Usa i prodotti persistiti
    shoppingList.setDataCreazione(Date.valueOf(LocalDate.now()));

    utente.setListaSpesa(shoppingList);

    if (shoppingList == null) {
      shoppingList = new ListaSpesa();
    }
    ListaSpesaRepository.save(shoppingList);
    return shoppingList;
  }

  /**
   * Genera una lista della spesa per un utente in base agli articoli disponibili nel frigo, ella
   * dispensa e nel piano giornaliero.
   *
   * @param utente Utente per cui si sta generando la lista.
   * @return La lista della spesa generata.
   */
  @Override
  public ListaSpesa generateShoppingList(Utente utente) {
    // Data corrente
    LocalDate today = LocalDate.now();

    // Recupera le preferenze alimentari dell'utente (categoria)
    List<String> userPreferences = utente.getCategoria();

    // Recupera i prodotti nel frigo e nella dispensa dell'utente
    List<PossiedeInFrigo> fridgeItems =
        PossiedeInFrigoRepository.findByUtenteEmail(utente.getEmail());
    List<PossiedeInDispensa> pantryItems = PossiedeInDispensaRepository.findByUtente(utente);

    // Crea una lista statica di prodotti per il piano giornaliero (simulazione)
    List<Prodotto> dailyPlanItems =
        createStaticDailyPlanItems(); // Metodo per creare la lista statica di prodotti

    // Combina i prodotti di frigo e dispensa in un unico Set di nomi di prodotti disponibili
    Set<String> availableItemNames = new HashSet<>();

    // Aggiungi prodotti in frigo compatibili con le preferenze
    availableItemNames.addAll(
        fridgeItems.stream()
            .filter(
                item ->
                    isCompatibleWithPreferences(item.getProdotto().getCategoria(), userPreferences))
            .map(item -> item.getProdotto().getName())
            .collect(Collectors.toSet()));

    // Aggiungi prodotti in dispensa compatibili con le preferenze
    availableItemNames.addAll(
        pantryItems.stream()
            .filter(
                item ->
                    isCompatibleWithPreferences(item.getProdotto().getCategoria(), userPreferences))
            .map(item -> item.getProdotto().getName())
            .collect(Collectors.toSet()));

    // Filtra gli item del piano giornaliero che non sono disponibili
    List<Prodotto> shoppingListProducts =
        dailyPlanItems.stream()
            .filter(item -> !availableItemNames.contains(item.getName()))
            .collect(Collectors.toList());

    // Aggiungi prodotti in scadenza (nei prossimi 2 giorni) compatibili con le preferenze
    List<Prodotto> finalShoppingListProducts = shoppingListProducts;
    fridgeItems.stream()
        .filter(
            item ->
                isExpiring(item.getDataScadenza(), today)
                    && isCompatibleWithPreferences(
                        item.getProdotto().getCategoria(), userPreferences))
        .forEach(item -> finalShoppingListProducts.add(item.getProdotto()));

    pantryItems.stream()
        .filter(
            item ->
                isExpiring(item.getDataScadenza(), today)
                    && isCompatibleWithPreferences(
                        item.getProdotto().getCategoria(), userPreferences))
        .forEach(item -> finalShoppingListProducts.add(item.getProdotto()));

    // Rimuovi i prodotti che hanno più di una occorrenza e una di queste non è in scadenza
    shoppingListProducts =
        shoppingListProducts.stream()
            .collect(Collectors.groupingBy(Prodotto::getName))
            .entrySet()
            .stream()
            .flatMap(
                entry -> {
                  List<Prodotto> productList = entry.getValue();
                  if (productList.size() > 1) {
                    // Verifica se esistono due occorrenze con scadenze diverse
                    boolean hasExpiringProduct =
                        productList.stream()
                            .anyMatch(product -> isExpiring(getExpirationDate(product), today));
                    boolean hasNonExpiringProduct =
                        productList.stream()
                            .anyMatch(product -> !isExpiring(getExpirationDate(product), today));
                    if (hasExpiringProduct && hasNonExpiringProduct) {
                      // Se c'è un prodotto in scadenza e uno non in scadenza, escludilo dalla lista
                      return productList.stream()
                          .filter(product -> isExpiring(getExpirationDate(product), today));
                    }
                  }
                  return productList.stream(); // Mantieni solo una occorrenza del prodotto
                })
            .collect(Collectors.toList());

    // Crea e salva la lista della spesa
    return createShoppingList(utente, shoppingListProducts);
  }

  private String getExpirationDate(Prodotto prodotto) {
    // Trova la data di scadenza per il prodotto, assumendo che la relazione fra prodotto e utente
    // esista
    return prodotto.getUtentiPossessori().stream()
        .filter(possiede -> possiede.getProdotto().equals(prodotto))
        .map(PossiedeInFrigo::getDataScadenza)
        .findFirst()
        .orElse(null); // Se non trovata, restituisci null
  }

  /**
   * Crea una lista statica di prodotti per il piano giornaliero (simulazione). Questo metodo
   * restituisce una lista predefinita di prodotti.
   *
   * @return Lista di prodotti del piano giornaliero.
   */
  List<Prodotto> createStaticDailyPlanItems() {
    // Esempio di prodotti simulati per il piano giornaliero
    List<Prodotto> dailyPlanItems = new ArrayList<>();
    dailyPlanItems.add(
        new Prodotto(
            3,
            "Uova biologiche 6 uova",
            "8002790048554",
            Arrays.asList(CategoriaAlimentare.VEGANO.toString())));
    dailyPlanItems.add(
        new Prodotto(
            2,
            "Marinated Tofu 160 g",
            "5013683305466",
            Arrays.asList(CategoriaAlimentare.VEGANO.toString())));

    // Aggiungere altri prodotti se necessario
    return dailyPlanItems;
  }

  /**
   * Verifica se un prodotto è compatibile con le preferenze alimentari dell'utente.
   *
   * @param productCategories Categorie del prodotto.
   * @param userPreferences Preferenze alimentari dell'utente.
   * @return {@code true} se almeno una categoria del prodotto è compatibile con le preferenze,
   *     {@code false} altrimenti.
   */
  boolean isCompatibleWithPreferences(
      List<String> productCategories, List<String> userPreferences) {
    if (userPreferences == null || userPreferences.isEmpty()) {
      return true; // Nessuna preferenza impostata, tutto è compatibile
    }

    //    if(userPreferences != null && productCategories.isEmpty()) {
    //
    //    }
    // Controlla se almeno una delle categorie del prodotto è compatibile con le preferenze
    return productCategories.stream().anyMatch(userPreferences::contains);
  }

  /**
   * Verifica se un prodotto è in scadenza nei prossimi 2 giorni.
   *
   * @param expirationDate Data di scadenza del prodotto.
   * @param today Data corrente.
   * @return {@code true} se il prodotto scade nei prossimi 2 giorni, {@code false} altrimenti.
   */
  boolean isExpiring(String expirationDate, LocalDate today) {
    if (expirationDate == null || expirationDate.isEmpty()) {
      return false; // Se non c'è una data di scadenza, non considerarlo in scadenza
    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate productDate = LocalDate.parse(expirationDate, formatter);
    return !productDate.isBefore(today) && !productDate.isAfter(today.plusDays(2));
  }
}
