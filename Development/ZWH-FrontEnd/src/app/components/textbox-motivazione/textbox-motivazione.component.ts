import { Component, OnInit } from '@angular/core';
import { TextboxMotivazioneModalService } from '../../services/servizio-motivazione-blocco/textbox-motivazione-blocco.service';
import { FormsModule } from '@angular/forms';
import { BottoneConfermaAzioneComponent } from '../bottone-conferma-azione/bottone-conferma-azione.component';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-textbox-motivazione-blocco',
  templateUrl: './textbox-motivazione.component.html',
  styleUrls: ['./textbox-motivazione.component.css'],
  standalone: true,
  imports: [FormsModule, BottoneConfermaAzioneComponent, NgIf],
})
export class TextBoxMotivazioneModalComponent implements OnInit {
  showModal: boolean = false;
  motivazione: string = '';

  constructor(private modalService: TextboxMotivazioneModalService) {}

  ngOnInit() {
    // Iscriviti allo stato della modale
    this.modalService.showModal$.subscribe(isVisible => {
      this.showModal = isVisible;
    });
  }

  // Funzione per chiudere la modale
  closeModal() {
    this.modalService.closeModal();
  }

  // Funzione per inviare la motivazione (placeholder per il salvataggio)
  submitMotivazione() {
    console.log('Motivazione inviata:', this.motivazione);
    this.closeModal(); // Chiudi la modale dopo l'invio
  }
}
