package it.unisa.zwhbackend.web.controller.gestioneAmministrativa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import it.unisa.zwhbackend.model.repository.GestorePagamentoRepository;
import it.unisa.zwhbackend.service.gestioneAmministrativa.AmministrazioneService;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per gestire le segnalazioni di pagamento.
 *
 * <p>Fornisce gli endpoint REST per:
 *
 * <ul>
 *   <li>Risoluzione di una segnalazione di pagamento.
 *   <li>Presa in carico di una segnalazione di pagamento.
 *   <li>Recupero di tutte le segnalazioni di pagamento.
 * </ul>
 *
 * <p>Questo controller utilizza i servizi per la gestione amministrativa e interagisce con il
 * repository {@link GestorePagamentoRepository}.
 *
 * <p>Autenticazione basata su Spring Security per determinare l'utente autenticato.
 *
 * @author Benito Farina
 */
@RestController
@RequestMapping("/api/gestore/segnalazioniPagamento")
public class SegnalazionePagamentoController {

  private final AmministrazioneService amministrazioneService;
  private final GestorePagamentoRepository gestorePagamentoRepository;

  /**
   * Costruttore del controller.
   *
   * @param amministrazioneService Servizio per la gestione amministrativa.
   * @param gestorePagamentoRepository Repository per accedere ai dati dei gestori di pagamento.
   */
  public SegnalazionePagamentoController(
      AmministrazioneService amministrazioneService,
      GestorePagamentoRepository gestorePagamentoRepository) {
    this.amministrazioneService = amministrazioneService;
    this.gestorePagamentoRepository = gestorePagamentoRepository;
  }

  /**
   * Risolve una segnalazione di pagamento.
   *
   * <p>Consente a un gestore autenticato di risolvere una segnalazione di pagamento. L'endpoint
   * aggiorna lo stato della segnalazione e registra i dettagli della risoluzione.
   *
   * @param idSegnalazione ID della segnalazione da risolvere.
   * @param dettagliRisoluzione Dettagli relativi alla risoluzione della segnalazione.
   * @return Una {@link ResponseEntity} contenente:
   *     <ul>
   *       <li>La segnalazione aggiornata, se l'operazione ha avuto successo.
   *       <li>Errore 400, se il gestore non è trovato o c'è un errore nei parametri.
   *       <li>Errore 500, in caso di errore interno del server.
   *     </ul>
   */
  @Operation(summary = "Risolvi una segnalazione")
  @ApiResponses({
    @ApiResponse(responseCode = "200", description = "Segnalazione risolta con successo"),
    @ApiResponse(responseCode = "400", description = "Errore durante la risoluzione"),
    @ApiResponse(responseCode = "500", description = "Errore interno del server")
  })
  @PatchMapping("/risolvi/{idSegnalazione}")
  public ResponseEntity<SegnalazionePagamento> risolvereSegnalazione(
      @PathVariable Long idSegnalazione, @RequestParam String dettagliRisoluzione) {
    try {
      // Ottieni l'email del gestore autenticato dal contesto di sicurezza
      String gestoreEmail = SecurityContextHolder.getContext().getAuthentication().getName();

      // Recupera il gestore di pagamento dalla repository
      Optional<GestorePagamento> optionalGestore =
          gestorePagamentoRepository.findByEmail(gestoreEmail);

      if (optionalGestore.isEmpty()) {
        return ResponseEntity.status(400)
            .body(null); // Restituisce errore se il gestore non è trovato
      }

      // Aggiorna lo stato della segnalazione
      Optional<SegnalazionePagamento> segnalazione =
          amministrazioneService.aggiornaStatoSegnalazionePagamento(
              idSegnalazione, optionalGestore.get(), dettagliRisoluzione);

      if (segnalazione.isEmpty()) {
        return ResponseEntity.status(500).body(null); // Errore durante l'aggiornamento
      }

      return ResponseEntity.ok(segnalazione.get()); // Restituisce la segnalazione risolta
    } catch (Exception e) {
      return ResponseEntity.status(400).body(null); // Gestisce errori generici
    }
  }

  /**
   * Prende in carico una segnalazione di pagamento.
   *
   * <p>Consente a un gestore autenticato di prendere in carico una segnalazione di pagamento.
   * L'endpoint associa il gestore alla segnalazione e aggiorna il suo stato.
   *
   * @param idSegnalazione ID della segnalazione da prendere in carico.
   * @return Una {@link ResponseEntity} contenente:
   *     <ul>
   *       <li>La segnalazione aggiornata, se l'operazione ha avuto successo.
   *       <li>Errore 400, se il gestore non è trovato o c'è un errore nei parametri.
   *       <li>Errore 500, in caso di errore interno del server.
   *     </ul>
   */
  @PatchMapping("/prendiInCarico/{idSegnalazione}")
  public ResponseEntity<SegnalazionePagamento> prendiInCaricoSegnalazione(
      @PathVariable Long idSegnalazione) {

    String gestoreEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    Optional<GestorePagamento> optionalGestore =
        gestorePagamentoRepository.findByEmail(gestoreEmail);

    if (optionalGestore.isEmpty()) {
      return ResponseEntity.status(400)
          .body(null); // Restituisce errore se il gestore non è trovato
    }

    Optional<SegnalazionePagamento> segnalazione =
        amministrazioneService.aggiornaStatoSegnalazionePagamento(
            idSegnalazione, optionalGestore.get(), null);

    if (segnalazione.isEmpty()) {
      return ResponseEntity.status(500).body(null); // Errore durante l'aggiornamento
    }

    return ResponseEntity.ok(segnalazione.get()); // Restituisce la segnalazione aggiornata
  }

  /**
   * Recupera tutte le segnalazioni di pagamento.
   *
   * <p>Restituisce una lista di tutte le segnalazioni di pagamento registrate nel sistema. Ogni
   * segnalazione è arricchita con informazioni relative all'utente e ai dettagli della
   * segnalazione.
   *
   * @return Una {@link ResponseEntity} contenente:
   *     <ul>
   *       <li>Una lista di mappe con i dettagli delle segnalazioni e degli utenti associati.
   *       <li>Errore 500, in caso di errore interno del server.
   *     </ul>
   */
  @GetMapping
  public ResponseEntity<List<Map<String, Object>>> getAllSegnalazioniPagamento() {
    List<SegnalazionePagamento> segnalazioni = amministrazioneService.getAllSegnalazioni();

    List<Map<String, Object>> response =
        segnalazioni.stream()
            .map(
                segnalazione -> {
                  // Mappa con i dettagli della segnalazione
                  Map<String, Object> info =
                      new HashMap<>(
                          Map.of(
                              "id", segnalazione.getId(),
                              "descrizioneProblema", segnalazione.getDescrizioneProblema(),
                              "stato", segnalazione.getStato(),
                              "dataCreazione", segnalazione.getDataCreazione(),
                              "dataRisoluzione",
                                  segnalazione.getDataRisoluzione() != null
                                      ? segnalazione.getDataRisoluzione()
                                      : LocalDate.now(),
                              "dettagliRisoluzione",
                                  segnalazione.getDettagliRisoluzione() != null
                                      ? segnalazione.getDettagliRisoluzione()
                                      : "Non disponibile"));

                  // Wrapper con utente e info
                  return Map.of(
                      "utente",
                      segnalazione.getUtente() != null
                          ? segnalazione.getUtente().getName()
                          : "Utente sconosciuto",
                      "info",
                      List.of(info));
                })
            .collect(Collectors.toList());

    return ResponseEntity.ok(response); // Restituisce la lista delle segnalazioni
  }
}
