import { Component, Input } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { InfoModificheModalService } from '../../services/servizio-info-modifiche/info-modifiche-modal.service';

@Component({
  selector: 'app-bottone-info-modifiche',
  standalone: true,
  imports: [navigationBtnComponent],
  templateUrl: './bottone-info-modifiche.component.html',
  styleUrl: './bottone-info-modifiche.component.css',
})
export class BottoneInfoModificheComponent {
  @Input() text: string = '';
  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: InfoModificheModalService) {}

  // Metodo per aprire la modale
  openModal(): void {
    this.modalService.openModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su true
  }
}
