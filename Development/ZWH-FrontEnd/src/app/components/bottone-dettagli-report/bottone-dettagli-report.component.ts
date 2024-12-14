import { Component, Input } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { ServizioDettgliReportService } from '../../services/servizio-dettagli-report/servizio-dettgli-report.service';

@Component({
  selector: 'app-bottone-dettagli-report',
  standalone: true,
  imports: [navigationBtnComponent],
  templateUrl: './bottone-dettagli-report.component.html',
  styleUrl: './bottone-dettagli-report.component.css',
})
export class BottoneDettagliReportComponent {
  @Input() text: string = '';
  // Costruttore: inietta il servizio per la gestione della modale
  constructor(private modalService: ServizioDettgliReportService) {}

  // Metodo per aprire la modale
  openModal(): void {
    this.modalService.openModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su true
  }
}
