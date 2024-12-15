// Service: dispensa.service.ts
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Prodotto } from '../servizio-prodotti-dispensa/prodotto';

@Injectable({
  providedIn: 'root',
})
export class FrigoService {
  private apiUrl = `${environment.apiUrl}/utente/gestioneProdotto/frigo`;
  constructor(private http: HttpClient) {}

  visualizzaFrigo(): Observable<Prodotto[]> {
    return this.http.get<any[]>(this.apiUrl).pipe(
      map(
        response => response.map(item => Prodotto.fromApiResponse(item)), // Mappatura in oggetti Prodotto
      ),
    );
  }
}
