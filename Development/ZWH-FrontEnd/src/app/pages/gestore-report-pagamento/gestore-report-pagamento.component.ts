import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component'; // Importa il componente per l'header
import { GestoreReportPaymentTableComponent } from '../../components/gestore-report-payment-table/gestore-report-payment-table.component'; // Importa il componente della tabella
import { FooterComponent } from '../../components/footer/footer.component'; // Importa il componente per il footer
import { SegnalazionePagamentoService } from '../../services/Servizio-SegnalazionePagamento/segnalazione-pagamento.service'; // Importa il servizio per la gestione delle segnalazioni di pagamento

@Component({
  selector: 'app-gestore-report-pagamento', // Selettore del componente, usato nei template HTML
  standalone: true, // Indica che il componente è standalone e non parte di un modulo Angular
  imports: [HeaderComponent, GestoreReportPaymentTableComponent, FooterComponent], // Importa i componenti utilizzati nel template
  templateUrl: './gestore-report-pagamento.component.html', // Template HTML associato al componente
  styleUrl: './gestore-report-pagamento.component.css', // Stile CSS associato al componente
})
export class GestoreReportPagamentoComponent {
  // Variabile che contiene la lista dei report da mostrare nel componente figlio
  reports: any[] = [];

  // Costruttore che inietta il servizio per recuperare le segnalazioni di pagamento
  constructor(private SegnalazionePagamentoService: SegnalazionePagamentoService) {}

  // Metodo del ciclo di vita Angular eseguito all'inizializzazione del componente
  ngOnInit(): void {
    // Recupera tutte le segnalazioni tramite il servizio
    this.SegnalazionePagamentoService.getAllSegnalazioni().subscribe(data => {
      this.reports = data; // Assegna i dati recuperati alla variabile 'reports'
    });
  }
}
