package it.unisa.zwhbackend.service.gestioneProdotti;

import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio per la ricerca dei prodotti per nome. Fornisce la logica di business
 * per effettuare ricerche di prodotti nel database in base a un criterio di ricerca parziale sul
 * nome.
 *
 * @author Alessandra Trotta
 */
@Service
public class GestioneRicercaProdottiPerNomeService implements RicercaProdottiPerNomeService {

  private final ProdottoRepository prodottoRepository;

  /**
   * Costruttore della classe. Inietta il repository dei prodotti necessario per le operazioni di
   * persistenza e ricerca.
   *
   * @param prodottoRepository il repository per la gestione dei prodotti
   */
  public GestioneRicercaProdottiPerNomeService(ProdottoRepository prodottoRepository) {
    this.prodottoRepository = prodottoRepository;
  }

  /**
   * Esegue la ricerca di prodotti in base a un criterio parziale sul nome.
   *
   * <p>Verifica che il nome fornito non sia nullo o vuoto prima di procedere alla ricerca. Utilizza
   * un'operazione case-insensitive per cercare prodotti il cui nome contenga la stringa fornita.
   *
   * @param name la stringa da cercare nei nomi dei prodotti
   * @return una lista di prodotti che soddisfano il criterio di ricerca
   * @throws IllegalArgumentException se il nome fornito è nullo o vuoto
   */
  @Override
  public List<Prodotto> RicercaPerNome(String name) {
    // Verifica che il nome non sia vuoto o nullo
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Il campo di ricerca non può essere vuoto");
    }

    // Esegui la ricerca dei prodotti che contengono il nome inserito (come operazione di ricerca
    // parziale)
    return prodottoRepository.findByNameContainingIgnoreCase(name);
  }
}
