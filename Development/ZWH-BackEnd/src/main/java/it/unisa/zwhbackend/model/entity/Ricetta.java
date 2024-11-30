package it.unisa.zwhbackend.model.entity;

import it.unisa.zwhbackend.model.enums.CategoriaRicetta;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

/**
 * Classe che rappresenta una ricetta nel sistema. Mappa la tabella "ricetta" nel database e include
 * le regole di validazione per i campi.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database. Usa {@code @Data} di Lombok per generare
 * automaticamente i metodi getter, setter, toString, equals e hashCode. Include regole di
 * validazione per i campi con le annotazioni di Jakarta Validation.
 *
 * @author Anna Tagliamonte
 */
@Entity
@Data
@Table(name = "ricetta")
public class Ricetta {

  /**
   * Identificatore univoco della ricetta.
   *
   * <p>Annota il campo con {@code @Id} per indicare che è la chiave primaria. Usa
   * {@code @GeneratedValue} con {@code GenerationType.IDENTITY} per l'auto-incremento nel database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Nome della ricetta.
   *
   * <p>Questo campo è obbligatorio e deve avere una lunghezza compresa tra 1 e 100 caratteri.
   * Annota il campo con {@code @NotBlank}, {@code @Size} e {@code Column} per definire le regole di
   * validazione e i vincoli del database.
   */
  @NotBlank(message = "Il campo 'Nome della ricetta' è obbligatorio")
  @Size(min = 1, max = 100, message = "Il nome della ricetta deve essere tra 1 e 100 caratteri")
  @Column(nullable = false)
  private String nome;

  /**
   * Elenco degli ingredienti della ricetta.
   *
   * <p>Questo campo è obbligatorio e deve contenere almeno un ingrediente. Usa
   * {@code @ElementCollection} per definire una relazione di tipo collection con il database.
   * Specifica la tabella degli ingredienti con {@code @CollectionTable}.
   */
  @ElementCollection
  @CollectionTable(name = "ingredienti_ricetta", joinColumns = @JoinColumn(name = "ricetta_id"))
  @Column(name = "ingrediente", nullable = false)
  private List<String> ingredienti;

  /**
   * Istruzioni per preparare la ricetta.
   *
   * <p>Questo campo è obbligatorio e deve avere una lunghezza massima di 5000 caratteri. Annota il
   * campo con {@code @NotBlank}, {@code @Size} e {@code Column} per definire le regole di
   * validazione e i vincoli del database.
   */
  @NotBlank(message = "Il campo 'Istruzioni' è obbligatorio")
  @Size(max = 5000, message = "Le istruzioni non possono superare i 5000 caratteri")
  @Column(nullable = false)
  private String istruzioni;

  /**
   * Categoria della ricetta.
   *
   * <p>Questo campo è obbligatorio e deve essere selezionato da un elenco predefinito. Usa
   * {@code @Enumerated} con {@code EnumType.STRING} per memorizzare i valori enumerati come
   * stringhe nel database.
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CategoriaRicetta categoria;

  /**
   * URL o percorso dell'immagine della ricetta.
   *
   * <p>Questo campo è opzionale, ma se presente deve essere in formato JPG o PNG. Annota il campo
   * con {@code @Pattern} per definire un'espressione regolare che valida il formato.
   */
  @Pattern(
      regexp = ".*\\.(jpg|png)$",
      message = "Formato immagine non supportato. Carica un file in formato JPG o PNG")
  private String img;
}
