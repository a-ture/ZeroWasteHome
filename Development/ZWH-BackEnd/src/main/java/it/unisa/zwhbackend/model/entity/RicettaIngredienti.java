package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;

/**
 * Classe che rappresenta la relazione tra una ricetta e i suoi ingredienti nel sistema.
 *
 * <p>Mappa la tabella "ricetta_ingredienti" nel database. Ogni record della tabella rappresenta
 * un'associazione tra una ricetta e un singolo ingrediente.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database.
 *
 * @author Anna Tagliamonte
 */
@Entity
@Table(name = "ricetta_ingredienti")
public class RicettaIngredienti {

  /**
   * Identificatore univoco della relazione tra una ricetta e un ingrediente.
   *
   * <p>Annota il campo con {@code @Id} per indicare che è la chiave primaria. Usa
   * {@code @GeneratedValue} con {@code GenerationType.IDENTITY} per l'auto-incremento nel database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Riferimento alla ricetta associata a questo ingrediente.
   *
   * <p>Usa {@code @ManyToOne} per definire la relazione molti-a-uno con l'entità {@code Ricetta}.
   * Il campo è obbligatorio e specifica il vincolo di join sulla colonna "ricetta_id".
   */
  @ManyToOne
  @JoinColumn(name = "ricetta_id", nullable = false)
  private Ricetta ricetta;

  /**
   * Nome dell'ingrediente associato alla ricetta.
   *
   * <p>Questo campo è obbligatorio e rappresenta un singolo ingrediente della ricetta. Annota il
   * campo con {@code @Column} per definire il vincolo di non null nel database.
   */
  @Column(name = "ingrediente", nullable = false)
  private String ingrediente;

  // Costruttori, getter e setter

  /** Costruttore predefinito. */
  public RicettaIngredienti() {}

  /**
   * Costruttore con parametri.
   *
   * @param ricetta la ricetta associata
   * @param ingrediente il nome dell'ingrediente
   */
  public RicettaIngredienti(Ricetta ricetta, String ingrediente) {
    this.ricetta = ricetta;
    this.ingrediente = ingrediente;
  }

  /**
   * Restituisce l'identificatore della relazione.
   *
   * @return l'identificatore univoco
   */
  public Long getId() {
    return id;
  }

  /**
   * Imposta l'identificatore della relazione.
   *
   * @param id l'identificatore univoco
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Restituisce la ricetta associata.
   *
   * @return la ricetta associata
   */
  public Ricetta getRicetta() {
    return ricetta;
  }

  /**
   * Imposta la ricetta associata.
   *
   * @param ricetta la ricetta da associare
   */
  public void setRicetta(Ricetta ricetta) {
    this.ricetta = ricetta;
  }

  /**
   * Restituisce il nome dell'ingrediente.
   *
   * @return il nome dell'ingrediente
   */
  public String getIngrediente() {
    return ingrediente;
  }

  /**
   * Imposta il nome dell'ingrediente.
   *
   * @param ingrediente il nome dell'ingrediente da associare
   */
  public void setIngrediente(String ingrediente) {
    this.ingrediente = ingrediente;
  }
}
