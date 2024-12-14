package it.unisa.zwhbackend.service.gestioneRicettePianoAlimentare;

import it.unisa.zwhbackend.annotations.ExcludeGeneratedFromCodeCoverage;
import it.unisa.zwhbackend.model.entity.Ricetta;
import it.unisa.zwhbackend.model.repository.RicettaRepository;
import it.unisa.zwhbackend.model.repository.UtenteRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Servizio per la gestione delle ricette nel sistema.
 *
 * <p>Contiene la logica di business per la creazione, lettura, aggiornamento e eliminazione delle
 * ricette. Inoltre, gestisce il blocco degli utenti in caso di segnalazione delle ricette.
 *
 * @author Anna Tagliamonte
 */
@Service
public class GestioneRicetteService implements RicettaService {

  private final RicettaRepository ricettaRepository;
  private final UtenteRepository utenteRepository;

  @Autowired
  public GestioneRicetteService(
      RicettaRepository ricettaRepository, UtenteRepository utenteRepository) {
    this.ricettaRepository = ricettaRepository;
    this.utenteRepository = utenteRepository;
  }

  /**
   * Aggiunge una nuova ricetta al sistema.
   *
   * @param ricetta la ricetta da aggiungere
   * @return la ricetta appena aggiunta
   */
  @Override
  public Ricetta aggiungiRicetta(Ricetta ricetta) {
    return ricettaRepository.save(ricetta);
  }

  /**
   * Restituisce tutte le ricette presenti nel sistema.
   *
   * @return un elenco di tutte le ricette
   */
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public List<Ricetta> getAllRicette() {
    return ricettaRepository.findAll();
  }

  /**
   * Restituisce una ricetta specifica dato il suo ID.
   *
   * @param id l'ID della ricetta da recuperare
   * @return una ricetta se esiste
   */
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public Optional<Ricetta> getRicettaById(Long id) {
    return ricettaRepository.findById(id);
  }

  /**
   * Aggiorna una ricetta esistente.
   *
   * @param id l'ID della ricetta da aggiornare
   * @param nuovaRicetta i nuovi dettagli della ricetta
   * @return la ricetta aggiornata
   */
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public Ricetta aggiornaRicetta(Long id, Ricetta nuovaRicetta) {
    Ricetta ricettaEsistente =
        ricettaRepository
            .findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Ricetta non trovata"));
    ricettaEsistente.setNome(nuovaRicetta.getNome());
    ricettaEsistente.setIstruzioni(nuovaRicetta.getIstruzioni());
    ricettaEsistente.setCategoria(nuovaRicetta.getCategoria());
    return ricettaRepository.save(ricettaEsistente);
  }

  /**
   * Elimina una ricetta specifica dato il suo ID.
   *
   * @param id l'ID della ricetta da eliminare
   */
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public void eliminaRicetta(Long id) {
    ricettaRepository.deleteById(id);
  }

  // Metodo per ottenere tutte le ricette di un determinato utente
  @Override
  @ExcludeGeneratedFromCodeCoverage
  public List<Ricetta> getRicetteByUtente(String email) {
    return ricettaRepository.findByAutore_Email(email);
  }
}
