import { Component, Input } from '@angular/core';
import { NgIf } from '@angular/common';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { ConfermaAzioneModalService } from '../../services/servizio-conferma-azione/conferma-azione-modal.service'; // Importa il servizio per la gestione della modale

@Component({
  selector: 'app-conferma-azione',
  standalone: true,
  imports: [NgIf, navigationBtnComponent],
  templateUrl: './conferma-azione.component.html',
  styleUrl: './conferma-azione.component.css',
})
export class ConfermaAzioneComponent {
  @Input() text: string = '';
  // Variabile per tracciare la visibilità della modale
  isModalVisible: boolean = false;

  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: ConfermaAzioneModalService) {}

  // Metodo eseguito all'inizializzazione del componente
  ngOnInit(): void {
    // Sottoscrizione all'observable del servizio per aggiornare lo stato della modale
    this.modalService.modalVisible$.subscribe(
      isVisible => (this.isModalVisible = isVisible), // Aggiorna lo stato della variabile isModalVisible
    );
  }

  // Metodo per chiudere la modale
  closeModal(): void {
    this.modalService.closeModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su false
  }

  // Metodo per gestire il clic sull'overlay (area scura attorno alla modale)
  onOverlayClick(event: MouseEvent): void {
    this.closeModal(); // Chiude la modale quando si clicca sull'overlay
  }
}
