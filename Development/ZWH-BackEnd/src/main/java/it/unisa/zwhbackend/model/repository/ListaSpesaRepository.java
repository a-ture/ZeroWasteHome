package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository per l'entità ListaSpesa.
 *
 * <p>Interfaccia che estende {@code JpaRepository} per la gestione delle operazioni CRUD
 * sull'entità ListaSpesa nel database. Include il metodo {@code findByUtenteEmail} per recuperare
 * le liste della spesa per un utente specifico.
 *
 * @author Giuseppe Russo
 */
@Repository
public interface ListaSpesaRepository extends JpaRepository<ListaSpesa, Long> {
  /**
   * Recupera la lista della spesa associata a un utente.
   *
   * @param utenteEmail Email dell'utente per cui si vuole recuperare la lista della spesa.
   * @return La lista della spesa associata all'utente, se esiste.
   */
  Optional<ListaSpesa> findByUtenteEmail(String utenteEmail);
}
