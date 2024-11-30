package it.unisa.zwhbackend.web.controller.gestioneListaSpesaController;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.ShoppingListRequest;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.service.GLS.ListaSpesaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per gestire le operazioni sulle liste della spesa.
 *
 * <p>Il controller fornisce gli endpoint per generare una lista della spesa per un utente in base
 * ai suoi articoli disponibili, e per ottenere le liste della spesa di un utente.
 *
 * <p>Il DTO {@link ShoppingListRequest} è utilizzato per raccogliere le informazioni relative ai
 * prodotti disponibili in frigo, dispensa e nel piano giornaliero per generare la lista della
 * spesa personalizzata.
 *
 * @author Giuseppe Russo
 */
@RestController
@RequestMapping("/api/lista-spesa")
public class ListaSpesaController {

  private final ListaSpesaService shoppingListService;

  /**
   * Costruttore per inizializzare il servizio per la gestione delle liste della spesa.
   *
   * @param shoppingListService Servizio per la gestione delle liste della spesa.
   */
  @Autowired
  public ListaSpesaController(ListaSpesaService shoppingListService) {
    this.shoppingListService = shoppingListService;
  }

  /**
   * Genera una lista della spesa personalizzata per l'utente in base ai suoi articoli disponibili.
   *
   * <p>Questo endpoint accetta una richiesta contenente gli articoli presenti in frigo, dispensa e
   * nel piano giornaliero dell'utente, e restituisce la lista della spesa risultante dalla combinazione
   * di questi elementi, filtrando in base alle preferenze alimentari dell'utente e alle scadenze dei prodotti.
   *
   * @param shoppingListRequest DTO contenente le informazioni sugli articoli di frigo, dispensa e piano
   *                            giornaliero.
   * @param userId ID dell'utente per cui generare la lista della spesa.
   * @return La lista della spesa generata.
   */
  @PostMapping("/generate")
  public ResponseEntity<ListaSpesa> generateShoppingList(
          @RequestBody ShoppingListRequest shoppingListRequest,
          @RequestParam Long userId) {
    // Simula il recupero dell'utente per semplicità
    Utente utente = new Utente();
    utente.setId(userId);

    // Ottieni le liste di prodotti
    List<Prodotto> fridgeItems = shoppingListRequest.getFridgeItems();
    List<Prodotto> pantryItems = shoppingListRequest.getPantryItems();
    List<Prodotto> dailyPlanItems = shoppingListRequest.getDailyPlanItems();

    // Genera la lista della spesa
    ListaSpesa shoppingList =
            shoppingListService.generateShoppingList(utente, fridgeItems, pantryItems, dailyPlanItems);

    return ResponseEntity.status(HttpStatus.CREATED).body(shoppingList);
  }

  /**
   * Ottiene tutte le liste della spesa associate a un utente.
   *
   * <p>Questo endpoint recupera tutte le liste della spesa esistenti per un utente specificato,
   * in base al suo ID. Se non sono presenti liste della spesa, viene restituito un errore 404.
   *
   * @param userId ID dell'utente di cui si vogliono recuperare le liste della spesa.
   * @return Lista delle liste della spesa dell'utente.
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<List<ListaSpesa>> getShoppingListsByUserId(@PathVariable Long userId) {
    List<ListaSpesa> shoppingLists = shoppingListService.getShoppingListsByUserId(userId);

    // Verifica se ci sono liste della spesa
    if (shoppingLists.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(shoppingLists);
  }
}