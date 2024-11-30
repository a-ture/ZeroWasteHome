package it.unisa.zwhbackend.service.GCCR;

import it.unisa.zwhbackend.model.entity.Ricetta;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia per definire i metodi del servizio per la gestione delle ricette.
 *
 * <p>Fornisce le operazioni necessarie per creare, leggere, aggiornare ed eliminare ricette. Deve
 * essere implementata dalla classe {@link GestioneRicetteService}.
 *
 * @author Anna Tagliamonte
 */
public interface RicettaService {

  /**
   * Aggiunge una nuova ricetta al sistema.
   *
   * @param ricetta la ricetta da aggiungere
   * @return l'entità {@link Ricetta} appena salvata
   */
  Ricetta aggiungiRicetta(Ricetta ricetta);

  /**
   * Restituisce tutte le ricette presenti nel sistema.
   *
   * @return un elenco di tutte le ricette
   */
  List<Ricetta> getAllRicette();

  /**
   * Restituisce una ricetta specifica dato il suo ID.
   *
   * @param id l'ID della ricetta da recuperare
   * @return un oggetto {@link Optional} che contiene la ricetta, se esiste
   */
  Optional<Ricetta> getRicettaById(Long id);

  /**
   * Aggiorna i dettagli di una ricetta esistente.
   *
   * @param id l'ID della ricetta da aggiornare
   * @param nuovaRicetta i nuovi dettagli della ricetta
   * @return la ricetta aggiornata
   */
  Ricetta aggiornaRicetta(Long id, Ricetta nuovaRicetta);

  /**
   * Elimina una ricetta specifica dato il suo ID.
   *
   * @param id l'ID della ricetta da eliminare
   */
  void eliminaRicetta(Long id);
}
