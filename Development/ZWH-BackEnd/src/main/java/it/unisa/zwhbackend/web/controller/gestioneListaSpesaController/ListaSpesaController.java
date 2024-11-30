package it.unisa.zwhbackend.web.controller.gestioneListaSpesaController;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import it.unisa.zwhbackend.model.entity.Prodotto;
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
   * Endpoint per generare una lista della spesa per un utente.
   *
   * @param fridgeItems Prodotti nel frigo.
   * @param pantryItems Prodotti nella dispensa.
   * @param dailyPlanItems Prodotti nel piano giornaliero.
   * @param userId ID dell'utente per cui generare la lista.
   * @return Lista della spesa generata.
   */
  @PostMapping("/generate")
  public ResponseEntity<ListaSpesa> generateShoppingList(
      @RequestBody List<Prodotto> fridgeItems,
      @RequestParam List<Prodotto> pantryItems,
      @RequestParam List<Prodotto> dailyPlanItems,
      @RequestParam Long userId) {
    // Simula il recupero dell'utente per semplicità
    Utente utente = new Utente();
    utente.setId(userId);

    ListaSpesa shoppingList =
        shoppingListService.generateShoppingList(utente, fridgeItems, pantryItems, dailyPlanItems);
    return ResponseEntity.status(HttpStatus.CREATED).body(shoppingList);
  }

  /**
   * Endpoint per ottenere tutte le liste della spesa di un utente.
   *
   * @param userId ID dell'utente di cui si vogliono recuperare le liste della spesa.
   * @return Lista di tutte le liste della spesa associate all'utente.
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
