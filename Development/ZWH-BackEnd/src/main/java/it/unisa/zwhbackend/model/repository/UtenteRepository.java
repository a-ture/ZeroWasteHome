package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia che definisce le operazioni per la modellazione e l'accesso alle informazioni
 * persistenti relative a un utente
 *
 * @author Alessia Ture
 * @see Utente
 */
public interface UtenteRepository extends JpaRepository<Utente, Long> {
  /**
   * Permette di trovare un utente in base all'email e alla password
   *
   * @param email l'email dell'utente
   * @param password la password dell'utente
   * @return l'utente
   */
  Utente findByEmailAndPassword(String email, String password);

  /**
   * Controlla se l'email è presente nel database
   *
   * @param email l'email da cercare
   * @return true se l'email è presente, false altrimenti
   */
  boolean existsByEmail(String email);

  /**
   * Permette di trovare un utente in base all'email
   *
   * @param email l'email dell'utente
   * @return l'utente
   */
  Utente findByEmail(String email);
}
