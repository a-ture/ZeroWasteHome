package it.unisa.zwhbackend.model.entity;

import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Classe che rappresenta un'entità SegnalazionePagamento nel sistema. Mappa la tabella
 * "segnalazione_pagamento" nel database.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database. Usa {@code @Data} di Lombok per generare
 * automaticamente i metodi getter, setter, toString, equals e hashCode.
 *
 * @author Benito Farina
 */
@Entity
@Table(name = "segnalazione_pagamento")
public class SegnalazionePagamento {

  /**
   * Identificatore univoco della segnalazione.
   *
   * <p>Annota il campo con {@code @Id} per indicare che è la chiave primaria. Usa
   * {@code @GeneratedValue} con {@code GenerationType.IDENTITY} per l'auto-incremento nel database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Riferimento all'utente che ha effettuato la segnalazione.
   *
   * <p>Annota il campo con {@code @ManyToOne} per indicare che più segnalazioni possono essere
   * fatte da un singolo utente. Usa {@code @JoinColumn} per definire la colonna di join con la
   * tabella "utente".
   */
  @ManyToOne
  @JoinColumn(name = "utente_id", nullable = false)
  private Utente utente; // L'utente che ha fatto la segnalazione

  /**
   * Riferimento al gestore di pagamento responsabile della segnalazione.
   *
   * <p>Annota il campo con {@code @ManyToOne} per indicare che più segnalazioni possono essere
   * gestite da un singolo gestore di pagamento.
   */
  @ManyToOne
  private GestorePagamento gestorePagamento; // Il gestore responsabile della segnalazione

  /**
   * Descrizione dettagliata del problema segnalato.
   *
   * <p>Questo campo è obbligatorio e deve essere riempito dall'utente. Annota il campo con
   * {@code @Column} per definire il vincolo di non nullità nel database.
   */
  @Column(nullable = false)
  private String descrizioneProblema;

  /**
   * Stato della segnalazione (ad esempio, IN_SOSPESO, RISOLTO).
   *
   * <p>Il campo è rappresentato come un enum. Annota il campo con {@code @Enumerated} per indicare
   * che si tratta di un valore enumerato, e con {@code @Column} per definire il vincolo di non
   * nullità nel database.
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private StatoSegnalazione stato; // Stato della segnalazione (e.g., IN_SOSPESO, RISOLTO)

  /**
   * Data di creazione della segnalazione.
   *
   * <p>Questo campo è obbligatorio e viene valorizzato automaticamente al momento della creazione
   * della segnalazione.
   */
  @Column(nullable = false)
  private LocalDateTime dataCreazione; // Data della segnalazione

  /**
   * Data di risoluzione della segnalazione (se applicabile).
   *
   * <p>Questo campo è facoltativo e viene valorizzato quando la segnalazione viene risolta.
   */
  private LocalDateTime dataRisoluzione; // Data della risoluzione (se applicabile)

  /**
   * Dettagli aggiuntivi riguardanti la risoluzione della segnalazione.
   *
   * <p>Campo facoltativo che fornisce una descrizione più dettagliata della risoluzione della
   * segnalazione.
   */
  @Column(length = 500)
  private String dettagliRisoluzione;

  public SegnalazionePagamento() {}

  public SegnalazionePagamento(
      Long id,
      Utente utente,
      GestorePagamento gestorePagamento,
      LocalDateTime dataCreazione,
      LocalDateTime dataRisoluzione,
      StatoSegnalazione stato,
      String descrizioneProblema,
      String dettagliRisoluzione) {
    this.id = id;
    this.utente = utente;
    this.gestorePagamento = gestorePagamento;
    this.dataCreazione = dataCreazione;
    this.dataRisoluzione = dataRisoluzione;
    this.stato = stato;
    this.descrizioneProblema = descrizioneProblema;
    this.dettagliRisoluzione = dettagliRisoluzione;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public GestorePagamento getGestorePagamento() {
    return gestorePagamento;
  }

  public void setGestorePagamento(GestorePagamento gestorePagamento) {
    this.gestorePagamento = gestorePagamento;
  }

  public String getDescrizioneProblema() {
    return descrizioneProblema;
  }

  public void setDescrizioneProblema(String descrizioneProblema) {
    this.descrizioneProblema = descrizioneProblema;
  }

  public StatoSegnalazione getStato() {
    return stato;
  }

  public void setStato(StatoSegnalazione stato) {
    this.stato = stato;
  }

  public LocalDateTime getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(LocalDateTime dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  public LocalDateTime getDataRisoluzione() {
    return dataRisoluzione;
  }

  public void setDataRisoluzione(LocalDateTime dataRisoluzione) {
    this.dataRisoluzione = dataRisoluzione;
  }

  public String getDettagliRisoluzione() {
    return dettagliRisoluzione;
  }

  public void setDettagliRisoluzione(String dettagliRisoluzione) {
    this.dettagliRisoluzione = dettagliRisoluzione;
  }
}
