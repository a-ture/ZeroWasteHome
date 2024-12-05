package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

/**
 * Classe che rappresenta un gestore della community.
 *
 * <p>Questa classe mappa la tabella "gestore_community" nel database e include le regole di
 * validazione per i campi. Utilizza le annotazioni di Jakarta Persistence (@Entity, @Table) per
 * configurare l'entità come una classe JPA. Lombok viene utilizzato per generare automaticamente i
 * metodi getter, setter, toString, equals e hashCode.
 *
 * @author Giovanni Balzano
 */
@Entity
@Table(name = "gestore_community")
public class GestoreCommunity {

  /**
   * Identificatore univoco del gestore.
   *
   * <p>La colonna è definita come chiave primaria {@code @Id} e il valore viene generato
   * automaticamente tramite {@code @GeneratedValue}. Utilizziamo {@code GenerationType.IDENTITY}
   * per un incremento automatico dei valori del campo {@code id}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Nome del gestore.
   *
   * <p>Il campo è obbligatorio grazie all'annotazione {@code @NotBlank}, che garantisce che il nome
   * non sia vuoto. Viene utilizzata l'annotazione {@code @Column(nullable = false)} per garantire
   * che il valore non sia {@code null}.
   */
  @NotBlank(message = "Il nome è obbligatorio")
  @Column(nullable = false)
  private String nome;

  /**
   * Email del gestore.
   *
   * <p>Questo campo è obbligatorio e deve rispettare un formato valido grazie alle annotazioni
   * {@code @NotBlank} e {@code @Email}. La colonna è unica nel database per evitare duplicati, come
   * indicato da {@code @Column(nullable = false, unique = true)}.
   */
  @NotBlank(message = "L'email è obbligatoria")
  @Email(message = "Inserisci un'email valida")
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * Segnalazioni gestite dal gestore.
   *
   * <p>Questo campo definisce una relazione uno-a-molti tra il {@code GestoreCommunity} e la classe
   * {@code SegnalazioneRicetta}. Viene utilizzata l'annotazione {@code @OneToMany} per indicare che
   * un gestore può gestire più segnalazioni. {@code mappedBy = "gestoreAssociato"} indica che la
   * relazione è gestita dalla proprietà {@code gestoreAssociato} in {@code SegnalazioneRicetta}.
   * L'annotazione {@code cascade = CascadeType.ALL} garantisce che le operazioni di persistenza
   * (salvataggio, eliminazione) vengano propagate alle segnalazioni associate.
   */
  @OneToMany(mappedBy = "gestoreAssociato", cascade = CascadeType.ALL)
  private List<SegnalazioneRicetta> segnalazioniGestite;

  public @NotBlank(message = "L'email è obbligatoria") @Email(message = "Inserisci un'email valida")
  String getEmail() {
    return email;
  }

  public void setEmail(
      @NotBlank(message = "L'email è obbligatoria") @Email(message = "Inserisci un'email valida")
          String email) {
    this.email = email;
  }

  public Long getId() {
    return id;
  }

  public void setId(long l) {
    this.id = l;
  }

  public @NotBlank(message = "Il nome è obbligatorio") String getNome() {
    return nome;
  }

  public void setNome(@NotBlank(message = "Il nome è obbligatorio") String nome) {
    this.nome = nome;
  }

  public List<SegnalazioneRicetta> getSegnalazioniGestite() {
    return segnalazioniGestite;
  }

  public void setSegnalazioniGestite(List<SegnalazioneRicetta> segnalazioniGestite) {
    this.segnalazioniGestite = segnalazioniGestite;
  }
}
