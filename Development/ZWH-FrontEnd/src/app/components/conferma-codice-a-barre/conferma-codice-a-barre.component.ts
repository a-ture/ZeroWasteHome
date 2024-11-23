import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa il modulo CommonModule per abilitare direttive come *ngIf e *ngFor
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { InserisciProdottoModalService } from '../../services/servizio-modale/modal.service'; // Importa il servizio per la gestione della modale

@Component({
  selector: 'app-conferma-codice-a-barre',
  standalone: true,
  imports: [CommonModule, navigationBtnComponent], // Aggiungi CommonModule qui
  templateUrl: './conferma-codice-a-barre.component.html',
  styleUrls: ['./conferma-codice-a-barre.component.css'],
})
export class ConfermaCodiceABarreComponent {
  // Variabile per tracciare la visibilità della modale
  isModalVisible: boolean = false;

  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: InserisciProdottoModalService) {}

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
