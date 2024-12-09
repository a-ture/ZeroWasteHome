import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, tap, throwError } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LoginModalService {
  // Stato della visibilità della modale gestito tramite BehaviorSubject
  // Il valore iniziale è `false` (la modale è chiusa)
  private modalVisibleSubject = new BehaviorSubject<boolean>(false);

  // Observable per permettere ad altri componenti di sottoscriversi e reagire
  // ai cambiamenti dello stato della visibilità della modale
  modalVisible$ = this.modalVisibleSubject.asObservable();

  // Metodo per aprire la modale
  // Imposta il valore di `modalVisibleSubject` su `true` per indicare che la modale deve essere visibile
  openModal(): void {
    this.modalVisibleSubject.next(true); // Notifica a tutti i sottoscrittori che la modale è stata aperta
  }

  // Metodo per chiudere la modale
  // Imposta il valore di `modalVisibleSubject` su `false` per indicare che la modale deve essere chiusa
  closeModal(): void {
    this.modalVisibleSubject.next(false); // Notifica a tutti i sottoscrittori che la modale è stata chiusa
  }

  // Endpoint per il login
  private apiUrl = `${environment.apiUrl}/public/login`;

  constructor(
    private http: HttpClient,
    private router: Router,
  ) {}

  // Metodo per eseguire il login
  login(email: string, password: string): Observable<any> {
    const loginData = { email, password }; // Dati da inviare nel corpo
    return this.http.post<LoginResponse>(this.apiUrl, loginData).pipe(
      tap(response => {
        localStorage.setItem('token', response.token); //salva il token
        this.router.navigate(['/home']);
      }),
      catchError(error => {
        // Estrai il messaggio d'errore dal backend
        return throwError(() => error.error?.messaggio || 'Errore imprevisto durante il login');
      }),
    );
  }
}

export interface LoginResponse {
  token: string;
}
