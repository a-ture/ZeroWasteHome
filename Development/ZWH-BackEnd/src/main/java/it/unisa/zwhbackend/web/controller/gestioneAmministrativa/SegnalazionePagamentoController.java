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
import org.springframework.web.bind.annotation.*;

/**
 * Controller per gestire le segnalazioni di pagamento. Fornisce l'endpoint per risolvere le
 * segnalazioni di pagamento e per prenderle in carico.
 *
 * <p>Questa classe espone un'API REST per risolvere le segnalazioni di pagamento e per permettere
 * ai gestori di prendere in carico i problemi relativi ai pagamenti tramite l'ID della segnalazione
 * e l'ID del gestore.
 *
 * @author Benito Farina
 */
@RestController
@RequestMapping("/api/segnalazioniPagamento")
public class SegnalazionePagamentoController {

  private final AmministrazioneService amministrazioneService;
  private final GestorePagamentoRepository gestorePagamentoRepository;

  /**
   * Costruttore per iniettare i servizi e i repository necessari al controller.
   *
   * @param amministrazioneService il servizio che fa da facade per le funzionalità di gestione
   *     amministrativa
   * @param gestorePagamentoRepository il repository per l'entità {@link GestorePagamento}
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
   * <p>Questo endpoint consente a un gestore di risolvere una segnalazione di pagamento fornendo
   * l'ID della segnalazione e l'ID del gestore che risolve il problema. Restituisce la segnalazione
   * aggiornata se la risoluzione è riuscita o un errore altrimenti.
   *
   * @param idSegnalazione l'ID della segnalazione di pagamento da risolvere
   * @param gestoreId l'ID del gestore che risolve la segnalazione
   * @param dettagliRisoluzione dettagli relativi alla risoluzione della segnalazione
   * @return una {@link ResponseEntity} contenente la segnalazione risolta o un errore
   */
  @Operation(summary = "Risolvi una segnalazione")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Segnalazione risolta con successo"),
        @ApiResponse(
            responseCode = "400",
            description = "Segnalazione non trovata o errore durante la risoluzione"),
        @ApiResponse(responseCode = "500", description = "Errore interno del server")
      })
  @PatchMapping("/risolvi/{idSegnalazione}")
  public ResponseEntity<SegnalazionePagamento> risolvereSegnalazione(
      @PathVariable Long idSegnalazione,
      @RequestParam String gestoreId,
      @RequestParam String dettagliRisoluzione) {
    try {

      // Recupera il gestore di pagamento dalla repository
      Optional<GestorePagamento> optionalGestore =
          gestorePagamentoRepository.findByEmail(gestoreId);

      // Se il gestore non è trovato, restituisce errore 400
      if (optionalGestore.isEmpty()) {
        return ResponseEntity.status(400).body(null); // Gestore non trovato
      }

      // Tenta di risolvere la segnalazione, aggiornando il suo stato
      Optional<SegnalazionePagamento> segnalazione =
          amministrazioneService.aggiornaStatoSegnalazionePagamento(
              idSegnalazione, optionalGestore.get(), dettagliRisoluzione);

      // Se la segnalazione non è stata aggiornata, restituisce errore 500
      if (segnalazione.isEmpty()) {
        return ResponseEntity.status(500).body(null); // Errore durante l'aggiornamento dello stato
      }

      // Restituisce la segnalazione aggiornata
      return ResponseEntity.ok(segnalazione.get());
    } catch (Exception e) {
      // In caso di errore generico, restituisce errore 400
      return ResponseEntity.status(400).body(null); // In caso di errore generico
    }
  }

  /**
   * Permette a un gestore di prendere in carico una segnalazione di pagamento.
   *
   * <p>Questo endpoint consente a un gestore di prendere in carico una segnalazione di pagamento
   * fornendo l'ID della segnalazione e l'ID del gestore. L'ID del gestore viene usato per associare
   * la segnalazione al gestore che si fa carico del problema.
   *
   * @param idSegnalazione l'ID della segnalazione di pagamento da prendere in carico
   * @param gestoreId l'ID del gestore che prende in carico la segnalazione
   * @return una {@link ResponseEntity} contenente la segnalazione aggiornata o un errore
   */
  @PatchMapping("/prendiInCarico/{idSegnalazione}")
  public ResponseEntity<SegnalazionePagamento> prendiInCaricoSegnalazione(
      @PathVariable Long idSegnalazione, @RequestParam String gestoreId) {

    // Recupera il gestore di pagamento dalla repository
    Optional<GestorePagamento> optionalGestore = gestorePagamentoRepository.findByEmail(gestoreId);

    // Se il gestore non è trovato, restituisce errore 400
    if (optionalGestore.isEmpty()) {
      return ResponseEntity.status(400).body(null); // Gestore non trovato
    }

    // Tenta di prendere in carico la segnalazione, aggiornando il suo stato
    Optional<SegnalazionePagamento> segnalazione =
        amministrazioneService.aggiornaStatoSegnalazionePagamento(
            idSegnalazione, optionalGestore.get(), null);

    // Se la segnalazione non è stata aggiornata, restituisce errore 500
    if (segnalazione.isEmpty()) {
      return ResponseEntity.status(500).body(null); // Errore durante l'aggiornamento dello stato
    }

    // Restituisce la segnalazione aggiornata
    return ResponseEntity.ok(segnalazione.get());
  }

  @GetMapping
  public ResponseEntity<List<Map<String, Object>>> getAllSegnalazioniPagamento() {
    List<SegnalazionePagamento> segnalazioni = amministrazioneService.getAllSegnalazioni();

    List<Map<String, Object>> response =
        segnalazioni.stream()
            .map(
                segnalazione -> {
                  // Crea una mappa con i dettagli della segnalazione
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

                  // Crea il wrapper con il nome dell'utente e info
                  Map<String, Object> wrapper =
                      new HashMap<>(
                          Map.of(
                              "utente",
                              segnalazione.getUtente() != null
                                  ? segnalazione.getUtente().getName()
                                  : "Utente sconosciuto",
                              "info",
                              List.of(info) // Aggiungi info come lista
                              ));

                  return wrapper;
                })
            .collect(Collectors.toList());

    return ResponseEntity.ok(response);
  }
}
