package it.unisa.zwhbackend.model.repository;

import it.unisa.zwhbackend.model.entity.Prodotto;
import java.util.List;
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

  /**
   * Trova i prodotti il cui nome contiene una determinata stringa, ignorando la distinzione tra
   * maiuscole e minuscole.
   *
   * <p>Questo metodo restituisce una lista di prodotti il cui nome contiene, in modo parziale o
   * completo, la stringa specificata, ignorando le differenze tra maiuscole e minuscole. È utile
   * per implementare ricerche flessibili basate sul nome dei prodotti.
   *
   * @param name la stringa da cercare nel nome dei prodotti
   * @return una lista di {@code Prodotto} che soddisfano il criterio di ricerca
   */
  List<Prodotto> findByNameContainingIgnoreCase(String name);
}
