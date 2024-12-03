package it.unisa.zwhbackend.model.entity;

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
@Entity
@Table(name = "segnalazione_ricetta")
public class SegnalazioneRicetta {

  /**
   * Identificatore univoco della segnalazione.
   *
   * <p>La colonna è definita come chiave primaria {@code @Id} e il valore viene generato
   * automaticamente tramite {@code @GeneratedValue}. Utilizziamo {@code GenerationType.IDENTITY}
   * per un incremento automatico dei valori del campo {@code id}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Contenuto della segnalazione.
   *
   * <p>Il campo è obbligatorio grazie all'annotazione {@code @NotBlank}, che garantisce che il
   * contenuto non sia vuoto. Viene utilizzata l'annotazione {@code @Column(nullable = false, length
   * = 5000)} per definire il campo come obbligatorio e limitarne la lunghezza a 5000 caratteri.
   */
  @NotBlank(message = "Il contenuto della segnalazione è obbligatorio")
  @Column(nullable = false, length = 5000)
  private String contenuto;

  /**
   * ID della ricetta associata alla segnalazione.
   *
   * <p>Questo campo rappresenta un riferimento alla ricetta associata alla segnalazione.
   * L'annotazione {@code @Column(nullable = false)} garantisce che il campo non possa essere {@code
   * null}.
   */
  @Column(nullable = false)
  private Long idRicetta;

  /**
   * Stato della ricetta associata alla segnalazione.
   *
   * <p>Questo campo rappresenta lo stato della segnalazione, utilizzando un enum definito in {@code
   * StatoSegnalazione}. L'annotazione {@code @Column(nullable = false)} garantisce che il campo non
   * possa essere {@code null}.
   */
  @Column(nullable = false)
  private StatoSegnalazione stato;

  /**
   * Gestore associato alla segnalazione.
   *
   * <p>Questo campo definisce una relazione molti-a-uno tra la segnalazione e il gestore che la
   * gestisce. L'annotazione {@code @ManyToOne} indica che molte segnalazioni possono essere
   * associate a un singolo gestore. {@code @JoinColumn(name = "gestore_id", nullable = false)}
   * specifica la colonna che memorizza il riferimento al gestore nella tabella "segnalazione".
   */
  @ManyToOne
  @JoinColumn(name = "gestore_id")
  private GestoreCommunity gestoreAssociato;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public @NotBlank(message = "Il contenuto della segnalazione è obbligatorio") String
      getContenuto() {
    return contenuto;
  }

  public void setContenuto(
      @NotBlank(message = "Il contenuto della segnalazione è obbligatorio") String contenuto) {
    this.contenuto = contenuto;
  }

  public Long getIdRicetta() {
    return idRicetta;
  }

  public void setIdRicetta(Long idRicetta) {
    this.idRicetta = idRicetta;
  }

  public StatoSegnalazione getStato() {
    return stato;
  }

  public void setStato(StatoSegnalazione stato) {
    this.stato = stato;
  }

  public GestoreCommunity getGestoreAssociato() {
    return gestoreAssociato;
  }

  public void setGestoreAssociato(GestoreCommunity gestoreAssociato) {
    this.gestoreAssociato = gestoreAssociato;
  }
}
