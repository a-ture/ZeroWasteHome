import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class TextboxMotivazioneModalService {
  private showModalSubject = new Subject<boolean>();
  private motivoBlocco: string = ''; // Proprietà per memorizzare il motivo del blocco

  // Observable per ascoltare la visibilità della modale
  showModal$ = this.showModalSubject.asObservable();

  // Funzione per aprire la modale
  openModal() {
    this.showModalSubject.next(true);
  }

  // Funzione per chiudere la modale
  closeModal() {
    this.showModalSubject.next(false);
  }

  // Funzione per impostare il motivo del blocco
  setMotivoBlocco(motivo: string): void {
    this.motivoBlocco = motivo;
  }

  // Funzione per ottenere il motivo del blocco
  getMotivoBlocco(): string {
    return this.motivoBlocco;
  }
}
