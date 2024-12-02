package it.unisa.zwhbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;

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
@Data
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

}
