package it.unisa.zwhbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un'entità Utente nel sistema. Mappa la tabella "utente" nel database e
 * include le regole di validazione per i campi.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database. Usa {@code @Data} di Lombok per generare
 * automaticamente i metodi getter, setter, toString, equals e hashCode. Include regole di
 * validazione per i campi con le annotazioni di Jakarta Validation.
 *
 * @author Alessia Ture
 */
@Entity
@Table(name = "utente")
public class Utente {
  /**
   * Identificatore univoco dell'utente.
   *
   * <p>Annota il campo con {@code @Id} per indicare che è la chiave primaria. Usa
   * {@code @GeneratedValue} con {@code GenerationType.IDENTITY} per l'auto-incremento nel database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Email dell'utente.
   *
   * <p>Questo campo è obbligatorio, deve essere un'email valida e unica nel database. Annota il
   * campo con {@code @NotBlank}, {@code @Email} e {@code Column} per definire le regole di
   * validazione e i vincoli del database.
   */
  @NotBlank(message = "L'email è obbligatoria")
  @Email(message = "Inserisci un'email valida")
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * Password dell'utente.
   *
   * <p>Questo campo è obbligatorio e deve avere almeno 6 caratteri. Annota il campo con
   * {@code @NotBlank}, {@code @Size} e {@code Column} per definire le regole di validazione e i
   * vincoli del database.
   */
  @NotBlank(message = "La password è obbligatoria")
  @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
  @Column(nullable = false)
  private String password;

  /**
   * Nome dell'utente.
   *
   * <p>Questo campo è obbligatorio. Annota il campo con {@code @NotBlank} per definire la regola di
   * validazione.
   */
  @NotBlank(message = "Il nome è obbligatorio")
  private String name;

  // Lista di prodotti in frigo
  @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore // Escludi dalla serializzazione
  private List<PossiedeInFrigo> prodottiInFrigo = new ArrayList<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public @NotBlank(message = "L'email è obbligatoria") @Email(message = "Inserisci un'email valida")
  String getEmail() {
    return email;
  }

  public void setEmail(
      @NotBlank(message = "L'email è obbligatoria") @Email(message = "Inserisci un'email valida")
          String email) {
    this.email = email;
  }

  public @NotBlank(message = "La password è obbligatoria") @Size(
      min = 6,
      message = "La password deve avere almeno 6 caratteri") String getPassword() {
    return password;
  }

  public void setPassword(
      @NotBlank(message = "La password è obbligatoria")
          @Size(min = 6, message = "La password deve avere almeno 6 caratteri")
          String password) {
    this.password = password;
  }

  public @NotBlank(message = "Il nome è obbligatorio") String getName() {
    return name;
  }

  public void setName(@NotBlank(message = "Il nome è obbligatorio") String name) {
    this.name = name;
  }

  public List<PossiedeInFrigo> getProdottiInFrigo() {
    return prodottiInFrigo;
  }

  public void setProdottiInFrigo(List<PossiedeInFrigo> prodottiInFrigo) {
    this.prodottiInFrigo = prodottiInFrigo;
  }

  /**
   * Relazione uno-a-molti con l'entità Ricetta.
   *
   * <p>Un utente può essere autore di più ricette. La relazione è mappata dal campo "autore" nella
   * classe Ricetta.
   */
  @OneToMany(mappedBy = "autore", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<Ricetta> ricette = new ArrayList<>();

  /**
   * Relazione uno-a-molti con l'entità ListaBloccati. Un utente può essere associato a più voci
   * nella lista dei bloccati.
   */
  @OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonIgnore
  private List<ListaBloccati> listaBloccati = new ArrayList<>();

  // Aggiunto il campo numeroSegnalazioni per contare il numero di segnalazioni
  @Column(nullable = false)
  private int numeroSegnalazioni = 0; // Inizializzato a 0

  // Nuovo campo per il blocco dell'utente
  @Column(nullable = false)
  private Boolean bloccato = false; // Indica se l'utente è bloccato o meno

  public int getNumeroSegnalazioni() {
    return numeroSegnalazioni;
  }

  public void setNumeroSegnalazioni(int numeroSegnalazioni) {
    this.numeroSegnalazioni = numeroSegnalazioni;
  }

  public Boolean getBloccato() {
    return bloccato;
  }

  public void setBloccato(Boolean bloccato) {
    this.bloccato = bloccato;
  }
}
