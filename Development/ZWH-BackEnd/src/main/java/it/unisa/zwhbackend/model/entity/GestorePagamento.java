package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Classe che rappresenta un'entità GestorePagamento nel sistema. Mappa la tabella
 * "gestore_pagamenti" nel database.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database.
 *
 * @author Benito Farina
 */
@Entity
@Table(name = "gestore_pagamenti")
public class GestorePagamento {

  /**
   * Nome del gestore dei pagamenti.
   *
   * <p>Questo campo è obbligatorio e non può essere nullo nel database. Annota il campo con
   * {@code @Column} per definire il vincolo di non nullità nel database.
   */
  @NotBlank(message = "Il nome è obbligatorio")
  @Column(nullable = false)
  private String nome;

  /**
   * Email del gestore dei pagamenti.
   *
   * <p>Questo campo è obbligatorio e deve essere univoco nel database. Annota il campo con
   * {@code @Id} per indicare che è la chiave primaria e {@code @Column} per definire il vincolo di
   * unicità nel database.
   */
  @Id
  @NotBlank(message = "L'email è obbligatoria")
  @Email(message = "Inserisci un'email valida")
  @Column(nullable = false, unique = true)
  private String email;

  /**
   * Password del gestore dei pagamenti.
   *
   * <p>Questo campo è obbligatorio e deve avere almeno 8 caratteri. Annota il campo con
   * {@code @NotBlank}, {@code @Size} e {@code Column} per definire le regole di validazione e i
   * vincoli del database.
   */
  @NotBlank(message = "La password è obbligatoria")
  @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
  @Column(nullable = false)
  private String password;

  /**
   * Costruttore per inizializzare un oggetto GestorePagamento con nome, email e password.
   *
   * @param nome il nome del gestore dei pagamenti
   * @param email l'email del gestore dei pagamenti
   * @param password la password del gestore dei pagamenti
   */
  public GestorePagamento(String nome, String email, String password) {
    this.nome = nome;
    this.email = email;
    this.password = password;
  }

  /** Costruttore vuoto per la creazione di oggetti GestorePagamento senza parametri. */
  public GestorePagamento() {
    // costruttore vuoto
  }

  /**
   * Ottiene il nome del gestore dei pagamenti.
   *
   * @return il nome del gestore dei pagamenti
   */
  public String getNome() {
    return nome;
  }

  /**
   * Imposta il nome del gestore dei pagamenti.
   *
   * @param nome il nome del gestore da impostare
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * Ottiene l'email del gestore dei pagamenti.
   *
   * @return l'email del gestore dei pagamenti
   */
  public String getEmail() {
    return email;
  }

  /**
   * Imposta l'email del gestore dei pagamenti.
   *
   * @param email l'email del gestore da impostare
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * Ottiene la password del gestore dei pagamenti.
   *
   * @return la password del gestore dei pagamenti
   */
  public @NotBlank(message = "La password è obbligatoria") @Size(
      min = 8,
      message = "La password deve avere almeno 8 caratteri") String getPassword() {
    return password;
  }

  /**
   * Imposta la password del gestore dei pagamenti.
   *
   * @param password la password del gestore da impostare
   */
  public void setPassword(
      @NotBlank(message = "La password è obbligatoria")
          @Size(min = 8, message = "La password deve avere almeno 8 caratteri")
          String password) {
    this.password = password;
  }
}
