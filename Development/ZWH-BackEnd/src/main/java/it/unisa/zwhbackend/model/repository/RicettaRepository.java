package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.Ricetta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaccia per la gestione dell'accesso ai dati dell'entità {@link Ricetta}.
 *
 * <p>Fornisce metodi CRUD predefiniti tramite l'estensione di {@link JpaRepository}. Contiene anche
 * metodi personalizzati per verificare l'esistenza di una ricetta tramite ID.
 *
 * @author Anna Tagliamonte
 */
@Repository
public interface RicettaRepository extends JpaRepository<Ricetta, Long> {

  /**
   * Verifica se esiste una ricetta con l'ID specificato.
   *
   * @param id l'ID della ricetta da verificare
   * @return {@code true} se una ricetta con l'ID specificato esiste, altrimenti {@code false}
   */
  boolean existsById(Long id);

  // Metodo che trova tutte le ricette di un determinato autore
  List<Ricetta> findByAutore_Email(String email);
}
