package it.unisa.zwhbackend.web.controller.gestioneProdotti;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.ProdottoRequestDTO;
import it.unisa.zwhbackend.service.gestioneProdotti.ProdottoService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST per la gestione della ricerca dei prodotti per nome.
 *
 * <p>Espone endpoint per cercare prodotti nel sistema in base a un criterio parziale sul nome.
 * Fornisce risposte standard HTTP, includendo messaggi di errore in caso di input non valido o di
 * altri problemi durante l'elaborazione.
 *
 * <p>Gli endpoint sono progettati per essere utilizzati dagli utenti autenticati, sfruttando
 * l'email dell'utente autenticato come parametro implicito.
 *
 * @author Alessandra Trotta
 */
@RestController
@RequestMapping("/api/utente/prodotti")
public class RicercaProdottiController {

  private final ProdottoService prodottoService;

  /**
   * Costruttore della classe. Inietta il servizio per la gestione della logica di ricerca dei
   * prodotti.
   *
   * @param prodottoService il servizio per la gestione della logica dei prodotti
   */
  public RicercaProdottiController(ProdottoService prodottoService) {
    this.prodottoService = prodottoService;
  }

  /**
   * Endpoint per la ricerca dei prodotti per nome.
   *
   * <p>Consente di cercare prodotti nel sistema in base a una stringa parziale del nome. L'email
   * dell'utente autenticato viene automaticamente recuperata dal contesto di sicurezza. Il metodo
   * gestisce:
   *
   * <ul>
   *   <li>Validazione dell'input
   *   <li>Ricerca dei prodotti tramite il servizio {@link ProdottoService}
   *   <li>Gestione di errori comuni (es. input vuoto, prodotti non trovati, errori imprevisti)
   * </ul>
   *
   * @param name il nome (o parte del nome) del prodotto da cercare
   * @return una {@link ResponseEntity} contenente:
   *     <ul>
   *       <li>HTTP 200 con la lista dei prodotti trovati in caso di successo
   *       <li>HTTP 400 con un messaggio di errore se il campo di ricerca è vuoto
   *       <li>HTTP 404 con un messaggio di errore se nessun prodotto viene trovato
   *       <li>HTTP 500 con un messaggio di errore in caso di errore imprevisto
   *     </ul>
   */
  @Operation(summary = "Ricerca prodotti per nome")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Prodotti trovati con successo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Prodotto.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Il campo di ricerca è vuoto",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            example =
                                "{ \"messaggio\": \"Il campo di ricerca non può essere vuoto\" }"))),
        @ApiResponse(
            responseCode = "404",
            description = "Nessun prodotto trovato",
            content =
                @Content(
                    mediaType = "application/json",
                    schema =
                        @Schema(
                            example =
                                "{ \"messaggio\": \"Nessun prodotto corrispondente trovato\" }"))),
        @ApiResponse(
            responseCode = "500",
            description = "Errore interno del server",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore imprevisto\" }")))
      })
  @GetMapping("/ricerca")
  public ResponseEntity<?> ricercaProdottiPerNome(@RequestParam("name") String name) {
    String email = SecurityContextHolder.getContext().getAuthentication().getName();
    try {
      // Verifica se il campo di ricerca è vuoto
      if (name == null || name.trim().isEmpty()) {
        return ResponseEntity.badRequest()
            .body("{ \"messaggio\": \"Il campo di ricerca non può essere vuoto\" }");
      }

      // Effettua la ricerca
      List<ProdottoRequestDTO> prodotti = prodottoService.RicercaPerNome(email, name);

      // Controlla se la lista dei prodotti è vuota
      if (prodotti.isEmpty()) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body("{ \"messaggio\": \"Nessun prodotto corrispondente trovato\" }");
      }

      // Restituisce i prodotti trovati
      return ResponseEntity.ok(prodotti);

    } catch (Exception e) {
      // Gestisce eventuali errori imprevisti
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body("{ \"messaggio\": \"Errore imprevisto\" }");
    }
  }
}
