package it.unisa.zwhbackend.web.controller.GCCR;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.Ricetta;
import it.unisa.zwhbackend.service.GCCR.GestioneRicetteService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST per la gestione delle ricette.
 *
 * <p>Fornisce endpoint per creare, ottenere, modificare ed eliminare ricette. Usa il servizio
 * {@link GestioneRicetteService} per eseguire le operazioni.
 *
 * @author Anna Tagliamonte
 */
@RestController
@RequestMapping("/api/ricette")
public class GestioneRicetteController {

  private final GestioneRicetteService gestioneRicetteService;

  /**
   * Costruttore della classe.
   *
   * <p>Inietta automaticamente l'istanza del servizio {@link GestioneRicetteService}.
   *
   * @param gestioneRicetteService il servizio per la gestione delle ricette
   */
  @Autowired
  public GestioneRicetteController(GestioneRicetteService gestioneRicetteService) {
    this.gestioneRicetteService = gestioneRicetteService;
  }

  /**
   * Endpoint per aggiungere una nuova ricetta.
   *
   * @param ricetta la ricetta da aggiungere
   * @return la ricetta aggiunta con un codice di risposta 201 Created
   */
  @Operation(summary = "Aggiunge una nuova ricetta")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Ricetta aggiunta con successo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Ricetta.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Errore di validazione",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore di validazione\" }"))),
        @ApiResponse(
            responseCode = "500",
            description = "Errore interno del server",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(example = "{ \"messaggio\": \"Errore imprevisto\" }")))
      })
  @PostMapping
  public ResponseEntity<Ricetta> aggiungiRicetta(@RequestBody @Valid Ricetta ricetta) {
    try {
      Ricetta nuovaRicetta = gestioneRicetteService.aggiungiRicetta(ricetta);
      return ResponseEntity.status(HttpStatus.CREATED).body(nuovaRicetta);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null); // In caso di errore di validazione
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(null); // Per errori imprevisti
    }
  }

  /**
   * Endpoint per ottenere tutte le ricette.
   *
   * @return un elenco di tutte le ricette presenti nel sistema
   */
  @Operation(summary = "Restituisce tutte le ricette")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Elenco delle ricette recuperato con successo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Ricetta.class))),
        @ApiResponse(
            responseCode = "500",
            description = "Errore interno del server",
            content =
                @Content(schema = @Schema(example = "{ \"messaggio\": \"Errore imprevisto\" }")))
      })
  @GetMapping
  public ResponseEntity<List<Ricetta>> getAllRicette() {
    try {
      List<Ricetta> ricette = gestioneRicetteService.getAllRicette();
      return ResponseEntity.ok(ricette);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * Endpoint per ottenere i dettagli di una ricetta tramite il suo ID.
   *
   * @param id l'ID della ricetta da recuperare
   * @return i dettagli della ricetta se esistente
   */
  @Operation(summary = "Recupera una ricetta tramite ID")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ricetta trovata con successo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Ricetta.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Ricetta non trovata",
            content =
                @Content(schema = @Schema(example = "{ \"messaggio\": \"Ricetta non trovata\" }")))
      })
  @GetMapping("/{id}")
  public ResponseEntity<Ricetta> getRicettaById(@PathVariable Long id) {
    return gestioneRicetteService
        .getRicettaById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
  }

  /**
   * Endpoint per modificare una ricetta esistente.
   *
   * @param id l'ID della ricetta da modificare
   * @param ricetta i nuovi dettagli della ricetta
   * @return la ricetta aggiornata se l'operazione è avvenuta con successo
   */
  @Operation(summary = "Modifica una ricetta esistente")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Ricetta aggiornata con successo",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Ricetta.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Ricetta non trovata",
            content =
                @Content(schema = @Schema(example = "{ \"messaggio\": \"Ricetta non trovata\" }"))),
        @ApiResponse(
            responseCode = "400",
            description = "Errore di validazione",
            content =
                @Content(
                    schema = @Schema(example = "{ \"messaggio\": \"Errore di validazione\" }")))
      })
  @PutMapping("/{id}")
  public ResponseEntity<Ricetta> modificaRicetta(
      @PathVariable Long id, @RequestBody @Valid Ricetta ricetta) {
    try {
      Ricetta ricettaAggiornata = gestioneRicetteService.aggiornaRicetta(id, ricetta);
      return ResponseEntity.ok(ricettaAggiornata);
    } catch (IllegalArgumentException e) {
      return ResponseEntity.badRequest().body(null);
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
  }

  /**
   * Endpoint per eliminare una ricetta tramite il suo ID.
   *
   * @param id l'ID della ricetta da eliminare
   * @return una risposta con stato 204 No Content se l'eliminazione è avvenuta con successo
   */
  @Operation(summary = "Elimina una ricetta tramite ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Ricetta eliminata con successo"),
        @ApiResponse(
            responseCode = "404",
            description = "Ricetta non trovata",
            content =
                @Content(schema = @Schema(example = "{ \"messaggio\": \"Ricetta non trovata\" }")))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminaRicetta(@PathVariable Long id) {
    try {
      gestioneRicetteService.eliminaRicetta(id);
      return ResponseEntity.noContent().build();
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
