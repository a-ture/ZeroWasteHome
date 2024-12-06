package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.PossiedeInFrigo;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.Utente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per l'entità {@code PossiedeInFrigo}.
 *
 * <p>Questo repository estende {@code JpaRepository} e fornisce metodi per l'accesso e la gestione
 * dei dati relativi alla relazione tra gli utenti e i prodotti nel loro frigo. Include un metodo
 * personalizzato per trovare una relazione specifica tra un utente e un prodotto.
 *
 * @author Marco Meglio
 */
public interface PossiedeInFrigoRepository
    extends JpaRepository<PossiedeInFrigo, PossiedeInFrigo.PossiedeInFrigoId> {

  /**
   * Trova una relazione {@code PossiedeInFrigo} per un dato utente e un prodotto specifico.
   *
   * <p>Questo metodo restituisce un {@code Optional} che può contenere la relazione se esiste un
   * record che associa l'utente con il prodotto nel frigo, altrimenti restituisce un {@code
   * Optional.empty()}. Il metodo è utile per verificare se un dato utente possiede un determinato
   * prodotto nel suo frigo.
   *
   * @param utente l'utente da cercare
   * @param prodotto il prodotto da cercare
   * @return un {@code Optional<PossiedeInFrigo>} che contiene la relazione se esiste
   */
  List<PossiedeInFrigo> findByUtenteAndProdotto(Utente utente, Prodotto prodotto);

  /** restituisce tutte le occorrenze della tabella PossiedeInFrigo dato uno userId */
  List<PossiedeInFrigo> findByUtenteId(long utenteId);
}
