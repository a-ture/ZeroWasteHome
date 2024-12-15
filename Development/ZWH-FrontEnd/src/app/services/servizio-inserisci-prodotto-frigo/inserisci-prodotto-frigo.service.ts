import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProdottoReq } from './prodottoRequestDTO';

@Injectable({
  providedIn: 'root',
})
export class InserisciProdottoFrigoService {
  private apiUrl = `${environment.apiUrl}/utente/gestioneProdotto/aggiungi-prodotto`;

  constructor(private http: HttpClient) {}

  aggiungiProdottoInFrigo(datiInserimento: ProdottoReq): Observable<ProdottoReq> {
    return this.http.post<ProdottoReq>(this.apiUrl, datiInserimento).pipe(
      tap((response: ProdottoReq) => {
        // Logica opzionale per il successo della richiesta
        console.log('Richiesta completata con successo:', response);
      }),
      catchError(error => {
        // Estrai il messaggio d'errore dal backend o restituisci un messaggio generico
        const errorMessage: string =
          error.error?.messaggio || 'Errore imprevisto durante il processo';
        console.error('Errore durante la richiesta:', errorMessage);
        return throwError(() => new Error(errorMessage));
      }),
    );
  }

}