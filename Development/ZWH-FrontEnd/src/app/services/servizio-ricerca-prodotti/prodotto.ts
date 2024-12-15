export class Prodotto {
  codiceBarre: string = '';
  nomeProdotto: string = '';
  dataScadenza: string = '';
  quantità: number = 0;
  idUtente: string = '';

  constructor(data?: Partial<Prodotto>) {
    if (data) {
      this.codiceBarre = data.codiceBarre || '';
      this.nomeProdotto = data.nomeProdotto || '';
      this.dataScadenza = data.dataScadenza || '';
      this.quantità = data.quantità || 0;
      this.idUtente = data.idUtente || '';
    }
  }
}
