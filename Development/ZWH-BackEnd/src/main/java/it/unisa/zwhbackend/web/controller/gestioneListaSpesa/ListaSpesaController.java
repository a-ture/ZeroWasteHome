package it.unisa.zwhbackend.web.controller.gestioneListaSpesa;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import it.unisa.zwhbackend.model.entity.Utente;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import it.unisa.zwhbackend.service.gestioneListaSpesa.ListaSpesaService;
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
   * Costruttore per inizializzare il controller per la gestione delle liste della spesa.
   *
   * @param shoppingListService Servizio per la gestione delle liste della spesa.
   * @param utenteRepository Repository per l'entità Utente, utilizzato per accedere ai dati degli
   *     utenti.
   */
  @Autowired
  public ListaSpesaController(
      ListaSpesaService shoppingListService, UtenteRepository utenteRepository) {
    this.shoppingListService = shoppingListService;
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
  public ResponseEntity<ListaSpesa> generateShoppingList(String email) {

    // Ottiene l'utente
    Utente utente = utenteRepository.findByEmail(email);

    // Genera la lista della spesa
    ListaSpesa shoppingList = shoppingListService.generateShoppingList(utente);

    return ResponseEntity.status(HttpStatus.CREATED).body(shoppingList);
  }

  /**
   * Ottiene la lista della spesa associata a un utente.
   *
   * <p>Questo endpoint recupera la lista della spesa esistente per un utente specificato, in base
   * al suo ID. Se non è presente una lista della spesa, viene restituito un errore 404.
   *
   * @param userId ID dell'utente di cui si vuole recuperare la lista della spesa.
   * @return Lista della spesa dell'utente.
   */
  @GetMapping("/user/{userId}")
  public ResponseEntity<ListaSpesa> getShoppingListByUserId(@PathVariable String userId) {
    Optional<ListaSpesa> shoppingList = shoppingListService.getShoppingListByUserId(userId);

    // Verifica se la lista della spesa esiste
    if (shoppingList.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    return ResponseEntity.ok(shoppingList.get());
  }
}
