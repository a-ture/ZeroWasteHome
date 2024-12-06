package it.unisa.zwhbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta una lista della spesa. Collegata a un utente tramite una relazione
 * uno-a-uno.
 */
@Entity
@Table(name = "lista_spesa")
public class ListaSpesa {

  /** Identificatore univoco della lista della spesa. */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Riferimento all'utente che ha creato la lista della spesa.
   *
   * <p>Relazione {@code OneToOne} con l'entità Utente, rappresenta l'utente a cui appartiene la
   * lista della spesa.
   */
  @OneToOne
  @JoinColumn(
      name = "utente_id",
      nullable = false,
      unique = true) // Chiave esterna unica per garantire la relazione 1:1
  @JsonManagedReference
  private Utente utente;

  /** Lista dei prodotti inclusi nella lista della spesa. */
  @ManyToMany
  @JoinTable(
      name = "comprende",
      joinColumns = @JoinColumn(name = "shopping_list_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Prodotto> products = new ArrayList<Prodotto>();

  /** Data di creazione della lista della spesa. */
  private Date dataCreazione;

  // Getter e Setter

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Utente getUtente() {
    return utente;
  }

  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  public List<Prodotto> getProducts() {
    return products;
  }

  public void setProducts(List<Prodotto> products) {
    this.products = products;
  }

  public Date getDataCreazione() {
    return dataCreazione;
  }

  public void setDataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  @Override
  public String toString() {
    return "ListaSpesa{" + "products=" + products + '}';
  }
}
