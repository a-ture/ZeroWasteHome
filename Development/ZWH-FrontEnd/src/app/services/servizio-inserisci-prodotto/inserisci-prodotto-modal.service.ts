import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root',
})
export class InserisciProdottoModalService {
  private apiUrl = `${environment.apiUrl}/productExctraction`;
  private modalVisibleSubject = new BehaviorSubject<boolean>(false);
  modalVisible$ = this.modalVisibleSubject.asObservable();

  private modalRouteSubject = new BehaviorSubject<string | null>(null);
  modalRoute$ = this.modalRouteSubject.asObservable();

  openModal(route: string): void {
    console.log('Opening modal with route:', route); // Debug
    this.modalRouteSubject.next(route); // Imposta il percorso
    this.modalVisibleSubject.next(true); // Mostra la modale
  }

  closeModal(): void {
    console.log('Closing modal'); // Debug
    this.modalRouteSubject.next(null); // Resetta il percorso
    this.modalVisibleSubject.next(false); // Chiude la modale
  }

  constructor(private http: HttpClient) {}

  getProductDetails(barcode: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${barcode}`);
  }
}
