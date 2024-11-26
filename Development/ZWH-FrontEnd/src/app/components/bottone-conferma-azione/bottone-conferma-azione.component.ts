import { Component, Input } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component'; // Importa il componente navigationBtn
import { ConfermaAzioneModalService } from '../../services/servizio-conferma-azione/conferma-azione-modal.service'; // Importa il servizio per la gestione della modale

@Component({
  selector: 'app-bottone-conferma-azione',
  standalone: true,
  imports: [navigationBtnComponent],
  templateUrl: './bottone-conferma-azione.component.html',
  styleUrl: './bottone-conferma-azione.component.css',
})
export class BottoneConfermaAzioneComponent {
  @Input() text: string = '';
  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: ConfermaAzioneModalService) {}

  // Metodo per aprire la modale
  openModal(): void {
    this.modalService.openModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su true
  }
}
