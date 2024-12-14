package it.unisa.zwhbackend.web.controller.gestioneListaSpesa;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.ListaSpesaRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import it.unisa.zwhbackend.service.gestioneListaSpesa.ListaSpesaService;
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
@CrossOrigin(origins = "http://localhost:4200")
public class ListaSpesaController {

  private final ListaSpesaService shoppingListService;
  // private final ListaSpesaRepository shoppingListRepository;
  private final UtenteRepository utenteRepository;

  /**
   * Costruttore per inizializzare il controller per la gestione delle liste della spesa.
   *
   * @param shoppingListService Servizio per la gestione delle liste della spesa.
   * @param utenteRepository Repository per l'entità Utente, utilizzato per accedere ai dati degli
   *     utenti.
   * @param shoppingListRepository repository per Entità lista della Spesa
   */
  @Autowired
  public ListaSpesaController(
      ListaSpesaService shoppingListService,
      ListaSpesaRepository shoppingListRepository,
      UtenteRepository utenteRepository) {
    this.shoppingListService = shoppingListService;
    // this.shoppingListRepository = shoppingListRepository;
    this.utenteRepository = utenteRepository;
  }

  /**
   * Genera una lista della spesa personalizzata per l'utente in base ai suoi articoli disponibili.
   *
   * <p>Questo endpoint accetta una richiesta contenente l'email dell'utente e utilizza le
   * informazioni sugli articoli presenti in frigo, dispensa e nel piano giornaliero per generare
   * una lista della spesa risultante. La lista è filtrata in base alle preferenze alimentari
   * dell'utente e alle scadenze dei prodotti.
   *
   * @param email L'email dell'utente per cui generare la lista della spesa.
   * @return La lista della spesa generata come {@code ResponseEntity}.
   */
  @PostMapping("/generate")
  public ResponseEntity<ListaSpesa> generateShoppingList(@RequestBody String email) {
    Utente utente = utenteRepository.findByEmail(email);

    if (utente == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    ListaSpesa shoppingList = shoppingListService.generateShoppingList(utente);
    return ResponseEntity.status(HttpStatus.CREATED).body(shoppingList);
  }

  /* Metodo non necessario ma funzionante
   *
   * Ottiene la lista della spesa associata a un utente.
   *
   * <p>Questo endpoint recupera la lista della spesa esistente per un utente specificato, in base
   * al suo ID. Se non è presente una lista della spesa, viene restituito un errore 404.
   *
   * @param userEmail Email dell'utente di cui si vuole recuperare la lista della spesa.
   * @return Lista della spesa dell'utente.
   */

  /*
  @GetMapping("/user/{userId}")
  public ResponseEntity<ListaSpesa> getShoppingListByUserEmail(@PathVariable String userEmail) {
    Optional<ListaSpesa> shoppingList = shoppingListRepository.findByUtenteEmail(userEmail);

    // Verifica se la lista della spesa esiste
    if (shoppingList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(shoppingList.get());
  }
   */
}
