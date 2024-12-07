package it.unisa.zwhbackend.service.gestioneProdotti;

import it.unisa.zwhbackend.model.entity.Prodotto;
import java.util.List;

/**
 * Interfaccia che definisce i servizi per la gestione dei prodotti. Fornisce metodi per
 * l'inserimento, la gestione e la visualizzazione dei prodotti associati a un utente.
 *
 * <p>Include operazioni per l'aggiunta di prodotti al sistema, l'associazione con utenti, e il
 * recupero di prodotti associati a un determinato utente.
 *
 * @author Marco Meglio
 */
public interface ProdottoService {

  /**
   * Inserisce un nuovo prodotto nel sistema.
   *
   * <p>Questo metodo crea un nuovo prodotto con i dettagli forniti e lo salva nel database. Se il
   * prodotto esiste già, restituisce una nuova istanza basata sullo stato corrente.
   *
   * @param nomeProdotto il nome del prodotto da inserire, non nullo e con una lunghezza massima di
   *     50 caratteri
   * @param dataScadenza la data di scadenza del prodotto, in formato "gg/MM/aaaa", non nulla
   * @param codiceBarre il codice a barre del prodotto, non nullo e composto da massimo 8 cifre
   * @return il prodotto inserito
   * @throws IllegalArgumentException se uno dei parametri non rispetta i vincoli di validazione
   */
  public Prodotto inserisciProdotto(String nomeProdotto, String dataScadenza, String codiceBarre);

  /**
   * Aggiunge un prodotto al frigo associandolo a un utente.
   *
   * <p>Questo metodo verifica se il prodotto esiste già nel sistema; se non esiste, lo crea.
   * Successivamente, associa il prodotto all'utente specificato e, se necessario, aggiorna la
   * quantità o crea una nuova relazione per la data di scadenza fornita.
   *
   * @param nomeProdotto il nome del prodotto da aggiungere, non nullo e con una lunghezza massima
   *     di 50 caratteri
   * @param dataScadenza la data di scadenza del prodotto, in formato "gg/MM/aaaa", non nulla
   * @param codiceBarre il codice a barre del prodotto, non nullo e composto da massimo 8 cifre
   * @param quantita la quantità del prodotto da aggiungere, deve essere un numero positivo maggiore
   *     di zero
   * @param idUtente l'ID dell'utente associato al prodotto, non nullo
   * @return il prodotto aggiunto al frigo
   * @throws IllegalArgumentException se uno dei parametri non rispetta i vincoli di validazione
   * @throws IllegalStateException se l'utente con l'ID specificato non è trovato nel sistema
   */
  public Prodotto aggiungiProdottoFrigo(
      String nomeProdotto, String dataScadenza, String codiceBarre, int quantita, String idUtente);

  /**
   * Visualizza i prodotti presenti nella dispensa di un utente.
   *
   * <p>Recupera tutti i prodotti associati alla dispensa dell'utente specificato. I prodotti
   * restituiti includono informazioni come nome, codice a barre, quantità e data di scadenza.
   *
   * @param email l'ID dell'utente di cui si vogliono visualizzare i prodotti nella dispensa, non
   *     nullo
   * @return una lista di prodotti presenti nella dispensa dell'utente specificato
   * @throws IllegalStateException se l'utente con l'ID specificato non è trovato nel sistema
   */
  public List<Prodotto> visualizzaProdottiDispensa(String email);
}
