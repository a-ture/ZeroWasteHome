package it.unisa.zwhbackend.web.controller.gestioneProdotto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.service.gestioneProdotto.ricercaProdotti.GestioneRicercaProdottiService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST per la gestione della ricerca dei prodotti per nome.
 *
 * <p>Espone endpoint per cercare prodotti nel sistema in base a un criterio parziale sul nome.
 * Restituisce risposte standard HTTP con eventuali messaggi di errore.
 *
 * @author Alessandra Trotta
 */
@RestController
@RequestMapping("/api/prodotti")
public class RicercaProdottiController {

  private final GestioneRicercaProdottiService ricercaProdottiService;

  /**
   * Costruttore della classe. Inietta il servizio per la gestione della logica di ricerca dei
   * prodotti.
   *
   * @param ricercaProdottiService il servizio per la ricerca dei prodotti
   */
  public RicercaProdottiController(GestioneRicercaProdottiService ricercaProdottiService) {
    this.ricercaProdottiService = ricercaProdottiService;
  }

  /**
   * Metodo GET per la ricerca dei prodotti per nome.
   *
   * <p>Questo metodo consente di cercare prodotti nel sistema in base a un nome fornito come
   * parametro. Valida l'input dell'utente, gestisce eventuali errori e restituisce i prodotti
   * trovati oppure messaggi di errore standard.
   *
   * @param name il nome (o parte del nome) del prodotto da cercare
   * @return una risposta HTTP contenente la lista di prodotti trovati o un messaggio di errore
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
    try {
      // Verifica se il campo di ricerca è vuoto
      if (name == null || name.trim().isEmpty()) {
        return ResponseEntity.badRequest()
            .body("{ \"messaggio\": \"Il campo di ricerca non può essere vuoto\" }");
      }

      // Effettua la ricerca
      List<Prodotto> prodotti = ricercaProdottiService.RicercaPerNome(name);

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
