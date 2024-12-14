package it.unisa.zwhbackend.web.controller.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.SegnalazioneRicetta;
import it.unisa.zwhbackend.service.gestioneAmministrativa.AmministrazioneService;
import it.unisa.zwhbackend.service.gestioneAmministrativa.SegnalazioneRicettaService;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per la gestione delle segnalazioni relative alle ricette. Fornisce endpoint per
 * ottenere tutte le segnalazioni e per risolvere una segnalazione specifica.
 *
 * @author Giovanni Balzano
 */
@RestController
@RequestMapping("/api/utente/gestoreSegnalazioni")
public class SegnalazioneRicettaController {

  // Variabile per memorizzare il servizio che gestisce le segnalazioni
  private final AmministrazioneService amministrazioneService;
  private final SegnalazioneRicettaService segnalazioneRicettaService;

  /**
   * Costruttore del controller per l'inizializzazione del servizio delle segnalazioni.
   *
   * <p>Il servizio {@link AmministrazioneService} contiene la logica di business per risolvere le
   * segnalazioni delle ricette.
   *
   * @param amministrazioneService il servizio che implementa la facade per le funzionalità della
   *     gestione amministrativa
   * @param segnalazioneRicettaService il servizio per la gestione delle segnalazioni di ricette
   */
  public SegnalazioneRicettaController(
      AmministrazioneService amministrazioneService,
      SegnalazioneRicettaService segnalazioneRicettaService) {
    this.amministrazioneService = amministrazioneService;
    this.segnalazioneRicettaService = segnalazioneRicettaService;
  }

  /**
   * Endpoint per ottenere tutte le segnalazioni di ricette.
   *
   * @return una ResponseEntity contenente la lista delle segnalazioni e lo stato HTTP OK (200)
   *     oppure lo stato HTTP INTERNAL_SERVER_ERROR (500) in caso di errore
   */
  @GetMapping
  public ResponseEntity<List<SegnalazioneRicetta>> getSegnalazioni() {
    try {
      List<SegnalazioneRicetta> segnalazioni = segnalazioneRicettaService.getAllSegnalazioni();
      return ResponseEntity.ok(segnalazioni);
    } catch (Exception e) {
      System.err.println("Errore durante il recupero delle segnalazioni: " + e.getMessage());
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }

  /**
   * Endpoint per risolvere una segnalazione di ricetta specifica.
   *
   * @param id l'identificativo univoco della segnalazione da risolvere
   * @param body una mappa contenente il motivo del blocco come chiave "motivoBlocco"
   * @return una ResponseEntity contenente un messaggio di successo o di errore, e il relativo stato
   *     HTTP (200 per successo, 400 per errore di input)
   */
  @PatchMapping("/{id}")
  public ResponseEntity<String> risolviSegnalazione(
      @PathVariable Long id, @RequestBody Map<String, String> body) {
    // Chiama il servizio per risolvere la segnalazione con l'ID fornito
    String motivoBlocco = body.get("motivoBlocco");
    if (motivoBlocco == null || motivoBlocco.trim().isEmpty()) {
      return ResponseEntity.badRequest().body("Motivo del blocco è obbligatorio.");
    }

    // Recupera l'email del gestore autenticato
    String email = SecurityContextHolder.getContext().getAuthentication().getName();

    // Passa l'email al servizio
    String response = amministrazioneService.risolviSegnalazioneRicetta(id, email, motivoBlocco);
    if (response.contains("successo")) {
      return ResponseEntity.ok(response);
    }
    return ResponseEntity.badRequest().body(response);
  }
}
