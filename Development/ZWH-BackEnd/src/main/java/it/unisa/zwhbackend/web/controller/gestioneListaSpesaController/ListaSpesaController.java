package it.unisa.zwhbackend.web.controller.gestioneListaSpesaController;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import it.unisa.zwhbackend.service.GLS.ListaSpesaService;
import java.util.List;
import java.util.Optional;

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
  private final UtenteRepository utenteRepository;

  /**
   * Costruttore per inizializzare il servizio per la gestione delle liste della spesa.
   *
   * @param shoppingListService Servizio per la gestione delle liste della spesa.
   */
  @Autowired
  public ListaSpesaController(ListaSpesaService shoppingListService, UtenteRepository utenteRepository) {
    this.shoppingListService = shoppingListService;
    this.utenteRepository = utenteRepository;
  }

  /**
   * Genera una lista della spesa personalizzata per l'utente in base ai suoi articoli disponibili.
   *
   * <p>Questo endpoint accetta una richiesta contenente gli articoli presenti in frigo, dispensa e
   * nel piano giornaliero dell'utente, e restituisce la lista della spesa risultante dalla combinazione
   * di questi elementi, filtrando in base alle preferenze alimentari dell'utente e alle scadenze dei prodotti.
   *
   * @return La lista della spesa generata.
   */
  @PostMapping("/generate")
  public ResponseEntity<ListaSpesa> generateShoppingList(long userId) {

    // Ottiene l'utente
    Optional<Utente> utente = utenteRepository.findById(userId);

    // Genera la lista della spesa
    ListaSpesa shoppingList =
            shoppingListService.generateShoppingList(utente.orElse(null));

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