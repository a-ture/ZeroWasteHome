package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.List;
import lombok.Data;
import lombok.Setter;

/**
 * Classe che rappresenta una lista della spesa. Collegata a un utente tramite una relazione
 * molti-a-uno.
 *
 * <p>Questa classe mappa la tabella "lista_spesa" nel database e rappresenta una lista della spesa
 * associata a un utente. Include la relazione {@code ManyToOne} per collegarsi all'entità Utente e
 * la relazione {@code ManyToMany} per i prodotti inclusi nella lista.
 *
 * @author Giuseppe Russo
 */
@Setter
@Entity
@Data
@Table(name = "lista_spesa")
public class ListaSpesa {

  /**
   * Identificatore univoco della lista della spesa.
   *
   * <p>Annotato con {@code @Id} per indicare la chiave primaria e con {@code @GeneratedValue}
   * per l'auto-incremento nel database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Riferimento all'utente che ha creato la lista della spesa.
   *
   * <p>Relazione {@code ManyToOne} con l'entità Utente, rappresenta l'utente a cui appartiene la lista
   * della spesa.
   */
  @ManyToOne
  @JoinColumn(name = "utente_id", nullable = false)
  private Utente utente;

  /**
   * Lista dei prodotti inclusi nella lista della spesa.
   *
   * <p>Relazione {@code ManyToMany} con l'entità Prodotto, rappresenta i prodotti che fanno parte della lista.
   */
  @ManyToMany
  @JoinTable(
          name = "comprende",
          joinColumns = @JoinColumn(name = "shopping_list_id"),
          inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Prodotto> products;

  /**
   * Data di creazione della lista della spesa.
   */
  private Date dataCreazione;
}
