package it.unisa.zwhbackend.web.controller.gestioneAmministrativa;

import it.unisa.zwhbackend.service.gestioneAmministrativa.SegnalazioneRicettaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per la gestione delle segnalazioni.
 *
 * <p>Questo controller espone un'API REST che consente di risolvere una segnalazione tramite
 * un'operazione di aggiornamento parziale (PATCH). La logica di risoluzione delle segnalazioni
 * viene delegata al servizio {@link SegnalazioneRicettaService}.
 *
 * @author Giovanni Balzano
 */
@RestController
@RequestMapping("/api/segnalazioni")
public class SegnalazioniRicetteController {

  // Variabile per memorizzare il servizio che gestisce le segnalazioni
  private final SegnalazioneRicettaService segnalazioneRicettaService;

  /**
   * Costruttore che inietta il servizio per la gestione delle segnalazioni delle ricette.
   *
   * <p>Il servizio {@link SegnalazioneRicettaService} contiene la logica di business per risolvere
   * le segnalazioni delle ricette.
   *
   * @param segnalazioneRicettaService il servizio per la gestione delle segnalazioni delle ricette.
   */
  public SegnalazioniRicetteController(SegnalazioneRicettaService segnalazioneRicettaService) {
    this.segnalazioneRicettaService = segnalazioneRicettaService;
  }

  /**
   * Endpoint per risolvere una segnalazione di una ricetta.
   *
   * <p>Quando questo endpoint viene chiamato, il servizio {@link SegnalazioneRicettaService}
   * aggiorna lo stato della segnalazione con l'ID fornito e la segnalazione viene risolta.
   *
   * @param id l'ID della segnalazione da risolvere
   * @param gestore_id l'ID del gestore che risolve la segnalazione
   * @return una {@link ResponseEntity} con un messaggio di successo o errore
   */
  @PatchMapping("/{id}")
  public ResponseEntity<String> risolviSegnalazione(
      @PathVariable Long id, Long gestore_id, String motivoBlocco) {
    // Chiama il servizio per risolvere la segnalazione con l'ID fornito
    String response = segnalazioneRicettaService.risolviSegnalazione(id, gestore_id, motivoBlocco);
    // Verifica se la risposta contiene la parola "successo"
    if (response.contains("successo")) {
      // Se la segnalazione è stata risolta con successo, restituisce una risposta HTTP 200 OK con
      // il messaggio
      return ResponseEntity.ok(response);
    }
    // Se c'è stato un errore, restituisce una risposta HTTP 400 Bad Request con il messaggio di
    // errore
    return ResponseEntity.badRequest().body(response);
  }
}
