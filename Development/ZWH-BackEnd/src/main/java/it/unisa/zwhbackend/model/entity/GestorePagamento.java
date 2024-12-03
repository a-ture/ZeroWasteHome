package it.unisa.zwhbackend.model.entity;

import jakarta.persistence.*;

/**
 * Classe che rappresenta un'entità GestorePagamento nel sistema. Mappa la tabella
 * "gestore_pagamenti" nel database.
 *
 * <p>Annota l'entità con {@code @Entity} per indicare che è una classe JPA. Usa {@code @Table} per
 * specificare il nome della tabella nel database. Usa {@code @Data} di Lombok per generare
 * automaticamente i metodi getter, setter, toString, equals e hashCode.
 *
 * @author Benito Farina
 */
@Entity
@Table(name = "gestore_pagamenti")
public class GestorePagamento {

  /**
   * Identificatore univoco del gestore del pagamento.
   *
   * <p>Annota il campo con {@code @Id} per indicare che è la chiave primaria. Usa
   * {@code @GeneratedValue} con {@code GenerationType.IDENTITY} per l'auto-incremento nel database.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * Nome del gestore del pagamento.
   *
   * <p>Questo campo è obbligatorio e non può essere nullo nel database. Annota il campo con
   * {@code @Column} per definire il vincolo di non nullità nel database.
   */
  @Column(nullable = false)
  private String nome;

  /**
   * Email del gestore del pagamento.
   *
   * <p>Questo campo è obbligatorio e deve essere univoco nel database. Annota il campo con
   * {@code @Column} per definire il vincolo di unicità nel database.
   */
  @Column(nullable = false, unique = true)
  private String email;

  public GestorePagamento(Long id, String nome, String email) {
    this.id = id;
    this.nome = nome;
    this.email = email;
  }

  public GestorePagamento() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNome() {
    return nome;
  }

  public void setNome(String nome) {
    this.nome = nome;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
