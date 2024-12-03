package it.unisa.zwhbackend.web.controller.gestioneAmministrativa;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import it.unisa.zwhbackend.model.repository.GestorePagamentoRepository;
import it.unisa.zwhbackend.service.gestioneAmministrativa.GestioneSegnalazionePagamentoService;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per gestire le segnalazioni di pagamento. Fornisce l'endpoint per risolvere le
 * segnalazioni di pagamento.
 *
 * <p>Questa classe espone un'API REST per risolvere le segnalazioni di pagamento, permettendo ai
 * gestori di risolvere i problemi relativi ai pagamenti tramite l'ID della segnalazione e l'ID del
 * gestore che risolve il problema.
 *
 * @author Benito Farina
 */
@RestController
@RequestMapping("/api/segnalazioniPagamento")
public class SegnalazionePagamentoController {

  private final GestioneSegnalazionePagamentoService gestioneSegnalazionePagamentoService;
  private final GestorePagamentoRepository gestorePagamentoRepository;

  /**
   * Costruttore per iniettare i servizi e i repository necessari al controller.
   *
   * @param gestioneSegnalazionePagamentoService il servizio che gestisce le segnalazioni di
   *     pagamento
   * @param gestorePagamentoRepository il repository per l'entità {@link GestorePagamento}
   */
  public SegnalazionePagamentoController(
      GestioneSegnalazionePagamentoService gestioneSegnalazionePagamentoService,
      GestorePagamentoRepository gestorePagamentoRepository) {
    this.gestioneSegnalazionePagamentoService = gestioneSegnalazionePagamentoService;
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
      @PathVariable Long idSegnalazione, @RequestParam Long gestoreId) { // L'ID del gestore
    try {
      // Recupera il gestore per ID
      Optional<GestorePagamento> optionalGestorePagamento =
          gestorePagamentoRepository.findById(gestoreId);

      // Verifica se il gestore è stato trovato
      if (optionalGestorePagamento.isEmpty()) {
        return ResponseEntity.status(400).body(null); // Gestore non trovato
      }

      // Risolve la segnalazione chiamando il servizio
      Optional<SegnalazionePagamento> segnalazione =
          gestioneSegnalazionePagamentoService.RisolviSegnalazione(
              idSegnalazione, optionalGestorePagamento.get());

      // Se la segnalazione non è stata trovata o non è risolvibile, restituisce un errore
      if (segnalazione.isEmpty()) {
        return ResponseEntity.status(500).body(null); // Errore durante la risoluzione
      }

      // Restituisce la segnalazione risolta
      return ResponseEntity.ok(segnalazione.get());
    } catch (Exception e) {
      return ResponseEntity.status(400).body(null); // In caso di errore generico
    }
  }
}
