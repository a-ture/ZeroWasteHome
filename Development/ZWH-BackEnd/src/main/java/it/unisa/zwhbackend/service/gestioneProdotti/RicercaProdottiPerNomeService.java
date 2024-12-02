package it.unisa.zwhbackend.service.gestioneProdotti;

import it.unisa.zwhbackend.model.entity.Prodotto;
import java.util.List;

/**
 * Interfaccia che definisce i metodi per la logica di ricerca dei prodotti per nome. Fornisce un
 * contratto per implementare servizi dedicati alla ricerca di prodotti nel database basata su un
 * criterio di corrispondenza parziale del nome.
 *
 * @author Alessandra Trotta
 */
public interface RicercaProdottiPerNomeService {

  /**
   * Cerca i prodotti in base a un criterio di corrispondenza parziale sul nome.
   *
   * <p>Implementazioni di questo metodo devono garantire la gestione di casi in cui il nome fornito
   * sia nullo o vuoto.
   *
   * @param name la stringa da cercare nei nomi dei prodotti
   * @return una lista di prodotti che soddisfano il criterio di ricerca
   */
  List<Prodotto> RicercaPerNome(String name);
}
