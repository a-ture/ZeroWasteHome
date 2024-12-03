package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Classe che rappresenta la lista degli utenti bloccati.
 *
 * <p>Questa classe mappa la tabella "lista_bloccati" nel database e tiene traccia degli utenti
 * bloccati, includendo l'ID dell'utente e la data di blocco.
 *
 * @author Giovanni Balzano
 */
@Entity
@Table(name = "lista_bloccati") // Mappa la tabella 'lista_bloccati' nel database
public class ListaBloccati {

  /**
   * Identificatore univoco della voce nella lista dei bloccati.
   *
   * <p>La colonna è definita come chiave primaria {@code @Id} e il valore viene generato
   * automaticamente tramite {@code @GeneratedValue}.
   */
  @Id // Definisce la chiave primaria della tabella
  @GeneratedValue(
      strategy =
          GenerationType.IDENTITY) // La chiave primaria viene generata automaticamente dal database
  private Long id;

  /**
   * Data di blocco.
   *
   * <p>Questo campo è obbligatorio e memorizza la data in cui l'utente è stato bloccato. Usa {@code
   * LocalDate} per rappresentare solo la data (senza orario).
   */
  @Column(nullable = false) // La colonna è obbligatoria (non può essere null)
  private LocalDate dataBlocco; // Memorizza la data in cui l'utente è stato bloccato

  /** Relazione molti-a-uno con l'entità Utente. Un blocco è associato a un singolo utente. */
  @ManyToOne // Relazione molti-a-uno con l'entità Utente
  @JoinColumn(
      name = "utente_id",
      nullable = false) // Collega questa entità a quella di Utente tramite la colonna 'utente_id'
  private Utente
      utente; // La proprietà 'utente' fa riferimento all'entità Utente associata a questo blocco

  /**
   * Metodo getter per l'ID del blocco.
   *
   * @return L'ID univoco del blocco
   */
  public Long getId() {
    return id;
  }

  /**
   * Metodo setter per l'ID del blocco.
   *
   * @param id Il nuovo ID da assegnare al blocco
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Metodo getter per la data di blocco.
   *
   * @return La data in cui l'utente è stato bloccato
   */
  public LocalDate getDataBlocco() {
    return dataBlocco;
  }

  /**
   * Metodo setter per la data di blocco.
   *
   * @param dataBlocco La nuova data di blocco
   */
  public void setDataBlocco(LocalDate dataBlocco) {
    this.dataBlocco = dataBlocco;
  }

  /**
   * Metodo getter per l'utente bloccato.
   *
   * @return L'utente associato al blocco
   */
  public Utente getUtente() {
    return utente;
  }

  /**
   * Metodo setter per l'utente bloccato.
   *
   * @param utente L'utente da associare al blocco
   */
  public void setUtente(Utente utente) {
    this.utente = utente;
  }
}
