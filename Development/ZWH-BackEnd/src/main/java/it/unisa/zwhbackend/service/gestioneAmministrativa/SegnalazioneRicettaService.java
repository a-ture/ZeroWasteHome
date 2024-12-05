package it.unisa.zwhbackend.service.gestioneAmministrativa;

/**
 * Interfaccia per la gestione delle segnalazioni nel sistema.
 *
 * <p>Questa interfaccia definisce il contratto per i servizi che gestiscono le segnalazioni, come
 * la loro risoluzione. Le classi che implementano questa interfaccia devono fornire una logica per
 * risolvere una segnalazione, come aggiornare il suo stato o intraprendere altre azioni necessarie.
 *
 * @author Giovanni Balzano
 */
public interface SegnalazioneRicettaService {

  /**
   * Risolve una segnalazione, aggiornando il suo stato e persisting la modifica nel database.
   *
   * <p>Implementazioni concrete devono definire la logica per aggiornare una segnalazione, come
   * l'aggiornamento del suo stato e altre operazioni correlate (es. bloccare l'autore).
   *
   * @param segnalazioneId l'ID della segnalazione da risolvere.
   * @param gestore_id l'ID del gestore che prende in carico la segnalazione.
   * @param motivoBlocco il motivo del blocco dell'utente.
   * @return un messaggio che indica se l'operazione è stata completata con successo o se ci sono
   *     errori.
   */
  String risolviSegnalazione(Long segnalazioneId, Long gestore_id, String motivoBlocco);
}
