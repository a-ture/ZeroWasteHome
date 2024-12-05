package it.unisa.zwhbackend.model.enums;

/**
 * Enum che rappresenta i possibili stati di una segnalazione nel sistema.
 *
 * <p>Questo enum è utilizzato per tracciare lo stato della segnalazione durante il processo di
 * gestione. Ogni stato riflette una fase del ciclo di vita della segnalazione.
 *
 * @author Giovanni Balzano
 */
public enum StatoSegnalazione {
  /**
   * Stato che indica che la segnalazione è stata aperta.
   *
   * <p>Questo stato viene utilizzato quando la segnalazione è stata ricevuta e un gestore può
   * prenderla in carico e lavorare su di essa.
   */
  APERTA,
  /**
   * Stato che indica che la segnalazione è in fase di risoluzione.
   *
   * <p>Questo stato viene utilizzato quando la segnalazione è stata ricevuta e il gestore sta
   * lavorando su di essa.
   */
  IN_RISOLUZIONE,

  /**
   * Stato che indica che la segnalazione è stata risolta.
   *
   * <p>Questo stato viene utilizzato quando la segnalazione è stata trattata e risolta dal gestore.
   */
  CHIUSA
}
