package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.Prodotto;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository per l'entità {@code Prodotto}.
 *
 * <p>Questo repository estende {@code JpaRepository} e fornisce metodi per l'accesso e la gestione
 * dei dati relativi ai prodotti. Include un metodo personalizzato per trovare un prodotto tramite
 * il suo codice a barre.
 *
 * @author Marco Meglio
 */
public interface ProdottoRepository extends JpaRepository<Prodotto, Long> {

  /**
   * Trova un prodotto tramite il suo codice a barre.
   *
   * <p>Questo metodo restituisce un {@code Optional} che può contenere il prodotto se esiste un
   * record che corrisponde al codice a barre specificato, altrimenti restituisce un {@code
   * Optional.empty()}. Il metodo è utile per recuperare i dettagli di un prodotto dato il suo
   * codice a barre.
   *
   * @param codiceBarre il codice a barre del prodotto da cercare
   * @return un {@code Optional<Prodotto>} che contiene il prodotto se esiste
   */
  Optional<Prodotto> findByCodiceBarre(String codiceBarre);
}
