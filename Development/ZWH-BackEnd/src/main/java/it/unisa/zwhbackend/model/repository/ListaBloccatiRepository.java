package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.ListaBloccati;
import it.unisa.zwhbackend.model.entity.Utente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaccia per la gestione dell'accesso ai dati dell'entità {@link ListaBloccati}.
 *
 * <p>Fornisce metodi CRUD predefiniti per l'entità {@link ListaBloccati}, che consente di gestire
 * le operazioni di accesso e modifica dei dati relativi agli utenti bloccati nel sistema.
 *
 * <p>Questa interfaccia estende {@link JpaRepository}, quindi beneficia di tutti i metodi
 * predefiniti forniti da JPA per l'interazione con il database.
 *
 * @author Giovanni Balzano
 */
public interface ListaBloccatiRepository extends JpaRepository<ListaBloccati, Long> {

  // Trova tutti i blocchi per un determinato utente
  List<ListaBloccati> findByUtente(Utente utente);
}
