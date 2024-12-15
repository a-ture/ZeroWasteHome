import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, Observable, tap, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Ricetta } from './ricetta';

@Injectable({
  providedIn: 'root',
})
export class GestioneRicetteService {
  private apiUrl = `${environment.apiUrl}/utente/ricette`;

  constructor(private http: HttpClient) {}

  aggiungiRicetta(ricetta: Ricetta): Observable<Ricetta> {
    return this.http.post<Ricetta>(this.apiUrl, ricetta).pipe(
      tap((response: Ricetta) => {
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

  // Recupera le ricette dell'utente

  getRicetteByUtente(): Observable<Ricetta[]> {
    return this.http.get<Ricetta[]>(`${this.apiUrl}/ottieniRicette`);
  }

  getRicettaById(id: any): Observable<Ricetta> {
    return this.http.get<Ricetta>(`${this.apiUrl}/${id}`);
  }
}
