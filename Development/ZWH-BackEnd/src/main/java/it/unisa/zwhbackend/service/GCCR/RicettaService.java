package it.unisa.zwhbackend.service.GCCR;

import it.unisa.zwhbackend.model.entity.Ricetta;
import it.unisa.zwhbackend.model.entity.Utente;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per la gestione delle ricette.
 *
 * <p>Definisce i metodi necessari per la creazione, lettura, aggiornamento ed eliminazione delle
 * ricette. Autore: Anna Tagliamonte
 */
public interface RicettaService {

  Ricetta aggiungiRicetta(Ricetta ricetta);

  List<Ricetta> getAllRicette();

  Optional<Ricetta> getRicettaById(Long id);

  Ricetta aggiornaRicetta(Long id, Ricetta nuovaRicetta);

  void eliminaRicetta(Long id);

  /**
   * Blocca l'autore di una ricetta, aggiungendo l'utente alla lista dei bloccati.
   *
   * @param autore l'utente da bloccare
   */
  void bloccaAutore(Utente autore);
}
