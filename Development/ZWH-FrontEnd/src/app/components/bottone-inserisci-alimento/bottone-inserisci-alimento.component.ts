import { Component } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component'; // Importa il componente navigationBtn
import { InserisciProdottoModalService } from '../../services/servizio-inserisci-prodotto/inserisci-prodotto-modal.service'; // Importa il servizio per la gestione della modale

@Component({
  selector: 'app-bottone-inserisci-alimento',
  standalone: true,
  imports: [navigationBtnComponent],
  templateUrl: './bottone-inserisci-alimento.component.html',
  styleUrl: './bottone-inserisci-alimento.component.css',
})
export class BottoneInserisciAlimentoComponent {
  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: InserisciProdottoModalService) {}

  // Metodo per aprire la modale
  openModal(): void {
    this.modalService.openModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su true
  }
}
