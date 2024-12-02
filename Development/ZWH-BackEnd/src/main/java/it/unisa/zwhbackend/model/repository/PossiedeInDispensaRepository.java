package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.PossiedeInDispensa;
import it.unisa.zwhbackend.model.entity.Utente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per l'entità {@code PossiedeInDispensa}.
 *
 * <p>Questo repository estende {@code JpaRepository} e fornisce metodi per l'accesso e la gestione
 * dei dati relativi alla relazione tra gli utenti e i prodotti nella loro dispensa. Include un
 * metodo personalizzato per trovare una relazione specifica tra un utente e un prodotto.
 *
 * @author Ferdinando
 */
public interface PossiedeInDispensaRepository
    extends JpaRepository<PossiedeInDispensa, PossiedeInDispensa.PossiedeInDispensaId> {

  /**
   * Trova una relazione {@code PossiedeInDispensa} per un dato utente e un prodotto specifico.
   *
   * <p>Questo metodo restituisce un {@code Optional} che può contenere la relazione se esiste un
   * record che associa l'utente con il prodotto nella dispensa, altrimenti restituisce un {@code
   * Optional.empty()}. Il metodo è utile per verificare se un dato utente possiede un determinato
   * prodotto nella sua dispensa.
   *
   * @param utente l'utente da cercare
   * @param prodotto il prodotto da cercare
   * @return un {@code Optional<PossiedeInDispensa>} che contiene la relazione se esiste
   */
  List<PossiedeInDispensa> findByUtente(Utente utente);
}
