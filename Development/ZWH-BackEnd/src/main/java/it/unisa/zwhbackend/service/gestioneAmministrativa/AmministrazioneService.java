package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.GestorePagamento;
import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per il servizio di gestione amministrativa.
 *
 * <p>Questa interfaccia definisce i metodi per la gestione delle segnalazioni di pagamento e
 * ricetta. Permette di risolvere e aggiornare lo stato delle segnalazioni.
 *
 * @author Marco Renella, Benito Farina, Giovanni Balzano
 */
public interface AmministrazioneService {

  /**
   * Risolve una segnalazione di pagamento.
   *
   * <p>Questo metodo consente di risolvere una segnalazione di pagamento specificata identificata
   * dal suo ID, fornendo i dettagli della risoluzione e il gestore del pagamento responsabile.
   *
   * @param id l'identificativo della segnalazione di pagamento da risolvere
   * @param gestorePagamento il gestore dei pagamenti
   * @param dettagliRisoluzione descrizione della risoluzione
   * @return la segnalazione aggiornata, se presente
   */
  Optional<SegnalazionePagamento> risolviSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione);

  /**
   * Aggiorna lo stato di una segnalazione di pagamento.
   *
   * <p>Questo metodo permette di aggiornare lo stato di una segnalazione di pagamento, associando
   * un gestore dei pagamenti e fornendo i dettagli della risoluzione.
   *
   * @param id l'identificativo della segnalazione di pagamento da aggiornare
   * @param gestorePagamento il gestore dei pagamenti
   * @param dettagliRisoluzione descrizione della risoluzione
   * @return la segnalazione aggiornata, se presente
   */
  Optional<SegnalazionePagamento> aggiornaStatoSegnalazionePagamento(
      Long id, GestorePagamento gestorePagamento, String dettagliRisoluzione);

  /**
   * Recupera tutte le segnalazioni di pagamento.
   *
   * <p>Questo metodo restituisce una lista di tutte le segnalazioni di pagamento presenti nel
   * sistema.
   *
   * @return una lista di segnalazioni di pagamento
   */
  List<SegnalazionePagamento> getAllSegnalazioni();

  /**
   * Risolve una segnalazione di ricetta.
   *
   * <p>Questo metodo consente di risolvere una segnalazione di ricetta, fornendo il motivo del
   * blocco e l'identificativo del gestore responsabile.
   *
   * @param id l'identificativo della segnalazione di ricetta da risolvere
   * @param gestoreId l'identificativo del gestore responsabile della risoluzione
   * @param motivoBlocco il motivo del blocco della ricetta
   * @return un messaggio di successo o errore
   */
  String risolviSegnalazioneRicetta(Long id, String gestoreId, String motivoBlocco);
}
