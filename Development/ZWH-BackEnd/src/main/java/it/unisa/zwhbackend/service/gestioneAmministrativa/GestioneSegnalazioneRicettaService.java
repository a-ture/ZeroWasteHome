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

  @Autowired private SegnalazioneRicettaRepository segnalazioneRicettaRepository;

  @Autowired private GestoreCommunityRepository gestoreRepository;

  @Autowired private RicettaRepository ricettaRepository;

  @Autowired private ListaBloccatiRepository listaBloccatiRepository;

  @Autowired private UtenteRepository utenteRepository;

  /**
   * Risolve una segnalazione associata a una ricetta.
   *
   * <p>Questo metodo si occupa di risolvere una segnalazione relativa a una ricetta, incrementando
   * il numero di segnalazioni dell'utente autore della ricetta. Se l'utente raggiunge il numero
   * massimo di segnalazioni (due), viene bloccato. Inoltre, la ricetta segnalata viene eliminata
   * dal sistema.
   *
   * @param segnalazioneId ID della segnalazione
   * @param gestore_id ID del gestore che risolve la segnalazione
   * @param motivoBlocco Motivo del blocco, se applicabile
   * @return Un messaggio che descrive l'esito dell'operazione
   */
  @Override
  public String risolviSegnalazione(Long segnalazioneId, String gestore_id, String motivoBlocco) {
    // Validazione del motivo del blocco
    String validazioneMotivo = validaMotivoBlocco(motivoBlocco);
    if (!validazioneMotivo.isEmpty()) {
      return validazioneMotivo;
    }

    try {
      // Recupera la segnalazione, il gestore e la ricetta associata
      SegnalazioneRicetta segnalazioneRicetta = getSegnalazioneRicetta(segnalazioneId);
      GestoreCommunity gestoreCommunity = getGestore(gestore_id);
      Ricetta ricetta = getRicettaAssociata(segnalazioneRicetta);

      // Ottieni l'autore della ricetta
      Utente autore = ricetta.getAutore();
      if (autore == null) {
        return "Autore non trovato per la ricetta.";
      }

      // Controlla il numero di segnalazioni dell'utente
      int numeroSegnalazioni = autore.getNumeroSegnalazioni();
      boolean isPrimaViolazione = numeroSegnalazioni < 2; // Se l'utente ha meno di 2 segnalazioni

      // Incrementa il contatore delle segnalazioni solo se è la prima violazione
      if (isPrimaViolazione) {
        autore.setNumeroSegnalazioni(numeroSegnalazioni + 1); // Incrementa solo la prima volta
      }

      // Se l'utente ha già ricevuto 2 segnalazioni, deve essere bloccato
      if (numeroSegnalazioni >= 2) {
        autore.setBloccato(true); // Aggiorna direttamente lo stato di blocco
        // Aggiungi il blocco nella lista dei bloccati
        ListaBloccati bloccato = new ListaBloccati();
        bloccato.setUtente(autore);
        bloccato.setDataBlocco(LocalDate.now());
        bloccato.setMotivoBlocco(motivoBlocco);
        listaBloccatiRepository.save(bloccato); // Salva la lista dei bloccati
      }

      // Salva l'utente solo una volta, con le modifiche a numeroSegnalazioni e stato di blocco
      utenteRepository.save(autore);

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
   * <p>Questa funzione verifica che il motivo del blocco non sia nullo, vuoto e che non superi i
   * 500 caratteri.
   *
   * @param motivoBlocco Il motivo del blocco da validare
   * @return Un messaggio di errore, se il motivo non è valido, o una stringa vuota se il motivo è
   *     valido
   */
  private String validaMotivoBlocco(String motivoBlocco) {
    if (motivoBlocco == null || motivoBlocco.isBlank()) {
      return "Il motivo del blocco è obbligatorio.";
    }
    if (motivoBlocco.length() > 500) {
      return "Il motivo del blocco non può superare i 500 caratteri.";
    }
    return ""; // Se il motivo è valido, ritorna una stringa vuota
  }

  /**
   * Recupera la segnalazione associata all'ID fornito.
   *
   * <p>Questo metodo recupera la segnalazione dal repository tramite il suo ID.
   *
   * @param segnalazioneId L'ID della segnalazione da recuperare
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
   * <p>Questo metodo recupera il gestore dal repository tramite il suo ID.
   *
   * @param gestoreId ID del gestore
   * @return Il gestore trovato
   * @throws RuntimeException Se il gestore non esiste
   */
  private GestoreCommunity getGestore(String gestoreId) {
    GestoreCommunity gestore = gestoreRepository.findByEmail(gestoreId);
    if (gestoreRepository == null) {
      new RuntimeException("Gestore non trovato.");
    }
    return gestore;
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
    // Imposta lo stato della segnalazione a CHIUSA
    segnalazioneRicetta.setStato(StatoSegnalazione.CHIUSA);
    segnalazioneRicetta.setGestoreAssociato(gestore); // Associa il gestore
    segnalazioneRicetta.setRicettaAssociato(null); // Rimuove la ricetta associata
    segnalazioneRicettaRepository.save(segnalazioneRicetta); // Salva la segnalazione aggiornata

    // Elimina la ricetta dal sistema
    ricettaRepository.delete(ricetta);
  }
}
