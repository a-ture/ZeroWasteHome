package it.unisa.zwhbackend.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta una lista della spesa. Collegata a un utente tramite una relazione
 * uno-a-uno.
 *
 * @author Giuseppe Russo
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
      name = "utente_email",
      nullable = false,
      unique = true) // Chiave esterna unica per garantire la relazione 1:1
  @JsonManagedReference
  private Utente utente;

  /**
   * Lista dei prodotti inclusi nella lista della spesa.
   *
   * <p>Relazione molti-a-molti con l'entità {@code Prodotto}, che rappresenta i prodotti acquistati
   * dall'utente nella lista della spesa.
   */
  @ManyToMany
  @JoinTable(
      name = "comprende",
      joinColumns = @JoinColumn(name = "shopping_list_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Prodotto> products = new ArrayList<Prodotto>();

  /** Data di creazione della lista della spesa. */
  private Date dataCreazione;

  // Getter e Setter

  /**
   * Ottiene l'identificatore della lista della spesa.
   *
   * @return l'identificatore univoco della lista della spesa
   */
  public Long getId() {
    return id;
  }

  /**
   * Imposta l'identificatore della lista della spesa.
   *
   * @param id l'identificatore univoco da impostare
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Ottiene l'utente associato alla lista della spesa.
   *
   * @return l'utente che ha creato la lista della spesa
   */
  public Utente getUtente() {
    return utente;
  }

  /**
   * Imposta l'utente associato alla lista della spesa.
   *
   * @param utente l'utente da associare alla lista della spesa
   */
  public void setUtente(Utente utente) {
    this.utente = utente;
  }

  /**
   * Ottiene la lista dei prodotti nella lista della spesa.
   *
   * @return la lista dei prodotti inclusi nella lista della spesa
   */
  public List<Prodotto> getProducts() {
    return products;
  }

  /**
   * Imposta i prodotti inclusi nella lista della spesa.
   *
   * @param products la lista di prodotti da aggiungere alla lista della spesa
   */
  public void setProducts(List<Prodotto> products) {
    this.products = products;
  }

  /**
   * Ottiene la data di creazione della lista della spesa.
   *
   * @return la data di creazione della lista della spesa
   */
  public Date getDataCreazione() {
    return dataCreazione;
  }

  /**
   * Imposta la data di creazione della lista della spesa.
   *
   * @param dataCreazione la data di creazione della lista della spesa
   */
  public void setDataCreazione(Date dataCreazione) {
    this.dataCreazione = dataCreazione;
  }

  /**
   * Restituisce una rappresentazione in formato stringa della lista della spesa.
   *
   * @return una stringa che rappresenta la lista della spesa
   */
  @Override
  public String toString() {
    return "ListaSpesa{" + "products=" + products + '}';
  }
}
