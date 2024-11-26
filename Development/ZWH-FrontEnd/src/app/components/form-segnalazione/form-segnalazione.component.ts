import { Component } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { CommonModule } from '@angular/common'; // Importa il modulo CommonModule per abilitare direttive come *ngIf e *ngFor
import { FormSegnalazioneModalService } from '../../services/servizio-form-segnalazione/from-segnalazione-modal.service';

@Component({
  selector: 'app-form-segnalazione',
  standalone: true,
  imports: [navigationBtnComponent, CommonModule],
  templateUrl: './form-segnalazione.component.html',
  styleUrl: './form-segnalazione.component.css',
})
export class FormSegnalazioneComponent {
  // Variabile per tracciare la visibilità della modale
  isModalVisible: boolean = false;
  constructor(private modalService: FormSegnalazioneModalService) {}

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
