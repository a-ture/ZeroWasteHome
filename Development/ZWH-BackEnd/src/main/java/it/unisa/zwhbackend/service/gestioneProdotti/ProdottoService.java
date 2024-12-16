package it.unisa.zwhbackend.service.gestioneProdotti;

import it.unisa.zwhbackend.model.entity.Prodotto;
import it.unisa.zwhbackend.model.entity.ProdottoRequestDTO;
import java.util.List;

/**
 * Interfaccia che definisce i servizi per la gestione dei prodotti nel sistema.
 *
 * <p>Questa interfaccia fornisce metodi per:
 *
 * <ul>
 *   <li>Inserire nuovi prodotti nel sistema
 *   <li>Aggiungere prodotti al frigo di un utente
 *   <li>Visualizzare i prodotti presenti nella dispensa di un utente
 *   <li>Ricercare prodotti per nome
 * </ul>
 *
 * <p>Ogni metodo include controlli di validazione per garantire l'integrità dei dati e può lanciare
 * eccezioni per segnalare errori o violazioni dei vincoli.
 *
 * @author Marco Meglio, Ferdinando Ranieri, Alessandra Trotta
 */
public interface ProdottoService {

  /**
   * Inserisce un nuovo prodotto nel sistema.
   *
   * <p>Questo metodo consente di creare un nuovo prodotto con i dettagli forniti e di salvarlo nel
   * database. Se il prodotto esiste già nel sistema, il comportamento può dipendere
   * dall'implementazione, ma generalmente restituisce il prodotto esistente.
   *
   * @param nomeProdotto il nome del prodotto da inserire; non può essere nullo e deve avere una
   *     lunghezza massima di 50 caratteri
   * @param dataScadenza la data di scadenza del prodotto, in formato "dd/MM/yyyy"; non può essere
   *     nulla
   * @param codiceBarre il codice a barre del prodotto, non nullo; deve contenere da 8 a 16 cifre
   * @param categoria una lista di stringhe che rappresentano le categorie associate al prodotto;
   *     può essere vuota
   * @return il prodotto inserito o aggiornato
   * @throws IllegalArgumentException se uno dei parametri non rispetta i vincoli di validazione
   */
  Prodotto inserisciProdotto(
      String nomeProdotto, String dataScadenza, String codiceBarre, List<String> categoria);

  /**
   * Aggiunge un prodotto al frigo associandolo a un utente.
   *
   * <p>Se il prodotto non esiste nel sistema, viene creato e salvato nel database. Se il prodotto è
   * già presente nel frigo dell'utente con la stessa data di scadenza, la quantità viene
   * aggiornata; altrimenti, viene creata una nuova relazione con la data di scadenza fornita.
   *
   * @param nomeProdotto il nome del prodotto da aggiungere; non può essere nullo e deve avere una
   *     lunghezza massima di 50 caratteri
   * @param dataScadenza la data di scadenza del prodotto, in formato "dd/MM/yyyy"; non può essere
   *     nulla
   * @param codiceBarre il codice a barre del prodotto, non nullo; deve contenere da 8 a 16 cifre
   * @param quantita la quantità del prodotto da aggiungere; deve essere un numero positivo maggiore
   *     di zero
   * @param idUtente l'email dell'utente associato al prodotto; non può essere nulla
   * @param categoria una lista di stringhe che rappresentano le categorie associate al prodotto;
   *     può essere vuota
   * @param img l'url dell'immagine del prodotto
   * @return il prodotto aggiunto o aggiornato
   * @throws IllegalArgumentException se uno dei parametri non rispetta i vincoli di validazione
   * @throws IllegalStateException se l'utente specificato non esiste nel sistema
   */
  Prodotto aggiungiProdottoFrigo(
      String nomeProdotto,
      String dataScadenza,
      String codiceBarre,
      int quantita,
      String idUtente,
      List<String> categoria,
      String img);

  /**
   * Visualizza i prodotti presenti nella dispensa di un utente.
   *
   * <p>Recupera tutti i prodotti associati alla dispensa dell'utente specificato. Ogni prodotto
   * include dettagli come nome, codice a barre, quantità e data di scadenza.
   *
   * @param email l'email dell'utente di cui si vogliono visualizzare i prodotti nella dispensa; non
   *     può essere nulla
   * @return una lista di {@link ProdottoRequestDTO} che rappresentano i prodotti nella dispensa
   * @throws IllegalStateException se l'utente specificato non esiste nel sistema
   */
  List<ProdottoRequestDTO> visualizzaProdottiDispensa(String email);

  /**
   * Visualizza i prodotti presenti nel frigo di un utente.
   *
   * <p>Recupera tutti i prodotti associati al frigo dell'utente specificato. Ogni prodotto include
   * dettagli come nome, codice a barre, quantità e data di scadenza.
   *
   * @param email l'email dell'utente di cui si vogliono visualizzare i prodotti nel frigo; non può
   *     essere nulla
   * @return una lista di {@link ProdottoRequestDTO} che rappresentano i prodotti nel frigo
   * @throws IllegalStateException se l'utente specificato non esiste nel sistema
   */
  List<ProdottoRequestDTO> visualizzaProdottiFrigo(String email);

  /**
   * Esegue una ricerca di prodotti di un utente basandosi su un criterio parziale del nome del
   * prodotto.
   *
   * <p>Il metodo consente di cercare prodotti che contengono nel nome la stringa fornita e
   * restituisce una lista di oggetti {@link ProdottoRequestDTO} contenenti le informazioni
   * dettagliate sui prodotti trovati in base all'utente specificato.
   *
   * @param emailUtente l'email dell'utente per cui eseguire la ricerca
   * @param nomeProdotto il criterio di ricerca parziale sul nome del prodotto
   * @return una lista di {@link ProdottoRequestDTO} contenente i prodotti trovati, basato sull'id
   *     dell'utente, che soddisfano i criteri di ricerca
   */
  public List<ProdottoRequestDTO> RicercaPerNome(String emailUtente, String nomeProdotto);
}
