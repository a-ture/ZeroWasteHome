package it.unisa.zwhbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un prodotto nel sistema. Mappa la tabella "prodotto" nel database e
 * include regole di validazione per i campi.
 *
 * <p>Questa classe utilizza JPA per la mappatura ORM e fornisce annotazioni di Jakarta Validation
 * per garantire l'integrità dei dati. Include un costruttore vuoto per JPA e costruttori aggiuntivi
 * per la creazione di istanze personalizzate.
 *
 * <p>Annotazioni principali:
 *
 * <ul>
 *   <li>{@code @Entity}: identifica questa classe come entità JPA.
 *   <li>{@code @Table(name = "prodotto")}: specifica il nome della tabella mappata nel database.
 *   <li>{@code @JsonIgnoreProperties}: esclude la proprietà {@code utentiPossessori} dalla
 *       serializzazione JSON.
 * </ul>
 *
 * @author Marco Meglio
 */
@Entity
@Table(name = "prodotto")
@JsonIgnoreProperties({
  "utentiPossessori"
}) // Esclude dalla serializzazione la lista utentiPossessori
public class Prodotto {

  /**
   * ID univoco del prodotto.
   *
   * <p>Mappato come chiave primaria auto-incrementale nel database tramite {@code @Id} e
   * {@code @GeneratedValue}.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment
  @Column(name = "id", nullable = false)
  private Long id;

  /**
   * Codice a barre del prodotto.
   *
   * <p>Identificatore univoco del prodotto, obbligatorio e composto solo da cifre. Deve avere una
   * lunghezza compresa tra 8 e 16 caratteri. La validazione è gestita tramite {@code @Pattern}.
   */
  @Column(name = "codice_barre", nullable = false, unique = true)
  @Pattern(
      regexp = "^[0-9]{8,16}$",
      message =
          "Il codice deve avere una lunghezza minima di 8 caratteri, massima di 16 e deve contenere solo cifre.")
  private String codiceBarre;

  /**
   * Nome del prodotto.
   *
   * <p>Deve contenere solo lettere dell'alfabeto, con una lunghezza massima di 50 caratteri. La
   * validazione è gestita tramite {@code @Pattern}.
   */
  @Column(name = "nome_prodotto")
  @Pattern(
      regexp = "^[a-zA-Z0-9\\s]{1,50}$",
      message =
          "La lunghezza massima per questo campo è 50 caratteri e deve contenere solo lettere dell'alfabeto.")
  private String name;

  /**
   * Lista delle categorie del prodotto.
   *
   * <p>Ogni prodotto può appartenere a una o più categorie (es. 'vegano', 'senza_glutine'). Le
   * categorie sono memorizzate come una collezione di stringhe tramite {@code @ElementCollection}.
   */
  @ElementCollection
  @Column(name = "categoria")
  private List<String> categoria;

  /**
   * Nome dell'immagine del prodotto.
   *
   * <p>Questo campo rappresenta il nome del file immagine associato al prodotto. Viene utilizzato
   * per memorizzare il percorso relativo o il nome del file immagine caricato per il prodotto.
   */
  @Column(name = "img")
  private String img;

  /**
   * Getter per il campo img.
   *
   * @return il nome del file immagine associato al prodotto.
   */
  public String getImg() {
    return img;
  }

  /**
   * Setter per il campo img.
   *
   * @param img il nome del file immagine da associare al prodotto.
   */
  public void setImg(String img) {
    this.img = img;
  }

  /**
   * Lista degli utenti che possiedono il prodotto.
   *
   * <p>Relazione uno-a-molti gestita tramite {@code @OneToMany}. Indica che un prodotto può essere
   * posseduto da più utenti e la relazione è mappata nella classe {@code PossiedeInFrigo}.
   */
  @OneToMany(mappedBy = "prodotto", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PossiedeInFrigo> utentiPossessori = new ArrayList<>();

  /** Costruttore vuoto necessario per JPA. */
  public Prodotto() {
    // Costruttore vuoto richiesto da JPA
  }

  /**
   * Costruttore che inizializza il prodotto con nome, codice a barre e categorie.
   *
   * @param nomeProdotto il nome del prodotto
   * @param codiceBarre il codice a barre del prodotto
   * @param categoria le categorie del prodotto
   */
  public Prodotto(String nomeProdotto, String codiceBarre, List<String> categoria) {
    this.name = nomeProdotto;
    this.codiceBarre = codiceBarre;
    this.categoria = categoria;
  }

  /**
   * Restituisce la lista degli utenti che possiedono il prodotto.
   *
   * @return lista di {@code PossiedeInFrigo} associati al prodotto
   */
  public List<PossiedeInFrigo> getUtentiPossessori() {
    return utentiPossessori;
  }

  /**
   * Imposta la lista degli utenti che possiedono il prodotto.
   *
   * @param utentiPossessori lista di {@code PossiedeInFrigo} da associare al prodotto
   */
  public void setUtentiPossessori(List<PossiedeInFrigo> utentiPossessori) {
    this.utentiPossessori = utentiPossessori;
  }

  /**
   * Restituisce la lista delle categorie del prodotto.
   *
   * @return lista di stringhe rappresentanti le categorie
   */
  public List<String> getCategoria() {
    return categoria;
  }

  /**
   * Imposta la lista delle categorie del prodotto.
   *
   * @param categoria lista di stringhe rappresentanti le categorie
   */
  public void setCategoria(List<String> categoria) {
    this.categoria = categoria;
  }

  /**
   * Restituisce il nome del prodotto.
   *
   * @return il nome del prodotto
   */
  public @Pattern(
      regexp = "^[a-zA-Z0-9\\s]{1,50}$",
      message =
          "La lunghezza massima per questo campo è 50 caratteri e deve contenere solo lettere dell'alfabeto.")
  String getName() {
    return name;
  }

  /**
   * Imposta il nome del prodotto.
   *
   * @param name il nome del prodotto
   */
  public void setName(
      @Pattern(
              regexp = "^[a-zA-Z0-9\\s]{1,50}$",
              message =
                  "La lunghezza massima per questo campo è 50 caratteri e deve contenere solo lettere dell'alfabeto.")
          String name) {
    this.name = name;
  }

  /**
   * Restituisce il codice a barre del prodotto.
   *
   * @return il codice a barre del prodotto
   */
  public String getCodiceBarre() {
    return codiceBarre;
  }

  /**
   * Imposta il codice a barre del prodotto.
   *
   * @param codiceBarre il codice a barre del prodotto
   */
  public void setCodiceBarre(String codiceBarre) {
    this.codiceBarre = codiceBarre;
  }

  public Prodotto(String codiceBarre, String name, List<String> categoria, String img) {
    this.codiceBarre = codiceBarre;
    this.name = name;
    this.categoria = categoria;
    this.img = img;
  }

  /**
   * Restituisce una rappresentazione stringa del prodotto.
   *
   * @return stringa contenente nome, codice a barre e categorie
   */
  @Override
  public String toString() {
    return "Prodotto{"
        + "nome='"
        + name
        + '\''
        + ", codiceBarre='"
        + codiceBarre
        + '\''
        + ", categoria="
        + categoria
        + '}';
  }
}
