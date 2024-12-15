import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

export interface SegnalazionePagamento {
  id: number;
  descrizione: string;
  utente: string;
  dataCreazione: string;
  statoSegnalazione: string;
  dettagli: string;
}

@Injectable({
  providedIn: 'root',
})
export class SegnalazionePagamentoService {
  private apiUrl = `${environment.apiUrl}/segnalazioniPagamento`; // Base URL dell'API

  constructor(private http: HttpClient) {}

  /**
   * Risolve una segnalazione di pagamento.
   * @param idSegnalazione L'ID della segnalazione da risolvere.
   * @param gestoreId L'ID del gestore che risolve la segnalazione.
   * @param dettagliRisoluzione I dettagli relativi alla risoluzione della segnalazione.
   * @returns Un Observable con la segnalazione aggiornata.
   */
  risolvereSegnalazione(
    idSegnalazione: number,
    gestoreId: string,
    dettagliRisoluzione: string,
  ): Observable<SegnalazionePagamento> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const params = { gestoreId, dettagliRisoluzione };

    return this.http.patch<SegnalazionePagamento>(
      `${this.apiUrl}/risolvi/${idSegnalazione}`,
      {},
      { headers, params },
    );
  }

  /**
   * Prende in carico una segnalazione di pagamento.
   * @param idSegnalazione L'ID della segnalazione da prendere in carico.
   * @param gestoreId L'ID del gestore che prende in carico la segnalazione.
   * @returns Un Observable con la segnalazione aggiornata.
   */
  prendiInCaricoSegnalazione(
    idSegnalazione: number,
    gestoreId: string,
  ): Observable<SegnalazionePagamento> {
    const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    const params = { gestoreId };

    return this.http.patch<SegnalazionePagamento>(
      `${this.apiUrl}/prendiInCarico/${idSegnalazione}`,
      {},
      { headers, params },
    );
  }

  getAllSegnalazioni(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
