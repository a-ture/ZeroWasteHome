package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.GestorePagamento;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per l'entità {@link GestorePagamento}. Estende {@link JpaRepository} per fornire
 * metodi CRUD predefiniti per la gestione della tabella "gestore_pagamenti" nel database.
 *
 * <p>La classe definisce un metodo di ricerca personalizzato per recuperare un {@link
 * GestorePagamento} in base alla sua email {@code email}.
 *
 * @author Benito Farina
 */
public interface GestorePagamentoRepository extends JpaRepository<GestorePagamento, String> {

  /**
   * Trova un gestore di pagamento per email.
   *
   * <p>Questo metodo è un'implementazione di ricerca personalizzata che restituisce un {@link
   * Optional} di {@link GestorePagamento}, per gestire i casi in cui l'entità non venga trovata nel
   * database.
   *
   * @param email l'email del gestore di pagamento
   * @return un {@link Optional} contenente il {@link GestorePagamento} trovato o vuoto se non
   *     trovato
   */
  public Optional<GestorePagamento> findByEmail(String email);
}
