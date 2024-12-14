package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import java.util.Optional;

public interface AmministrazioneService {

  /**
   * Risolve una segnalazione di pagamento.
   *
   * @param id l'identificativo della segnalazione di pagamento da risolvere
   * @param gestorePagamento il gestore dei pagamenti
   * @param dettagliRisoluzione descrizione della risoluzione
   * @return la segnalazione aggiornata
   */
  Optional<SegnalazionePagamento> risolviSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione);

  Optional<SegnalazionePagamento> aggiornaStatoSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione);

  /**
   * Risolve una segnalazione di ricetta.
   *
   * @param id l'identificativo della segnalazione di ricetta
   * @param gestoreId l'identificativo del gestore
   * @param motivoBlocco il motivo del blocco
   * @return un messaggio di successo o errore
   */
  String risolviSegnalazioneRicetta(Long id, String gestoreId, String motivoBlocco);
}
