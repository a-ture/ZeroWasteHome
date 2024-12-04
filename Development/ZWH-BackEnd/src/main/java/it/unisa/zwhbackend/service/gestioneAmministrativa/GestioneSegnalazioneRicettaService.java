package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.GestoreCommunity;
import it.unisa.zwhbackend.model.entity.Ricetta;
import it.unisa.zwhbackend.model.entity.SegnalazioneRicetta;
import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import it.unisa.zwhbackend.model.repository.GestoreCommunityRepository;
import it.unisa.zwhbackend.model.repository.RicettaRepository;
import it.unisa.zwhbackend.model.repository.SegnalazioneRicettaRepository;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servizio per gestire la risoluzione delle segnalazioni nel sistema.
 *
 * <p>Questa classe implementa la logica di business per risolvere una segnalazione, aggiornando il
 * suo stato e persistendo i cambiamenti nel database.
 *
 * @author Giovanni Balzano
 */
@Service
public class GestioneSegnalazioneRicettaService implements SegnalazioneRicettaService {

  private final SegnalazioneRicettaRepository segnalazioneRicettaRepository;
  private final RicettaRepository ricettaRepository;
  private final GestoreCommunityRepository gestoreRepository;

  /**
   * Costruttore per iniettare i repository necessari per la gestione delle segnalazioni delle
   * ricette.
   *
   * <p>Il repository delle segnalazioni viene utilizzato per accedere e modificare i dati delle
   * segnalazioni nel database. Il repository delle ricette consente di interagire con i dati delle
   * ricette, mentre il repository dei gestori della community gestisce le informazioni relative
   * agli utenti della piattaforma.
   *
   * @param segnalazioneRicettaRepository il repository per la gestione delle segnalazioni delle
   *     ricette
   * @param ricettaRepository il repository per la gestione delle ricette
   * @param gestoreRepository il repository per la gestione dei gestori della community
   */
  public GestioneSegnalazioneRicettaService(
      SegnalazioneRicettaRepository segnalazioneRicettaRepository,
      RicettaRepository ricettaRepository,
      GestoreCommunityRepository gestoreRepository) {
    this.segnalazioneRicettaRepository = segnalazioneRicettaRepository;
    this.ricettaRepository = ricettaRepository;
    this.gestoreRepository = gestoreRepository;
  }

  /**
   * Risolve una segnalazione, aggiornando il suo stato e persistendo il cambiamento nel database.
   *
   * <p>Se la segnalazione esiste, lo stato viene aggiornato a "RISOLTO" e la segnalazione viene
   * salvata nuovamente. Se la segnalazione non viene trovata, viene restituito un messaggio di
   * errore.
   *
   * @param segnalazioneId l'ID della segnalazione da risolvere
   * @return un messaggio che indica se l'operazione è stata completata con successo o se ci sono
   *     errori
   */
  @Override
  public String risolviSegnalazione(Long segnalazioneId, Long gestore_id) {
    // Recupera la segnalazioneRicetta dal repository tramite il suo ID
    Optional<SegnalazioneRicetta> optionalSegnalazione =
        segnalazioneRicettaRepository.findById(segnalazioneId);
    Optional<Ricetta> optionalRicetta = null;
    Optional<GestoreCommunity> optionalGestoreCommunity = gestoreRepository.findById(gestore_id);
    // Verifica se la segnalazioneRicetta esiste nel database
    if (optionalSegnalazione.isEmpty()) {
      return "SegnalazioneRicetta non trovata."; // Restituisce un errore se la segnalazioneRicetta
      // non esiste
    }
    if (optionalGestoreCommunity.isEmpty()) {
      return "Gestore non trovato."; // Restituisce un errore se il gestore non esiste
    }

    // Ottiene la segnalazioneRicetta
    SegnalazioneRicetta segnalazioneRicetta = optionalSegnalazione.get();
    GestoreCommunity gestoreCommunity = optionalGestoreCommunity.get();
    optionalRicetta = ricettaRepository.findById(segnalazioneRicetta.getId());
    Ricetta ricetta = optionalRicetta.get();

    // Aggiorna lo stato della segnalazioneRicetta a "RISOLTO"
    segnalazioneRicetta.setStato(StatoSegnalazione.RISOLTO); // Imposta lo stato a "RISOLTO"
    segnalazioneRicetta.setGestoreAssociato(gestoreCommunity);
    ricettaRepository.delete(ricetta);
    // Salva la segnalazioneRicetta aggiornata nel database
    segnalazioneRicettaRepository.save(
        segnalazioneRicetta); // Questo eseguirà un'operazione di update sull'entità

    // Restituisce un messaggio di successo
    return "SegnalazioneRicetta risolta con successo. Autore bloccato.";
  }
}
