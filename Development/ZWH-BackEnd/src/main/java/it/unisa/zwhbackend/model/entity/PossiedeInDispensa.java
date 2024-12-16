package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;

/**
 * Classe che rappresenta la relazione tra un utente e i prodotti nella sua dispensa. Mappa la
 * tabella "possiede_in_dispensa" nel database e include le regole di validazione per i campi.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database. La chiave primaria della tabella è composta da
 * due campi: {@code utente} e {@code prodotto}, definita nella classe {@code PossiedeInDispensaId}.
 *
 * @author Ferdinando
 */
@Entity
@Table(name = "possiede_in_dispensa")
@IdClass(PossiedeInDispensa.PossiedeInDispensaId.class) // Specifica la chiave primaria composta
public class PossiedeInDispensa {
  /**
   * Riferimento all'utente associato al prodotto nella dispensa.
   *
   * <p>Questo campo è una chiave esterna che collega l'entità {@code Utente}. Annota il campo con
   * {@code @ManyToOne} per indicare una relazione molti-a-uno con {@code Utente}. Usa
   * {@code @JoinColumn} per definire la colonna della chiave esterna nel database.
   */
  @Id
  @ManyToOne
  @JoinColumn(name = "utente_email", nullable = false)
  private Utente utente;

  /**
   * Riferimento al prodotto posseduto dall'utente nella dispensa.
   *
   * <p>Questo campo è una chiave esterna che collega l'entità {@code Prodotto}. Annota il campo con
   * {@code @ManyToOne} per indicare una relazione molti-a-uno con {@code Prodotto}. Usa
   * {@code @JoinColumn} per definire la colonna della chiave esterna nel database.
   */
  @Id
  @ManyToOne
  @JoinColumn(name = "prodotto_id", nullable = false)
  private Prodotto prodotto;

  /**
   * Data di scadenza del prodotto.
   *
   * <p>Questo campo è obbligatorio e deve rispettare il formato "gg/mm/aa". Annota il campo con
   * {@code @Pattern} per validare il formato della data.
   */
  @Id
  @Column(name = "data_scadenza")
  @Pattern(
      regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$",
      message = "La Data deve essere del formato gg/mm/aaaa.")
  private String dataScadenza;

  /**
   * Quantità di prodotto presente nella dispensa.
   *
   * <p>Questo campo rappresenta la quantità del prodotto posseduto dall'utente nella dispensa. È
   * obbligatorio e deve essere un numero positivo maggiore di zero. Usa {@code @Min} per validare
   * che il valore sia maggiore di zero.
   */
  @Min(value = 1, message = "La Quantità deve essere un numero positivo maggiore di zero.")
  private int quantita;

  /**
   * Costruttore vuoto.
   *
   * <p>Necessario per il funzionamento della JPA, permette di creare una nuova istanza senza
   * parametri.
   */
  public PossiedeInDispensa() {
    // costruttore vuoto
  }

  /**
   * Costruttore con parametri.
   *
   * <p>Permette di creare una nuova istanza di {@code PossiedeInDispensa} con i dati specificati
   * per utente, prodotto e quantità.
   *
   * @param utente l'utente associato al prodotto
   * @param prodotto il prodotto posseduto dall'utente
   * @param quantita la quantità del prodotto nella dispensa
   * @param dataScadenza la quantità del prodotto nella dispensa
   */
  public PossiedeInDispensa(
      Utente utente, Prodotto prodotto, Integer quantita, String dataScadenza) {
    this.utente = utente;
    this.prodotto = prodotto;
    this.quantita = quantita;
    this.dataScadenza = dataScadenza;
  }

  /**
   * Restituisce l'utente associato al prodotto nella dispensa.
   *
   * @return l'utente
   */
  public Utente getUtente() {
    return utente;
  }

  /**
   * Imposta l'utente associato al prodotto nella dispensa.
   *
   * @param utente l'utente da associare
   */
  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  /**
   * Restituisce il prodotto posseduto dall'utente nella dispensa.
   *
   * @return il prodotto
   */
  public Prodotto getProdotto() {
    return prodotto;
  }

  /**
   * Imposta il prodotto posseduto dall'utente nella dispensa.
   *
   * @param prodotto il prodotto da associare
   */
  public void setProdotto(Prodotto prodotto) {
    this.prodotto = prodotto;
  }

  /**
   * Restituisce la data di scadenza del prodotto.
   *
   * @return la data di scadenza
   */
  public String getDataScadenza() {
    return dataScadenza;
  }

  /**
   * Imposta la data di scadenza del prodotto.
   *
   * @param dataScadenza la data da impostare
   */
  public void setDataScadenza(String dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  /**
   * Restituisce la quantità di prodotto presente nella dispensa.
   *
   * @return la quantità del prodotto
   */
  public int getQuantita() {
    return quantita;
  }

  /**
   * Imposta la quantità di prodotto presente nella dispensa.
   *
   * @param quantita la quantità da impostare
   */
  public void setQuantita(int quantita) {
    this.quantita = quantita;
  }

  /** Classe per rappresentare la chiave primaria composta */
  public static class PossiedeInDispensaId implements Serializable {

    /**
     * Riferimento all'utente associato al prodotto nella dispensa.
     *
     * <p>Deve corrispondere a {@code utente} in {@code PossiedeInDispensa}.
     */
    private Utente utente;

    /**
     * Riferimento al prodotto posseduto dall'utente nella dispensa.
     *
     * <p>Deve corrispondere a {@code prodotto} in {@code PossiedeInDispensa}.
     */
    private Prodotto prodotto;

    /**
     * Riferimento alla data di scadenza del prodotto.
     *
     * <p>Deve corrispondere al formato {@code gg/MM/yyyy}.
     */
    private String dataScadenza;

    /**
     * Costruttore vuoto.
     *
     * <p>Necessario per il funzionamento della JPA, permette di creare una nuova istanza senza
     * parametri.
     */
    public PossiedeInDispensaId() {
      // costruttore vuoto
    }

    /**
     * Costruttore con parametri.
     *
     * <p>Permette di creare una nuova istanza della chiave composta con i dati specificati per
     * utente e prodotto.
     *
     * @param utente l'utente associato al prodotto
     * @param prodotto il prodotto posseduto dall'utente
     * @param dataScadenza data di scadenza del prodotto
     */
    public PossiedeInDispensaId(Utente utente, Prodotto prodotto, String dataScadenza) {
      this.utente = utente;
      this.prodotto = prodotto;
      this.dataScadenza = dataScadenza;
    }

    /**
     * Restituisce l'utente associato al prodotto nella dispensa.
     *
     * @return l'utente
     */
    public Utente getUtente() {
      return utente;
    }

    /**
     * Imposta l'utente associato al prodotto nella dispensa.
     *
     * @param utente l'utente da associare
     */
    public void setUtente(Utente utente) {
      this.utente = utente;
    }

    /**
     * Restituisce il prodotto posseduto dall'utente nella dispensa.
     *
     * @return il prodotto
     */
    public Prodotto getProdotto() {
      return prodotto;
    }

    /**
     * Imposta il prodotto posseduto dall'utente nella dispensa.
     *
     * @param prodotto il prodotto da associare
     */
    public void setProdotto(Prodotto prodotto) {
      this.prodotto = prodotto;
    }

    /**
     * Restituisce la data di scadenza del prodotto.
     *
     * @return la data di scadenza
     */
    public String getDataScadenza() {
      return dataScadenza;
    }

    /**
     * Imposta la data di scadenza del prodotto.
     *
     * @param dataScadenza la data da impostare
     */
    public void setDataScadenza(String dataScadenza) {
      this.dataScadenza = dataScadenza;
    }

    /**
     * Override del metodo {@code equals} per garantire la corretta comparazione delle chiavi
     * primarie composte.
     *
     * @param o l'oggetto da confrontare
     * @return true se gli oggetti sono uguali, false altrimenti
     */
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      PossiedeInDispensaId that = (PossiedeInDispensaId) o;
      return Objects.equals(utente, that.utente) && Objects.equals(prodotto, that.prodotto);
    }

    /**
     * Override del metodo {@code hashCode} per garantire la corretta generazione del codice hash
     * per la chiave composta.
     *
     * @return il codice hash generato dalla combinazione degli oggetti utente e prodotto
     */
    @Override
    public int hashCode() {
      return Objects.hash(utente, prodotto);
    }
  }
}
