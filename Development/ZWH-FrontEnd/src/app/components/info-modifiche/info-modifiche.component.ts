import { Component, Input } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { InfoModificheModalService } from '../../services/servizio-info-modifiche/info-modifiche-modal.service';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-info-modifiche',
  standalone: true,
  imports: [navigationBtnComponent, NgIf],
  templateUrl: './info-modifiche.component.html',
  styleUrl: './info-modifiche.component.css',
})
export class InfoModificheComponent {
  @Input() text: string = '';
  // Variabile per tracciare la visibilità della modale
  isModalVisible: boolean = false;

  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: InfoModificheModalService) {}

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
