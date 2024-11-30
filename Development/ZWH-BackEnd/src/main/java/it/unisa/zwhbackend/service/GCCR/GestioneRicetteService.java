package it.unisa.zwhbackend.service.GCCR;

import it.unisa.zwhbackend.model.entity.Ricetta;
import it.unisa.zwhbackend.model.repository.RicettaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementazione del servizio per la gestione delle ricette.
 *
 * <p>Fornisce i metodi per aggiungere, ottenere, aggiornare ed eliminare ricette.
 *
 * @author Anna Tagliamonte
 */
@Service
public class GestioneRicetteService implements RicettaService {

  private final RicettaRepository ricettaRepository;

  /**
   * Costruttore della classe.
   *
   * <p>Inietta il repository necessario per le operazioni di persistenza.
   *
   * @param ricettaRepository il repository per la gestione delle ricette
   */
  @Autowired
  public GestioneRicetteService(RicettaRepository ricettaRepository) {
    this.ricettaRepository = ricettaRepository;
  }

  @Override
  public Ricetta aggiungiRicetta(Ricetta ricetta) {
    return ricettaRepository.save(ricetta);
  }

  /**
   * Restituisce tutte le ricette presenti nel sistema.
   *
   * @return un elenco di tutte le ricette
   */
  public List<Ricetta> getAllRicette() {
    return ricettaRepository.findAll();
  }

  /**
   * Restituisce una ricetta specifica dato il suo ID.
   *
   * @param id l'ID della ricetta da recuperare
   * @return un oggetto Optional che contiene la ricetta se esiste
   */
  public Optional<Ricetta> getRicettaById(Long id) {
    return ricettaRepository.findById(id);
  }

  /**
   * Aggiorna i dettagli di una ricetta esistente.
   *
   * @param id l'ID della ricetta da aggiornare
   * @param nuovaRicetta i nuovi dettagli della ricetta
   * @return la ricetta aggiornata
   * @throws EntityNotFoundException se la ricetta con l'ID specificato non esiste
   */
  public Ricetta aggiornaRicetta(Long id, Ricetta nuovaRicetta) {
    return ricettaRepository
        .findById(id)
        .map(
            ricettaEsistente -> {
              ricettaEsistente.setNome(nuovaRicetta.getNome());
              ricettaEsistente.setIngredienti(nuovaRicetta.getIngredienti());
              ricettaEsistente.setIstruzioni(nuovaRicetta.getIstruzioni());
              ricettaEsistente.setCategoria(nuovaRicetta.getCategoria());
              ricettaEsistente.setImg(nuovaRicetta.getImg());
              return ricettaRepository.save(ricettaEsistente);
            })
        .orElseThrow(() -> new EntityNotFoundException("Ricetta con ID " + id + " non trovata"));
  }

  /**
   * Elimina una ricetta specifica dato il suo ID.
   *
   * @param id l'ID della ricetta da eliminare
   * @throws EntityNotFoundException se la ricetta con l'ID specificato non esiste
   */
  public void eliminaRicetta(Long id) {
    if (!ricettaRepository.existsById(id)) {
      throw new EntityNotFoundException("Ricetta con ID " + id + " non trovata");
    }
    ricettaRepository.deleteById(id);
  }
}
