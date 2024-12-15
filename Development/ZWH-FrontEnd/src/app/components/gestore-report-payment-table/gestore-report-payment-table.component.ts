import { Component, Input } from '@angular/core';
import { DatePipe, NgForOf, NgIf, NgStyle } from '@angular/common';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { BottoneDettagliReportComponent } from '../bottone-dettagli-report/bottone-dettagli-report.component';
import { DettagliReportComponent } from '../dettagli-report/dettagli-report.component';
import { BottoneFormSegnalazioneComponent } from '../bottone-form-segnalazione/bottone-form-segnalazione.component';
import { FormSegnalazioneComponent } from '../form-segnalazione/form-segnalazione.component';
import { SegnalazionePagamentoService } from '../../services/Servizio-SegnalazionePagamento/segnalazione-pagamento.service';

@Component({
  selector: 'app-gestore-report-payment-table', // Selettore del componente, usato nei template HTML
  standalone: true, // Indica che il componente è standalone e non parte di un modulo Angular
  imports: [
    NgForOf, // Direttiva per iterare su array
    NgIf, // Direttiva per mostrare/nascondere elementi condizionatamente
    navigationBtnComponent, // Componente personalizzato per i pulsanti di navigazione
    DatePipe, // Pipe per formattare date
    NgStyle, // Direttiva per applicare stili dinamicamente
    BottoneDettagliReportComponent, // Componente per il bottone "Dettagli Report"
    DettagliReportComponent, // Componente per mostrare i dettagli di un report
    BottoneFormSegnalazioneComponent, // Componente per il bottone "Risolvi"
    FormSegnalazioneComponent, // Componente per la modale di risoluzione
  ],
  templateUrl: './gestore-report-payment-table.component.html', // Template HTML associato al componente
  styleUrls: ['./gestore-report-payment-table.component.css'], // Stile CSS associato al componente
})
export class GestoreReportPaymentTableComponent {
  // Lista dei report ricevuta come input dal componente genitore
  @Input() reports: {
    utente: string;
    info: {
      id: number;
      dataCreazione: Date;
      stato: string;
      descrizioneProblema: string;
      dettagli_risoluzione: string;
    }[];
  }[] = [];

  // Report selezionato per la modale
  selectedReport: {
    info: { id: number; dataCreazione: Date; stato: string; descrizioneProblema: string }[];
  } | null = null;

  // Costruttore per iniettare il servizio per la gestione delle segnalazioni
  constructor(private SegnalazionePagamentoService: SegnalazionePagamentoService) {}

  // Metodo per aprire la modale con il report selezionato
  apriModale(report: {
    info: { id: number; dataCreazione: Date; stato: string; descrizioneProblema: string }[];
  }): void {
    this.selectedReport = report; // Imposta il report selezionato
    // La logica per mostrare la modale può essere implementata direttamente o tramite un servizio
  }

  // Metodo per verificare se un report contiene almeno un elemento con stato "APERTA"
  isReportOpen(report: { info: { stato: string }[] }): boolean {
    return report.info.some(info => info.stato === 'APERTA'); // Ritorna true se almeno un elemento è "APERTA"
  }

  // Metodo per verificare se un report è già chiuso
  isReportClosed(report: { info: { stato: string }[] }): boolean {
    return report.info.some(info => info.stato === 'CHIUSA'); // Ritorna true se almeno un elemento è "CHIUSA"
  }

  // Indice del report selezionato per i dettagli
  selectedReportIndex: number | null = null;

  // Mostra o nasconde i dettagli di un report
  toggleDetails(index: number): void {
    this.selectedReportIndex = this.selectedReportIndex === index ? null : index; // Alterna tra l'indice selezionato e null
  }

  // Recupera i dettagli del report selezionato
  getDetails(index: number): string {
    // Concatena le descrizioni dei problemi del report selezionato
    return this.reports[index]?.info.map(info => info.descrizioneProblema).join('\n') || '';
  }

  // Metodo per prendere in carico un report
  prendiInCaricoReport(report: { info: { id: number }[] }): void {
    const idSegnalazione = report.info[0]?.id; // Ottiene l'ID del primo elemento della lista "info"
    if (idSegnalazione) {
      this.SegnalazionePagamentoService.prendiInCaricoSegnalazione(idSegnalazione).subscribe(
        response => {
          console.log('Report preso in carico con successo:', response); // Log della risposta del server
          this.aggiornaDati(); // Ricarica i dati dopo l'aggiornamento
        },
        error => {
          console.error('Errore durante la presa in carico del report:', error); // Log dell'errore
          alert('Errore durante la presa in carico del report.'); // Mostra un messaggio di errore
        },
      );
    } else {
      console.error('ID Segnalazione non trovato nel report.'); // Log se l'ID è assente
      alert('Impossibile prendere in carico il report: ID non valido.');
    }
  }

  // Metodo per aggiornare la lista dei report recuperandoli dal backend
  aggiornaDati(): void {
    this.SegnalazionePagamentoService.getAllSegnalazioni().subscribe({
      next: data => {
        this.reports = data; // Aggiorna la lista dei report con i dati più recenti
      },
      error: error => {
        console.error("Errore durante l'aggiornamento dei dati:", error); // Log dell'errore
      },
    });
  }
}
