// Service: dispensa.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Prodotto } from './prodotto';

@Injectable({
  providedIn: 'root',
})
export class DispensaService {
  private apiUrl = `${environment.apiUrl}/utente/gestioneProdotto/dispensa`;
  constructor(private http: HttpClient) {}

  visualizzaDispensa(): Observable<Prodotto[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(
        response => response.map(item => Prodotto.fromApiResponse(item)), // Mappatura in oggetti Prodotto
      ),
    );
  }
}
