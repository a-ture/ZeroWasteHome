import { Injectable } from '@angular/core'; // Importa il decoratore Injectable per definire un servizio
import { BehaviorSubject } from 'rxjs'; // Importa BehaviorSubject per gestire lo stato reattivo

@Injectable({
  providedIn: 'root', // Rende il servizio disponibile globalmente in tutta l'app
})
export class InserisciProdottoModalService {
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
}
