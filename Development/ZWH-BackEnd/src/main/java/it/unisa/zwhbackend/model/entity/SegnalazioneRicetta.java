package it.unisa.zwhbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**
 * Classe che rappresenta una segnalazione di una ricetta nel sistema.
 *
 * <p>Questa classe mappa la tabella "segnalazione_ricetta" nel database e include le regole di
 * validazione per i campi. Utilizza le annotazioni di Jakarta Persistence (@Entity, @Table) per
 * configurare l'entità come una classe JPA. Lombok viene utilizzato per generare automaticamente i
 * metodi getter, setter, toString, equals e hashCode.
 *
 * @author Giovanni Balzano
 */
@Entity // Annota la classe come una JPA entity, mappata su una tabella del database
@Table(name = "segnalazione_ricetta") // Definisce il nome della tabella nel database
public class SegnalazioneRicetta {

  /**
   * Identificatore univoco della segnalazione.
   *
   * <p>La colonna è definita come chiave primaria {@code @Id} e il valore viene generato
   * automaticamente tramite {@code @GeneratedValue}. Utilizziamo {@code GenerationType.IDENTITY}
   * per un incremento automatico dei valori del campo {@code id}.
   */
  @Id // Definisce la chiave primaria della tabella
  @GeneratedValue(
      strategy =
          GenerationType.IDENTITY) // La chiave primaria viene generata automaticamente dal database
  private Long id; // Identificatore univoco per ogni segnalazione

  /**
   * Contenuto della segnalazione.
   *
   * <p>Il campo è obbligatorio grazie all'annotazione {@code @NotBlank}, che garantisce che il
   * contenuto non sia vuoto. Viene utilizzata l'annotazione {@code @Column(nullable = false, length
   * = 5000)} per definire il campo come obbligatorio e limitarne la lunghezza a 5000 caratteri.
   */
  @NotBlank(
      message =
          "Il contenuto della segnalazione è obbligatorio") // Il contenuto non può essere vuoto
  @Column(
      nullable = false,
      length = 5000) // Colonna obbligatoria e con una lunghezza massima di 5000 caratteri
  private String contenuto; // Contenuto testuale della segnalazione

  /**
   * Stato della ricetta associata alla segnalazione.
   *
   * <p>Questo campo rappresenta lo stato della segnalazione, utilizzando un enum definito in {@code
   * StatoSegnalazione}. L'annotazione {@code @Column(nullable = false)} garantisce che il campo non
   * possa essere {@code null}.
   */
  @Column(nullable = false) // La colonna è obbligatoria (non può essere null)
  private StatoSegnalazione stato; // Stato della segnalazione (ad esempio, "in corso", "risolto")

  /**
   * Gestore associato alla segnalazione.
   *
   * <p>Questo campo definisce una relazione molti-a-uno tra la segnalazione e il gestore che la
   * gestisce. L'annotazione {@code @ManyToOne} indica che molte segnalazioni possono essere
   * associate a un singolo gestore. {@code @JoinColumn(name = "gestore_id", nullable = false)}
   * specifica la colonna che memorizza il riferimento al gestore nella tabella "segnalazione".
   */
  @ManyToOne // Relazione molti-a-uno con l'entità GestoreCommunity
  @JoinColumn(name = "gestore_id") // Collega questa entità alla colonna 'gestore_id' della tabella
  // 'segnalazione_ricetta'
  @JsonIgnore
  private GestoreCommunity gestoreAssociato; // Il gestore che ha preso in carico la segnalazione

  /**
   * Relazione molti-a-uno con l'entità Ricetta.
   *
   * <p>Questo campo rappresenta la relazione tra la segnalazione e la ricetta associata.
   * L'annotazione {@code @ManyToOne} definisce che molte segnalazioni possono essere associate a
   * una singola ricetta. {@code @JoinColumn(name = "ricetta_id", nullable = true)} indica che la
   * colonna "ricetta_id" è una chiave esterna nella tabella e che il valore può essere nullo (non
   * tutte le segnalazioni devono essere necessariamente associate a una ricetta).
   */
  @ManyToOne // Relazione molti-a-uno con l'entità Ricetta
  @JoinColumn(
      name = "ricetta_id",
      nullable = true) // La colonna 'ricetta_id' è opzionale (nullable = true)
  @JsonIgnore
  private Ricetta ricettaAssociato; // La ricetta associata a questa segnalazione (può essere null)

  /**
   * Metodo getter per l'ID della segnalazione.
   *
   * @return L'ID univoco della segnalazione
   */
  public Long getId() {
    return id;
  }

  /**
   * Metodo setter per l'ID della segnalazione.
   *
   * @param id Il nuovo ID da assegnare alla segnalazione
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Metodo getter per il contenuto della segnalazione.
   *
   * @return Il contenuto della segnalazione
   */
  public @NotBlank(message = "Il contenuto della segnalazione è obbligatorio") String
      getContenuto() {
    return contenuto;
  }

  /**
   * Metodo setter per il contenuto della segnalazione.
   *
   * @param contenuto Il nuovo contenuto da assegnare alla segnalazione
   */
  public void setContenuto(
      @NotBlank(message = "Il contenuto della segnalazione è obbligatorio") String contenuto) {
    this.contenuto = contenuto;
  }

  /**
   * Metodo getter per lo stato della segnalazione.
   *
   * @return Lo stato della segnalazione (es. "RISOLTO")
   */
  public StatoSegnalazione getStato() {
    return stato;
  }

  /**
   * Metodo setter per lo stato della segnalazione.
   *
   * @param stato Il nuovo stato da assegnare alla segnalazione
   */
  public void setStato(StatoSegnalazione stato) {
    this.stato = stato;
  }

  /**
   * Metodo getter per il gestore associato alla segnalazione.
   *
   * @return Il gestore che ha preso in carico la segnalazione
   */
  public GestoreCommunity getGestoreAssociato() {
    return gestoreAssociato;
  }

  /**
   * Metodo setter per il gestore associato alla segnalazione.
   *
   * @param gestoreAssociato Il nuovo gestore da associare alla segnalazione
   */
  public void setGestoreAssociato(GestoreCommunity gestoreAssociato) {
    this.gestoreAssociato = gestoreAssociato;
  }

  /**
   * Metodo getter per la ricetta associata alla segnalazione.
   *
   * @return La ricetta associata alla segnalazione
   */
  public Ricetta getRicettaAssociato() {
    return ricettaAssociato;
  }

  /**
   * Metodo setter per la ricetta associata alla segnalazione.
   *
   * @param ricettaAssociato La nuova ricetta da associare alla segnalazione
   */
  public void setRicettaAssociato(Ricetta ricettaAssociato) {
    this.ricettaAssociato = ricettaAssociato;
  }
}
