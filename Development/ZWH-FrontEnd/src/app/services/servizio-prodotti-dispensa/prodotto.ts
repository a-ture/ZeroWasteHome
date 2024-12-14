// Prodotto Frontend Class

export class Prodotto {
  codiceBarre: string;
  nomeProdotto: string;
  dataScadenza: string;
  quantità: number;
  idUtente: string;

  constructor(
    codiceBarre: string,
    nomeProdotto: string,
    dataScadenza: string,
    quantità: number,
    idUtente: string,
  ) {
    this.codiceBarre = codiceBarre;
    this.nomeProdotto = nomeProdotto;
    this.dataScadenza = dataScadenza;
    this.quantità = quantità;
    this.idUtente = idUtente;
  }

  // Metodo statico per mappare una risposta API a un'istanza della classe
  static fromApiResponse(response: any): Prodotto {
    return new Prodotto(
      response.codiceBarre,
      response.nomeProdotto,
      response.dataScadenza,
      response.quantità,
      response.idUtente,
    );
  }

  // Metodo per convertire l'oggetto in un formato adatto per le richieste API
  toApiRequest(): any {
    return {
      codiceBarre: this.codiceBarre,
      nomeProdotto: this.nomeProdotto,
      dataScadenza: this.dataScadenza,
      quantità: this.quantità,
      idUtente: this.idUtente,
    };
  }
}
