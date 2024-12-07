package it.unisa.zwhbackend.model.entity;

// Importazioni per la validazione dei campi

/**
 * Data Transfer Object (DTO) che rappresenta un oggetto di richiesta per un prodotto.
 *
 * <p>Questa classe viene utilizzata per ricevere i dati relativi a un prodotto dalla richiesta
 * dell'utente, applicando le regole di validazione sui vari campi tramite annotazioni di Jakarta
 * Validation. Il DTO è utile per gestire i dati che vengono inviati tramite le API.
 *
 * @author Marco Meglio
 */
public class ProdottoRequestDTO {

  /**
   * Codice a barre del prodotto.
   *
   * <p>Questo campo deve contenere un codice numerico con una lunghezza massima di 8 caratteri. La
   * validazione è eseguita tramite {@code @Pattern} che impone la regex: {@code ^[0-9]{1,8}$}.
   */
  private String codiceBarre; // Variabile per memorizzare il codice a barre del prodotto

  /**
   * Nome del prodotto.
   *
   * <p>Questo campo deve contenere solo lettere dell'alfabeto con una lunghezza massima di 50
   * caratteri. La validazione è eseguita tramite {@code @Pattern} che impone la regex: {@code
   * ^[a-zA-Z]{1,50}$}.
   */
  private String nomeProdotto; // Variabile per memorizzare il nome del prodotto

  /**
   * Data di scadenza del prodotto.
   *
   * <p>Il campo deve rispettare il formato {@code gg/mm/aaaa} (giorno, mese e anno a 4 cifre). La
   * validazione è eseguita tramite {@code @Pattern} che impone la regex: {@code
   * ^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$}.
   */
  private String dataScadenza; // Variabile per memorizzare la data di scadenza del prodotto

  /**
   * Quantità del prodotto.
   *
   * <p>Questo campo deve contenere un valore numerico positivo maggiore di zero. La validazione è
   * eseguita tramite {@code @Min} che impone un valore minimo di 1.
   */
  private int quantità; // Variabile per memorizzare la quantità del prodotto

  /**
   * ID utente.
   *
   * <p>Questo campo deve contenere un valore numerico positivo maggiore di zero. La validazione è
   * eseguita tramite {@code @Min} che impone un valore minimo di 1.
   */
  private String
      idUtente; // Variabile per avere un corretto riferimento all'utente che inserisci il prodotto

  // in frigo

  // Getter e Setter per l'id dell'utente
  public String getIdUtente() {
    return idUtente;
  }

  // Getter e Setter per il campo codiceBarre
  public String getCodiceBarre() {
    return codiceBarre;
  }

  public void setCodiceBarre(String codiceBarre) {
    this.codiceBarre = codiceBarre;
  }

  // Getter e Setter per il campo nomeProdotto
  public String getNomeProdotto() {
    return nomeProdotto;
  }

  public void setNomeProdotto(String nomeProdotto) {
    this.nomeProdotto = nomeProdotto;
  }

  // Getter e Setter per il campo dataScadenza
  public String getDataScadenza() {
    return dataScadenza;
  }

  public void setDataScadenza(String dataScadenza) {
    this.dataScadenza = dataScadenza;
  }

  // Getter e Setter per il campo quantità
  public int getQuantità() {
    return quantità;
  }

  public void setQuantità(int quantità) {
    this.quantità = quantità;
  }
}
