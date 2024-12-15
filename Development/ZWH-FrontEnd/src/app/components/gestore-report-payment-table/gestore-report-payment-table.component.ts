import { Component, Input } from '@angular/core';
import { DatePipe, NgForOf, NgIf, NgStyle } from '@angular/common';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { BottoneDettagliReportComponent } from '../bottone-dettagli-report/bottone-dettagli-report.component';
import { DettagliReportComponent } from '../dettagli-report/dettagli-report.component';
import { BottoneFormSegnalazioneComponent } from '../bottone-form-segnalazione/bottone-form-segnalazione.component';
import { FormSegnalazioneComponent } from '../form-segnalazione/form-segnalazione.component';
import { SegnalazionePagamentoService } from '../../services/Servizio-SegnalazionePagamento/segnalazione-pagamento.service';
@Component({
  selector: 'app-gestore-report-payment-table',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    navigationBtnComponent,
    DatePipe,
    NgStyle,
    BottoneDettagliReportComponent,
    DettagliReportComponent,
    NgIf,
    BottoneFormSegnalazioneComponent,
    FormSegnalazioneComponent,
  ],
  templateUrl: './gestore-report-payment-table.component.html',
  styleUrls: ['./gestore-report-payment-table.component.css'],
})
export class GestoreReportPaymentTableComponent {
  // lista report
  @Input() reports: {
    utente: string;
    info: {
      dataCreazione: Date;
      stato: string;
      descrizioneProblema: string;
    }[];
  }[] = [];

  // Metodo per controllare se il bottone deve essere visibile
  isReportOpen(report: { info: { stato: string }[] }): boolean {
    return report.info.some(info => info.stato === 'APERTA');
  }
  // Indice del report selezionato per i dettagli
  selectedReportIndex: number | null = null;

  // Mostra o nasconde i dettagli di un report
  toggleDetails(index: number): void {
    this.selectedReportIndex = this.selectedReportIndex === index ? null : index;
  }

  // Recupera i dettagli del report selezionato
  getDetails(index: number): string {
    return this.reports[index]?.info.map(info => info.descrizioneProblema).join('\n') || '';
  }
}
