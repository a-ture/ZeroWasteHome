package it.unisa.zwhbackend.service.gestioneAmministrativa;

import it.unisa.zwhbackend.model.entity.*;
import it.unisa.zwhbackend.model.enums.StatoSegnalazione;
import it.unisa.zwhbackend.model.repository.*;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private SegnalazioneRicettaRepository
      segnalazioneRicettaRepository; // Repository per le segnalazioni

  @Autowired
  private GestoreCommunityRepository gestoreRepository; // Repository per i gestori della community

  @Autowired private RicettaRepository ricettaRepository; // Repository per le ricette

  @Autowired
  private ListaBloccatiRepository listaBloccatiRepository; // Repository per i blocchi utenti

  @Autowired private UtenteRepository utenteRepository; // Repository per gli utenti

  /**
   * Risolve una segnalazione associata a una ricetta.
   *
   * @param segnalazioneId ID della segnalazione
   * @param gestore_id ID del gestore che risolve la segnalazione
   * @param motivoBlocco Motivo del blocco se l'utente viene bloccato
   * @return Un messaggio che descrive l'esito dell'operazione
   */
  @Override
  public String risolviSegnalazione(Long segnalazioneId, Long gestore_id, String motivoBlocco) {
    // Validazione del motivo del blocco
    String validazioneMotivo = validaMotivoBlocco(motivoBlocco);
    if (!validazioneMotivo.isEmpty()) {
      return validazioneMotivo; // Restituisce un messaggio di errore se il motivo del blocco non è
      // valido
    }

    try {
      // Recupera la segnalazione, il gestore e la ricetta associata
      SegnalazioneRicetta segnalazioneRicetta = getSegnalazioneRicetta(segnalazioneId);
      GestoreCommunity gestoreCommunity = getGestore(gestore_id);
      Ricetta ricetta = getRicettaAssociata(segnalazioneRicetta);

      // Ottieni l'autore della ricetta
      Utente autore = ricetta.getAutore();
      if (autore == null) {
        return "Autore non trovato per la ricetta."; // Errore se l'autore non è trovato
      }

      // Controlla il numero di segnalazioni dell'utente
      int numeroSegnalazioni = autore.getNumeroSegnalazioni();
      boolean isPrimaViolazione = numeroSegnalazioni < 2; // Se l'utente ha meno di 2 segnalazioni

      // Incrementa il contatore delle segnalazioni
      autore.setNumeroSegnalazioni(numeroSegnalazioni + 1);
      utenteRepository.save(autore); // Salva l'utente con il nuovo numero di segnalazioni

      // Se l'utente ha già ricevuto 2 segnalazioni, deve essere bloccato
      if (!isPrimaViolazione) {
        bloccaUtente(autore, motivoBlocco); // Blocco dell'utente
      }

      // Elimina la ricetta e aggiorna lo stato della segnalazione
      eliminaRicetta(segnalazioneRicetta, ricetta, gestoreCommunity);

      // Restituisce il messaggio di esito finale
      if (isPrimaViolazione) {
        return "L'utente è alla prima violazione e non viene bloccato, ma la ricetta è stata eliminata.";
      } else {
        return "Segnalazione risolta con successo. Autore bloccato e ricetta eliminata. Motivazione: "
            + motivoBlocco;
      }

    } catch (Exception e) {
      System.err.println("Errore durante la risoluzione della segnalazione: " + e.getMessage());
      e.printStackTrace();
      return "Errore interno del server: " + e.getMessage();
    }
  }

  /**
   * Validazione del motivo del blocco.
   *
   * @param motivoBlocco Motivo del blocco
   * @return Un messaggio di errore se il motivo non è valido, altrimenti una stringa vuota
   */
  private String validaMotivoBlocco(String motivoBlocco) {
    if (motivoBlocco == null || motivoBlocco.isBlank()) {
      return "Il motivo del blocco è obbligatorio."; // Controllo se il motivo è vuoto
    }
    if (motivoBlocco.length() > 500) {
      return "Il motivo del blocco non può superare i 500 caratteri."; // Controllo lunghezza del
      // motivo
    }
    return ""; // Se il motivo è valido, ritorna una stringa vuota
  }

  /**
   * Recupera la segnalazione associata all'ID.
   *
   * @param segnalazioneId ID della segnalazione
   * @return La segnalazione trovata
   * @throws RuntimeException Se la segnalazione non esiste
   */
  private SegnalazioneRicetta getSegnalazioneRicetta(Long segnalazioneId) {
    return segnalazioneRicettaRepository
        .findById(segnalazioneId)
        .orElseThrow(() -> new RuntimeException("SegnalazioneRicetta non trovata."));
  }

  /**
   * Recupera il gestore associato all'ID.
   *
   * @param gestoreId ID del gestore
   * @return Il gestore trovato
   * @throws RuntimeException Se il gestore non esiste
   */
  private GestoreCommunity getGestore(Long gestoreId) {
    return gestoreRepository
        .findById(gestoreId)
        .orElseThrow(() -> new RuntimeException("Gestore non trovato."));
  }

  /**
   * Recupera la ricetta associata alla segnalazione.
   *
   * @param segnalazioneRicetta La segnalazione
   * @return La ricetta associata
   * @throws RuntimeException Se la ricetta non esiste
   */
  private Ricetta getRicettaAssociata(SegnalazioneRicetta segnalazioneRicetta) {
    return ricettaRepository
        .findById(segnalazioneRicetta.getRicettaAssociato().getId())
        .orElseThrow(() -> new RuntimeException("Ricetta non trovata."));
  }

  /**
   * Elimina la ricetta e aggiorna lo stato della segnalazione.
   *
   * @param segnalazioneRicetta La segnalazione da risolvere
   * @param ricetta La ricetta da eliminare
   * @param gestore Il gestore che risolve la segnalazione
   */
  private void eliminaRicetta(
      SegnalazioneRicetta segnalazioneRicetta, Ricetta ricetta, GestoreCommunity gestore) {
    // Imposta lo stato della segnalazione a RISOLTO
    segnalazioneRicetta.setStato(StatoSegnalazione.RISOLTO);
    segnalazioneRicetta.setGestoreAssociato(gestore); // Associa il gestore
    segnalazioneRicetta.setRicettaAssociato(null); // Rimuove la ricetta associata
    segnalazioneRicettaRepository.save(segnalazioneRicetta); // Salva la segnalazione aggiornata

    // Elimina la ricetta dal sistema
    ricettaRepository.delete(ricetta);
  }

  /**
   * Blocca l'utente e aggiunge la motivazione nella lista dei bloccati.
   *
   * @param autore L'utente da bloccare
   * @param motivoBlocco Motivo per cui l'utente è bloccato
   */
  private void bloccaUtente(Utente autore, String motivoBlocco) {
    // Imposta il campo 'bloccato' su true
    autore.setBloccato(true); // Questo aggiorna lo stato 'bloccato'

    // Salva l'utente con lo stato aggiornato
    utenteRepository.save(autore);

    // Crea un nuovo record nella lista dei bloccati
    ListaBloccati bloccato = new ListaBloccati();
    bloccato.setUtente(autore);
    bloccato.setDataBlocco(LocalDate.now()); // Imposta la data di blocco
    bloccato.setMotivoBlocco(motivoBlocco); // Imposta il motivo di blocco
    listaBloccatiRepository.save(bloccato); // Salva nella lista dei bloccati
  }
}
