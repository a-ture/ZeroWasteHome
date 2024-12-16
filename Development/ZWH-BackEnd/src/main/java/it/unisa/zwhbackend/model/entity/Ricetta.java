package it.unisa.zwhbackend.model.entity;

import it.unisa.zwhbackend.model.enums.CategoriaRicetta;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

/**
 * Classe che rappresenta una ricetta nel sistema. Mappa la tabella "ricetta" nel database e include
 * le regole di validazione per i campi.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database. Include regole di validazione per i campi con le
 * annotazioni di Jakarta Validation.
 *
 * @author Anna Tagliamonte
 */
@Entity
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
   * Include validazioni con {@code @NotBlank} e {@code @Size}.
   */
  @NotBlank(message = "Il campo 'Nome della ricetta' è obbligatorio")
  @Size(min = 1, max = 100, message = "Il nome della ricetta deve essere tra 1 e 100 caratteri")
  @Column(nullable = false)
  private String nome;

  /**
   * Elenco degli ingredienti della ricetta.
   *
   * <p>Questo campo è obbligatorio e deve contenere almeno un ingrediente. Usa
   * {@code @ElementCollection} per definire una relazione con una collection nel database.
   */
  @ElementCollection
  @CollectionTable(name = "ingredienti_ricetta", joinColumns = @JoinColumn(name = "ricetta_id"))
  @Column(name = "ingrediente", nullable = false)
  @NotNull(message = "L'elenco ingredienti non può essere nullo")
  @NotEmpty(message = "L'elenco ingredienti non può essere vuoto")
  private List<String> ingredienti;

  /**
   * Istruzioni per preparare la ricetta.
   *
   * <p>Questo campo è obbligatorio e deve avere una lunghezza massima di 5000 caratteri. Include
   * validazioni con {@code @NotBlank} e {@code @Size}.
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
  @NotNull(message = "Seleziona una categoria valida per la ricetta")
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CategoriaRicetta categoria;

  /**
   * URL o percorso dell'immagine della ricetta.
   *
   * <p>Questo campo è opzionale, ma se presente deve essere in formato JPG o PNG. Include
   * validazioni con {@code @Pattern}.
   */
  @Pattern(
      regexp = ".*\\.(jpg|png)$",
      message = "Formato immagine non supportato. Carica un file in formato JPG o PNG")
  private String img;

  /**
   * Quantità per persona per la ricetta.
   *
   * <p>Questo campo è opzionale e deve essere maggiore o uguale a 1 se presente. Include
   * validazione con {@code @Min}.
   */
  @Min(value = 1, message = "La quantità per persona deve essere almeno 1")
  private int quantitaPerPersona;

  /**
   * Autore della ricetta.
   *
   * <p>Rappresenta una relazione molti-a-uno con l'entità {@code Utente}. La colonna "utente_email"
   * nel database memorizza il riferimento all'utente autore della ricetta.
   */
  @ManyToOne
  @JoinColumn(name = "utente_email", nullable = false)
  private Utente autore;

  /**
   * Elenco delle segnalazioni associate alla ricetta.
   *
   * <p>Rappresenta una relazione uno-a-molti con l'entità {@code SegnalazioneRicetta}.
   */
  @OneToMany(mappedBy = "ricettaAssociato", cascade = CascadeType.PERSIST, orphanRemoval = false)
  private List<SegnalazioneRicetta> segnalazioniRicetta;

  // Getter e Setter

  /**
   * Restituisce l'identificatore univoco della ricetta.
   *
   * @return l'identificatore univoco della ricetta
   */
  public Long getId() {
    return id;
  }

  /**
   * Imposta l'identificatore univoco della ricetta.
   *
   * @param id l'identificatore da impostare
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Restituisce il nome della ricetta.
   *
   * @return il nome della ricetta
   */
  public @NotBlank(message = "Il campo 'Nome della ricetta' è obbligatorio") @Size(
      min = 1,
      max = 100,
      message = "Il nome della ricetta deve essere tra 1 e 100 caratteri") String getNome() {
    return nome;
  }

  /**
   * Imposta il nome della ricetta.
   *
   * @param nome il nome da impostare
   */
  public void setNome(
      @NotBlank(message = "Il campo 'Nome della ricetta' è obbligatorio")
          @Size(
              min = 1,
              max = 100,
              message = "Il nome della ricetta deve essere tra 1 e 100 caratteri")
          String nome) {
    this.nome = nome;
  }

  /**
   * Restituisce l'elenco degli ingredienti della ricetta.
   *
   * @return l'elenco degli ingredienti
   */
  public @NotNull(message = "L'elenco ingredienti non può essere nullo") @NotEmpty(
      message = "L'elenco ingredienti non può essere vuoto") List<String> getIngredienti() {
    return ingredienti;
  }

  /**
   * Imposta l'elenco degli ingredienti della ricetta.
   *
   * @param ingredienti l'elenco degli ingredienti da impostare
   */
  public void setIngredienti(
      @NotNull(message = "L'elenco ingredienti non può essere nullo")
          @NotEmpty(message = "L'elenco ingredienti non può essere vuoto")
          List<String> ingredienti) {
    this.ingredienti = ingredienti;
  }

  /**
   * Restituisce le istruzioni per preparare la ricetta.
   *
   * @return le istruzioni della ricetta
   */
  public @NotBlank(message = "Il campo 'Istruzioni' è obbligatorio") @Size(
      max = 5000,
      message = "Le istruzioni non possono superare i 5000 caratteri") String getIstruzioni() {
    return istruzioni;
  }

  /**
   * Imposta le istruzioni per preparare la ricetta.
   *
   * @param istruzioni le istruzioni da impostare
   */
  public void setIstruzioni(
      @NotBlank(message = "Il campo 'Istruzioni' è obbligatorio")
          @Size(max = 5000, message = "Le istruzioni non possono superare i 5000 caratteri")
          String istruzioni) {
    this.istruzioni = istruzioni;
  }

  /**
   * Restituisce la categoria della ricetta.
   *
   * @return la categoria della ricetta
   */
  public @NotNull(message = "Seleziona una categoria valida per la ricetta") CategoriaRicetta
      getCategoria() {
    return categoria;
  }

  /**
   * Imposta la categoria della ricetta.
   *
   * @param categoria la categoria da impostare
   */
  public void setCategoria(
      @NotNull(message = "Seleziona una categoria valida per la ricetta")
          CategoriaRicetta categoria) {
    this.categoria = categoria;
  }

  /**
   * Restituisce l'URL o il percorso dell'immagine della ricetta.
   *
   * @return l'URL dell'immagine
   */
  public @Pattern(
      regexp = ".*\\.(jpg|png)$",
      message = "Formato immagine non supportato. Carica un file in formato JPG o PNG") String
      getImg() {
    return img;
  }

  /**
   * Imposta l'URL o il percorso dell'immagine della ricetta.
   *
   * @param img l'URL da impostare
   */
  public void setImg(
      @Pattern(
              regexp = ".*\\.(jpg|png)$",
              message = "Formato immagine non supportato. Carica un file in formato JPG o PNG")
          String img) {
    this.img = img;
  }

  /**
   * Restituisce la quantità per persona della ricetta.
   *
   * @return la quantità per persona
   */
  @Min(value = 1, message = "La quantità per persona deve essere almeno 1")
  public int getQuantitaPerPersona() {
    return quantitaPerPersona;
  }

  /**
   * Imposta la quantità per persona della ricetta.
   *
   * @param quantitaPerPersona la quantità da impostare
   */
  public void setQuantitaPerPersona(
      @Min(value = 1, message = "La quantità per persona deve essere almeno 1")
          int quantitaPerPersona) {
    this.quantitaPerPersona = quantitaPerPersona;
  }

  /**
   * Restituisce l'autore della ricetta.
   *
   * @return l'autore della ricetta
   */
  public Utente getAutore() {
    return autore;
  }

  /**
   * Imposta l'autore della ricetta.
   *
   * @param autore l'autore da impostare
   */
  public void setAutore(Utente autore) {
    this.autore = autore;
  }

  /**
   * Restituisce le segnalazioni associate alla ricetta.
   *
   * @return l'elenco delle segnalazioni
   */
  public List<SegnalazioneRicetta> getSegnalazioniRicetta() {
    return segnalazioniRicetta;
  }

  /**
   * Imposta le segnalazioni per la ricetta.
   *
   * @param segnalazioniRicetta l'elenco delle segnalazioni da impostare
   */
  public void setSegnalazioniRicetta(List<SegnalazioneRicetta> segnalazioniRicetta) {
    this.segnalazioniRicetta = segnalazioniRicetta;
  }
}
