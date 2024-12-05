import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class InserisciProdottoModalService {
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
}
