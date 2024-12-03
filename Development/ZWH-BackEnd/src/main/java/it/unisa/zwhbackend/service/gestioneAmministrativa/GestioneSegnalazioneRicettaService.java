package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import it.unisa.zwhbackend.model.repository.*;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Servizio per gestire la risoluzione delle segnalazioni nel sistema.
 *
 * <p>Questo servizio implementa la logica di business per la gestione delle segnalazioni, come il
 * blocco degli utenti, l'aggiornamento dello stato delle segnalazioni e la rimozione delle ricette
 * segnalate.
 *
 * @author Giovanni Balzano
 */
@Service
public class GestioneSegnalazioneRicettaService implements SegnalazioneRicettaService {

  // Repository per interagire con le entità del database
  private final SegnalazioneRicettaRepository segnalazioneRicettaRepository;
  private final RicettaRepository ricettaRepository;
  private final GestoreCommunityRepository gestoreRepository;
  private final UtenteRepository utenteRepository;
  private final ListaBloccatiRepository listaBloccatiRepository;

  /**
   * Costruttore per iniettare le dipendenze necessarie.
   *
   * @param segnalazioneRicettaRepository Repository per gestire le segnalazioni
   * @param ricettaRepository Repository per gestire le ricette
   * @param gestoreRepository Repository per gestire i gestori
   * @param utenteRepository Repository per gestire gli utenti
   * @param listaBloccatiRepository Repository per gestire la lista degli utenti bloccati
   */
  public GestioneSegnalazioneRicettaService(
      SegnalazioneRicettaRepository segnalazioneRicettaRepository,
      RicettaRepository ricettaRepository,
      GestoreCommunityRepository gestoreRepository,
      UtenteRepository utenteRepository,
      ListaBloccatiRepository listaBloccatiRepository) {
    this.segnalazioneRicettaRepository = segnalazioneRicettaRepository;
    this.ricettaRepository = ricettaRepository;
    this.gestoreRepository = gestoreRepository;
    this.utenteRepository = utenteRepository;
    this.listaBloccatiRepository = listaBloccatiRepository;
  }

  /**
   * Risolve una segnalazione nel sistema.
   *
   * <p>Blocca l'autore della ricetta, aggiorna lo stato della segnalazione, disassocia la ricetta e
   * la elimina.
   *
   * @param segnalazioneId ID della segnalazione da risolvere
   * @param gestore_id ID del gestore che risolve la segnalazione
   * @return Un messaggio che indica il risultato dell'operazione
   */
  @Override
  public String risolviSegnalazione(Long segnalazioneId, Long gestore_id) {
    try {
      // Recupera la segnalazione dalla repository
      Optional<SegnalazioneRicetta> optionalSegnalazione =
          segnalazioneRicettaRepository.findById(segnalazioneId);
      if (optionalSegnalazione.isEmpty()) {
        return "SegnalazioneRicetta non trovata."; // Se la segnalazione non è trovata
      }

      // Recupera il gestore dalla repository tramite l'ID
      Optional<GestoreCommunity> optionalGestoreCommunity = gestoreRepository.findById(gestore_id);
      if (optionalGestoreCommunity.isEmpty()) {
        return "Gestore non trovato."; // Restituisce un errore se il gestore non è trovato
      }

      // Ottieni la segnalazione e il gestore
      SegnalazioneRicetta segnalazioneRicetta = optionalSegnalazione.get();
      GestoreCommunity gestoreCommunity = optionalGestoreCommunity.get();

      // Verifica se la ricetta associata alla segnalazione esiste
      Optional<Ricetta> optionalRicetta =
          ricettaRepository.findById(segnalazioneRicetta.getRicettaAssociato().getId());
      if (optionalRicetta.isEmpty()) {
        return "Ricetta non trovata con ID: " + segnalazioneRicetta.getRicettaAssociato().getId();
      }

      Ricetta ricetta = optionalRicetta.get();

      // Ottieni l'autore della ricetta
      Utente autore = ricetta.getAutore();
      if (autore == null) {
        return "Autore non trovato per la ricetta."; // Se non esiste l'autore
      }

      // Aggiungi l'utente alla lista dei bloccati
      ListaBloccati bloccato = new ListaBloccati();
      bloccato.setUtente(autore);
      bloccato.setDataBlocco(LocalDate.now());
      listaBloccatiRepository.save(bloccato);

      // Aggiorna lo stato della segnalazione a "RISOLTO"
      segnalazioneRicetta.setStato(StatoSegnalazione.RISOLTO);
      segnalazioneRicetta.setGestoreAssociato(gestoreCommunity);

      // Disassocia la ricetta dalla segnalazione
      segnalazioneRicetta.setRicettaAssociato(null);
      segnalazioneRicettaRepository.save(segnalazioneRicetta); // Salva la segnalazione aggiornata

      // Elimina la ricetta
      ricettaRepository.delete(ricetta); // Elimina la ricetta

      return "Segnalazione risolta con successo. Autore bloccato e ricetta eliminata.";
    } catch (Exception e) {
      System.err.println("Errore durante la risoluzione della segnalazione: " + e.getMessage());
      e.printStackTrace();
      return "Errore interno del server: " + e.getMessage(); // Restituisce un errore 500
    }
  }
}
