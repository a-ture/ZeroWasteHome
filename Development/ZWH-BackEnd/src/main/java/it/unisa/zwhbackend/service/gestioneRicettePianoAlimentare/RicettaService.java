package it.unisa.zwhbackend.service.gestioneRicettePianoAlimentare;

import it.unisa.zwhbackend.model.entity.Ricetta;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per la gestione delle ricette.
 *
 * <p>Definisce i metodi necessari per la creazione, lettura, aggiornamento ed eliminazione delle
 * ricette.
 *
 * @author Anna Tagliamonte
 */
public interface RicettaService {

  Ricetta aggiungiRicetta(Ricetta ricetta);

  List<Ricetta> getAllRicette();

  Optional<Ricetta> getRicettaById(Long id);

  Ricetta aggiornaRicetta(Long id, Ricetta nuovaRicetta);

  void eliminaRicetta(Long id);
}
