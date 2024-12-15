import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Prodotto } from './prodotto';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProdottiService {
  private apiUrl = `${environment.apiUrl}/utente/prodotti/ricerca`;
  constructor(private http: HttpClient) {}

  /**
   * Effettua una ricerca di prodotti per nome.
   * @param name Nome del prodotto da cercare.
   * @returns Observable contenente la lista di prodotti trovati.
   */
  ricercaProdottiPerNome(name: string): Observable<Prodotto[]> {
    return this.http.get<Prodotto[]>(`${this.apiUrl}?name=${encodeURIComponent(name)}`);
  }
}
