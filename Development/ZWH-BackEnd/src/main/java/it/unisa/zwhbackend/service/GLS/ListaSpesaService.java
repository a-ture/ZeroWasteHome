package it.unisa.zwhbackend.service.GLS;

import it.unisa.zwhbackend.model.entity.ListaSpesa;
import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.Utente;
import java.util.List;
import java.util.Optional;

/**
 * Interfaccia che definisce le operazioni per la gestione delle liste della spesa.
 *
 * <p>Queste operazioni comprendono la creazione di una lista della spesa, il recupero delle liste
 * associate a un utente, e la generazione automatica di una lista in base alle preferenze
 * alimentari dell'utente e ai prodotti disponibili nel frigo e nella dispensa.
 *
 * @author Giuseppe Russo
 */
public interface ListaSpesaService {

  /**
   * Crea una nuova lista della spesa.
   *
   * @param utente Utente per cui si sta creando la lista della spesa.
   * @param products Lista di prodotti da aggiungere alla lista della spesa.
   * @return La lista della spesa creata.
   */
  ListaSpesa createShoppingList(Utente utente, List<Prodotto> products);

  /**
   * Ottiene la lista della spesa associata a un utente.
   *
   * @param userId ID dell'utente di cui si vuole recuperare la lista della spesa.
   * @return La lista della spesa associata all'utente, se esiste.
   */
  Optional<ListaSpesa> getShoppingListByUserId(Long userId);

  /**
   * Genera una lista della spesa per un utente in base agli articoli disponibili nel frigo, nella
   * dispensa e nel piano giornaliero.
   *
   * @param utente Utente per cui si sta generando la lista.
   * @return La lista della spesa generata.
   */
  ListaSpesa generateShoppingList(Utente utente);
}
