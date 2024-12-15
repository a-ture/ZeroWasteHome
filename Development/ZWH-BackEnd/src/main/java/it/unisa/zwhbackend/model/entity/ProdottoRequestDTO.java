package it.unisa.zwhbackend.model.entity;

import java.util.List;

/**
 * Data Transfer Object (DTO) che rappresenta un oggetto di richiesta per un prodotto.
 *
 * <p>Questa classe viene utilizzata per ricevere i dati relativi a un prodotto tramite una
 * richiesta API, applicando regole di validazione sui vari campi tramite annotazioni di Jakarta
 * Validation. È progettata per fornire una struttura coerente e valida per la gestione dei dati di
 * input lato server.
 *
 * @author Marco Meglio, Ferdinando Ranieri, Alessandra Trotta
 */
public class ProdottoRequestDTO {

  /**
   * Codice a barre del prodotto.
   *
   * <p>Rappresenta un identificatore univoco numerico per il prodotto, con una lunghezza compresa
   * tra 8 e 16 caratteri. La validazione è eseguita tramite {@code @Pattern} che applica la regex:
   * {@code ^\\d{8,16}$}.
   */
  private String codiceBarre;

  /**
   * Nome del prodotto.
   *
   * <p>Contiene il nome descrittivo del prodotto. Deve includere solo lettere dell'alfabeto, spazi
   * e caratteri accentati, con una lunghezza massima di 50 caratteri. La validazione è eseguita
   * tramite {@code @Pattern} che impone la regex: {@code ^[a-zA-Z\\s]{1,50}$}.
   */
  private String nomeProdotto;

  /**
   * Data di scadenza del prodotto.
   *
   * <p>Indica la data entro la quale il prodotto deve essere consumato. Deve rispettare il formato
   * {@code gg/mm/aaaa} (giorno, mese, anno a 4 cifre). La validazione è eseguita tramite
   * {@code @Pattern} che applica la regex: {@code
   * ^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$}.
   */
  private String dataScadenza;

  /**
   * Quantità del prodotto.
   *
   * <p>Specifica il numero di unità del prodotto. Deve essere un valore numerico positivo maggiore
   * di zero. La validazione è eseguita tramite {@code @Min(1)}.
   */
  private int quantità;

  /**
   * ID dell'utente.
   *
   * <p>Rappresenta l'identificativo univoco dell'utente che inserisce il prodotto. Deve essere una
   * stringa non vuota.
   */
  private String idUtente;

  /**
   * Nome dell'immagine del prodotto.
   *
   * <p>Questo campo rappresenta il nome del file immagine associato al prodotto. Viene utilizzato
   * per memorizzare il percorso relativo o il nome del file immagine caricato per il prodotto.
   */
  private String img; // Variabile per memorizzare il nome dell'immagine del prodotto

  // Getter e Setter per il campo img
  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  /**
   * Categoria del prodotto.
   *
   * <p>Una lista di stringhe che rappresenta le categorie associate al prodotto. Ad esempio,
   * categorie come "VEGANO", "GLUTENFREE" o "VEGETARIANO" possono essere utilizzate per descrivere
   * il prodotto. La validazione può includere un controllo sul set predefinito di valori ammessi.
   */
  private List<String> categoria;

  // Getter e Setter per categoria
  public List<String> getCategoria() {
    return categoria;
  }

  public void setCategoria(List<String> categoria) {
    this.categoria = categoria;
  }

  /**
   * Costruttore vuoto.
   *
   * <p>Utilizzato principalmente per la deserializzazione automatica durante l'elaborazione delle
   * richieste API.
   */
  public ProdottoRequestDTO() {
    // Costruttore vuoto
  }

  /**
   * Costruttore della classe ProdottoRequestDTO.
   *
   * @param codiceBarre il codice a barre del prodotto
   * @param nomeProdotto il nome del prodotto
   * @param dataScadenza la data di scadenza del prodotto
   * @param quantità la quantità del prodotto
   * @param idUtente l'ID dell'utente che inserisce il prodotto
   */
  public ProdottoRequestDTO(
      String codiceBarre, String nomeProdotto, String dataScadenza, int quantità, String idUtente) {
    this.codiceBarre = codiceBarre;
    this.nomeProdotto = nomeProdotto;
    this.dataScadenza = dataScadenza;
    this.quantità = quantità;
    this.idUtente = idUtente;
  }

  public ProdottoRequestDTO(
      String codiceBarre,
      String nomeProdotto,
      String img,
      String dataScadenza,
      int quantità,
      String idUtente) {
    this.codiceBarre = codiceBarre;
    this.nomeProdotto = nomeProdotto;
    this.dataScadenza = dataScadenza;
    this.quantità = quantità;
    this.img = img;
    this.idUtente = idUtente;
  }

  // Getter e Setter per l'id dell'utente
  public String getIdUtente() {
    return idUtente;
  }

  public void setIdUtente(String idUtente) {
    this.idUtente = idUtente;
  }

  // Getter e Setter per codiceBarre
  public String getCodiceBarre() {
    return codiceBarre;
  }

  public void setCodiceBarre(String codiceBarre) {
    this.codiceBarre = codiceBarre;
  }

  // Getter e Setter per nomeProdotto
  public String getNomeProdotto() {
    return nomeProdotto;
  }

  public void setNomeProdotto(String nomeProdotto) {
    this.nomeProdotto = nomeProdotto;
  }

  // Getter e Setter per dataScadenza
  public String getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(String dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  // Getter e Setter per quantità
  public int getQuantità() {
    return quantità;
  }

  public void setQuantità(int quantità) {
    this.quantità = quantità;
  }
}
