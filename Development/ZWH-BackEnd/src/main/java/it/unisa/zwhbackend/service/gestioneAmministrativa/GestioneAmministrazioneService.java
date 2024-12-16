package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.annotations.ExcludeGeneratedFromCodeCoverage;
import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio di gestione amministrativa.
 *
 * <p>Questa classe implementa i metodi definiti nell'interfaccia {@link AmministrazioneService}. Si
 * occupa di risolvere e aggiornare lo stato delle segnalazioni di pagamento e ricetta, interagendo
 * con i rispettivi servizi {@link SegnalazionePagamentoService} e {@link
 * SegnalazioneRicettaService}.
 *
 * @author Marco Renella, Benito Farina, Giovanni Balzano
 */
@Service
public class GestioneAmministrazioneService implements AmministrazioneService {

  private final SegnalazionePagamentoService pagamentoService;
  private final SegnalazioneRicettaService ricettaService;

  /**
   * Costruttore del servizio di gestione amministrativa.
   *
   * <p>Inizializza i servizi {@code SegnalazionePagamentoService} e {@code
   * SegnalazioneRicettaService} necessari per la gestione delle segnalazioni.
   *
   * @param pagamentoService il servizio per la gestione delle segnalazioni di pagamento
   * @param ricettaService il servizio per la gestione delle segnalazioni di ricetta
   */
  public GestioneAmministrazioneService(
      SegnalazionePagamentoService pagamentoService, SegnalazioneRicettaService ricettaService) {
    this.pagamentoService = pagamentoService;
    this.ricettaService = ricettaService;
  }

  /**
   * Risolve una segnalazione di pagamento.
   *
   * <p>Questo metodo delega l'operazione di risoluzione della segnalazione di pagamento al servizio
   * {@link SegnalazionePagamentoService}.
   *
   * @param id l'identificativo della segnalazione di pagamento da risolvere
   * @param gestorePagamento il gestore dei pagamenti
   * @param dettagliRisoluzione descrizione della risoluzione
   * @return la segnalazione aggiornata, se presente
   */
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public Optional<SegnalazionePagamento> risolviSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione) {
    return pagamentoService.aggiornaStatoSegnalazione(id, gestorePagamento, dettagliRisoluzione);
  }

  /**
   * Aggiorna lo stato di una segnalazione di pagamento.
   *
   * <p>Questo metodo delega l'operazione di aggiornamento dello stato della segnalazione di
   * pagamento al servizio {@link SegnalazionePagamentoService}.
   *
   * @param id l'identificativo della segnalazione di pagamento da aggiornare
   * @param gestorePagamento il gestore dei pagamenti
   * @param dettagliRisoluzione descrizione della risoluzione
   * @return la segnalazione aggiornata, se presente
   */
  @Override
  public Optional<SegnalazionePagamento> aggiornaStatoSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione) {
    return pagamentoService.aggiornaStatoSegnalazione(id, gestorePagamento, dettagliRisoluzione);
  }

  /**
   * Risolve una segnalazione di ricetta.
   *
   * <p>Questo metodo delega l'operazione di risoluzione della segnalazione di ricetta al servizio
   * {@link SegnalazioneRicettaService}.
   *
   * @param id l'identificativo della segnalazione di ricetta da risolvere
   * @param gestoreId l'identificativo del gestore responsabile della risoluzione
   * @param motivoBlocco il motivo del blocco della ricetta
   * @return un messaggio di successo o errore
   */
  @Override
  public String risolviSegnalazioneRicetta(Long id, String gestoreId, String motivoBlocco) {
    return ricettaService.risolviSegnalazione(id, gestoreId, motivoBlocco);
  }

  /**
   * Recupera tutte le segnalazioni di pagamento.
   *
   * <p>Questo metodo delega l'operazione di recupero delle segnalazioni di pagamento al servizio
   * {@link SegnalazionePagamentoService}.
   *
   * @return una lista di segnalazioni di pagamento
   */
  @Override
  public List<SegnalazionePagamento> getAllSegnalazioni() {
    return pagamentoService.getAllSegnalazioni();
  }
}
