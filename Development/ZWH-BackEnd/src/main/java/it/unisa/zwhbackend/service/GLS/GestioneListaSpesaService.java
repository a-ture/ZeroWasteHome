package it.unisa.zwhbackend.service.GLS;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.ListaSpesaRepository;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
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

  /**
   * Costruttore che inizializza il repository per l'accesso ai dati delle liste della spesa.
   *
   * @param shoppingListRepository Repository per l'entità ListaSpesa.
   */
  @Autowired
  public GestioneListaSpesaService(ListaSpesaRepository shoppingListRepository) {
    this.ListaSpesaRepository = shoppingListRepository;
  }

  /**
   * Crea e salva una nuova lista della spesa.
   *
   * @param utente Utente per cui si sta creando la lista della spesa.
   * @param products Lista dei prodotti da aggiungere alla lista della spesa.
   * @return La lista della spesa creata.
   */
  @Override
  public ListaSpesa createShoppingList(Utente utente, List<Prodotto> products) {
    ListaSpesa shoppingList = new ListaSpesa();
    shoppingList.setUtente(utente);
    shoppingList.setProducts(products);
    return ListaSpesaRepository.save(shoppingList);
  }

  /**
   * Ottiene tutte le liste della spesa associate a un utente.
   *
   * @param userId ID dell'utente di cui si vogliono recuperare le liste della spesa.
   * @return Lista di tutte le liste della spesa associate all'utente.
   */
  @Override
  public List<ListaSpesa> getShoppingListsByUserId(Long userId) {
    return ListaSpesaRepository.findByUtenteId(userId);
  }

  /**
   * Genera una lista della spesa per un utente in base agli articoli disponibili nel frigo,
   ella dispensa e nel piano giornaliero.
   *
   * @param utente Utente per cui si sta generando la lista.
   * @param fridgeItems Prodotti nel frigo.
   * @param pantryItems Prodotti nella dispensa.
   * @param dailyPlanItems Prodotti nel piano giornaliero.
   * @return La lista della spesa generata.
   */
  @Override
  public ListaSpesa generateShoppingList(
          Utente utente,
          List<Prodotto> fridgeItems,
          List<Prodotto> pantryItems,
          List<Prodotto> dailyPlanItems) {
    // Data corrente
    LocalDate today = LocalDate.now();

    // Recupera le preferenze alimentari dell'utente (categoria)
    List<String> userPreferences = utente.getCategoria();

    // Combina i prodotti di frigo e dispensa in un unico Set
    Set<String> availableItemNames = fridgeItems.stream()
            .filter(product -> isCompatibleWithPreferences(product.getCategoria(), userPreferences))
            .map(Prodotto::getName)
            .collect(Collectors.toSet());

    availableItemNames.addAll(
            pantryItems.stream()
                    .filter(product -> isCompatibleWithPreferences(product.getCategoria(), userPreferences))
                    .map(Prodotto::getName)
                    .collect(Collectors.toSet())
    );

    // Filtra gli item del piano giornaliero che non sono disponibili
    List<Prodotto> shoppingListProducts = dailyPlanItems.stream()
            .filter(item -> !availableItemNames.contains(item.getName()))
            .collect(Collectors.toList());

    // Aggiungi prodotti in scadenza (nei prossimi 2 giorni) compatibili con le preferenze
    fridgeItems.stream()
            .filter(product -> isExpiring(product.getScadenza(), today) && isCompatibleWithPreferences(product.getCategoria(), userPreferences))
            .forEach(shoppingListProducts::add);

    pantryItems.stream()
            .filter(product -> isExpiring(product.getScadenza(), today) && isCompatibleWithPreferences(product.getCategoria(), userPreferences))
            .forEach(shoppingListProducts::add);

    // Crea e salva la lista della spesa
    return createShoppingList(utente, shoppingListProducts);
  }

  /**
   * Verifica se un prodotto è compatibile con le preferenze alimentari dell'utente.
   *
   * @param productCategories Categorie del prodotto.
   * @param userPreferences Preferenze alimentari dell'utente.
   * @return {@code true} se almeno una categoria del prodotto è compatibile con le preferenze,
   *         {@code false} altrimenti.
   */
  private boolean isCompatibleWithPreferences(List<String> productCategories, List<String> userPreferences) {
    if (userPreferences == null || userPreferences.isEmpty()) {
      return true; // Nessuna preferenza impostata, tutto è compatibile
    }
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
  private boolean isExpiring(Date expirationDate, LocalDate today) {
    if (expirationDate == null) {
      return false; // Se non c'è una data di scadenza, non considerarlo in scadenza
    }
    LocalDate productDate = expirationDate.toLocalDate();
    return !productDate.isBefore(today) && !productDate.isAfter(today.plusDays(2));
  }
}
