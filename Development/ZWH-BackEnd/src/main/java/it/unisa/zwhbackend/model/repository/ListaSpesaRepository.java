package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità ListaSpesa.
 *
 * <p>Interfaccia che estende {@code JpaRepository} per la gestione delle operazioni CRUD
 * sull'entità ListaSpesa nel database. Include il metodo {@code findByUtenteId} per recuperare le
 * liste della spesa per un utente specifico.
 *
 * @author Giuseppe Russo
 */
@Repository
public interface ListaSpesaRepository extends JpaRepository<ListaSpesa, Long> {
  /**
   * Recupera tutte le liste della spesa associate a un utente.
   *
   * @param utenteId ID dell'utente per cui si vogliono recuperare le liste della spesa.
   * @return lista delle liste della spesa associate all'utente.
   */
  List<ListaSpesa> findByUtenteId(Long utenteId);
}
