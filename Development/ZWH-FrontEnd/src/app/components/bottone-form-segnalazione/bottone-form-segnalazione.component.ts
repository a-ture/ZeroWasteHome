import { Component } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import {
  InserisciProdottoModalService
} from '../../services/servizio-inserisci-prodotto/inserisci-prodotto-modal.service';
import {
  FormSegnalazioneModalService
} from '../../services/servizio-form-segnalazione/from-segnalazione-modal.service';

@Component({
  selector: 'app-bottone-form-segnalazione',
  standalone: true,
  imports: [navigationBtnComponent],
  templateUrl: './bottone-form-segnalazione.component.html',
  styleUrl: './bottone-form-segnalazione.component.css',
})
export class BottoneFormSegnalazioneComponent {
  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: FormSegnalazioneModalService) {}

  // Metodo per aprire la modale
  openModal(): void {
    this.modalService.openModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su true
  }
}
