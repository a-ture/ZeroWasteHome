package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import java.util.Optional;

/**
 * Interfaccia che definisce i metodi per la gestione delle segnalazioni di pagamento.
 *
 * <p>La classe che implementa questa interfaccia fornirà la logica per risolvere le segnalazioni di
 * pagamento e altre operazioni necessarie per la gestione delle segnalazioni.
 *
 * @author Benito Farina
 */
public interface SegnalazionePagamentoService {

  /**
   * Risolve una segnalazione di pagamento.
   *
   * <p>Questo metodo permette di risolvere una segnalazione di pagamento, aggiornando lo stato
   * della segnalazione e assegnando il gestore che ha risolto il problema. Restituisce un {@link
   * Optional} contenente la segnalazione aggiornata se la risoluzione è stata effettuata
   * correttamente, altrimenti restituisce un {@link Optional} vuoto.
   *
   * @param id l'identificativo della segnalazione di pagamento da risolvere
   * @param gestore il gestore che risolve la segnalazione
   * @param dettagliRisoluzione descrizione della risoluzione della segnalazione
   * @return un {@link Optional} contenente la segnalazione risolta o vuoto se la segnalazione non è
   *     risolvibile
   */
  public Optional<SegnalazionePagamento> RisolviSegnalazione(
      Long id, GestorePagamento gestore, String dettagliRisoluzione);

  public Optional<SegnalazionePagamento> aggiornaStatoSegnalazione(
      Long idSegnalazione, GestorePagamento gestore, String dettagliRisoluzione);
}
