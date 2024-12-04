package it.unisa.zwhbackend.service.gestioneProdotto;

import it.unisa.zwhbackend.model.entity.Prodotto;

import java.util.List;

/**
 * Interfaccia che definisce i servizi per la gestione dei prodotti.
 * Fornisce metodi per l'inserimento e la gestione di prodotti.
 *
 * @author Marco Meglio
 */
public interface ProdottoService {

  /**
   * Inserisce un nuovo prodotto nel sistema.
   *
   * @param nomeProdotto il nome del prodotto da inserire
   * @param dataScadenza la data di scadenza del prodotto, in formato stringa
   * @param codiceBarre il codice a barre del prodotto
   * @return il prodotto inserito
   */
  public Prodotto inserisciProdotto(String nomeProdotto, String dataScadenza, String codiceBarre);

  /**
   * Aggiunge un prodotto al frigo associandolo a un utente.
   *
   * @param nomeProdotto il nome del prodotto da aggiungere
   * @param dataScadenza la data di scadenza del prodotto, in formato stringa
   * @param codiceBarre il codice a barre del prodotto
   * @param quantita la quantità del prodotto da aggiungere
   * @param idUtente l'ID dell'utente associato al prodotto
   * @return il prodotto aggiunto al frigo
   */
  public Prodotto aggiungiProdottoFrigo(String nomeProdotto, String dataScadenza, String codiceBarre, int quantita, Long idUtente);
  public List<Prodotto> visualizzaProdottiDispensa(Long idUtente);
}
