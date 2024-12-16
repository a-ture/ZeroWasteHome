import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from 'primeng/api';
import { environment } from '../../../environments/environment';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class LoginGestoreService {
  private apiUrl = `${environment.apiUrl}/public/admin/login`;

  constructor(
    private http: HttpClient,
    private messageService: MessageService,
    private router: Router,
  ) {}

  // Metodo per decodificare il JWT
  private decodeToken(token: string): any {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/'); // Sistema la parte base64
    return JSON.parse(atob(base64)); // Decodifica il payload e restituisce come oggetto
  }

  // Metodo per il login
  login(email: string, password: string): Observable<any> {
    const loginData = { email, password };
    return this.http.post<{ token: string }>(this.apiUrl, loginData).pipe(
      tap(response => {
        // Decodifica il token per estrarre i ruoli
        const decodedToken = this.decodeToken(response.token);
        const roles = decodedToken.roles; // Supponiamo che i ruoli siano nel campo 'roles'

        console.log(roles);
        // Salva sia il token che i ruoli nel localStorage
        localStorage.setItem('admin_token', response.token);
        localStorage.setItem('roles', JSON.stringify(roles));

        // Mostra un messaggio di successo
        this.messageService.add({
          severity: 'success',
          summary: 'Login effettuato',
          detail: 'Accesso eseguito con successo!',
          life: 4000,
        });

        if (roles == 'GESTORE_COMMUNITY') {
          this.router.navigate(['/gestore-community']);
        } else if (roles == 'GESTORE_PAGAMENTI') {
          this.router.navigate(['/gestore-segnalazioni']);
        }
      }),
      catchError(error => {
        return throwError(() => error.error?.message || 'Errore imprevisto durante il login');
      }),
    );
  }

  // Metodo per ottenere i ruoli dall'archivio
  getRoles(): string[] {
    const roles = localStorage.getItem('roles');
    return roles ? JSON.parse(roles) : [];
  }
}
