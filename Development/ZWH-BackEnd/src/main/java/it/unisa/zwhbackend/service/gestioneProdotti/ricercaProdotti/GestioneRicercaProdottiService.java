package it.unisa.zwhbackend.service.gestioneProdotti.ricercaProdotti;

import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.repository.ProdottoRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio per la ricerca dei prodotti per nome. Fornisce la logica di business
 * per effettuare ricerche di prodotti nel database in base a un criterio di ricerca parziale sul
 * nome. Gestisce eccezioni per input non valido o assenza di risultati.
 *
 * @author Alessandra Trotta
 */
@Service
public class GestioneRicercaProdottiService implements RicercaProdottiService {

  private final ProdottoRepository prodottoRepository;

  /**
   * Costruttore della classe. Inietta il repository dei prodotti necessario per le operazioni di
   * persistenza e ricerca.
   *
   * @param prodottoRepository il repository per la gestione dei prodotti
   */
  public GestioneRicercaProdottiService(ProdottoRepository prodottoRepository) {
    this.prodottoRepository = prodottoRepository;
  }

  /**
   * Esegue la ricerca di prodotti in base a un criterio parziale sul nome.
   *
   * <p>Verifica che il nome fornito non sia nullo o vuoto prima di procedere alla ricerca. Utilizza
   * un'operazione case-insensitive per cercare prodotti il cui nome contenga la stringa fornita. Se
   * non vengono trovati risultati, viene sollevata un'eccezione.
   *
   * @param name la stringa da cercare nei nomi dei prodotti
   * @return una lista di prodotti che soddisfano il criterio di ricerca
   * @throws IllegalArgumentException se il nome fornito è nullo o vuoto
   * @throws NoSuchElementException se non viene trovato alcun prodotto corrispondente
   */
  @Override
  public List<Prodotto> RicercaPerNome(String name) {
    // Verifica che il nome non sia vuoto o nullo
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Il campo di ricerca non può essere vuoto");
    }

    // Effettua la ricerca dei prodotti
    List<Prodotto> prodotti = prodottoRepository.findByNameContainingIgnoreCase(name);

    // Gestisce il caso in cui non vengano trovati prodotti
    if (prodotti == null || prodotti.isEmpty()) {
      throw new NoSuchElementException("Nessun prodotto trovato");
    }

    return prodotti;
  }
}
