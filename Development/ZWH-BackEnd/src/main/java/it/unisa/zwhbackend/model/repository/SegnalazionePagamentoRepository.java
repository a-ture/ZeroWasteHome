package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.SegnalazionePagamento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per l'entità {@link SegnalazionePagamento}. Estende {@link JpaRepository} per fornire
 * metodi CRUD predefiniti per la gestione della tabella "segnalazione_pagamento" nel database.
 *
 * <p>La classe definisce un metodo di ricerca personalizzato per recuperare una {@link
 * SegnalazionePagamento} in base al suo identificativo {@code id}.
 *
 * @author Benito Farina
 */
public interface SegnalazionePagamentoRepository
    extends JpaRepository<SegnalazionePagamento, Long> {

  /**
   * Trova una segnalazione di pagamento per ID.
   *
   * <p>Questo metodo è un'implementazione di ricerca personalizzata che restituisce un {@link
   * Optional} di {@link SegnalazionePagamento}, per gestire i casi in cui l'entità non venga
   * trovata nel database.
   *
   * @param id l'identificativo della segnalazione di pagamento
   * @return un {@link Optional} contenente la {@link SegnalazionePagamento} trovata o vuoto se non
   *     trovata
   */
  public Optional<SegnalazionePagamento> findById(Long id);

  public List<SegnalazionePagamento> findAll();
}
