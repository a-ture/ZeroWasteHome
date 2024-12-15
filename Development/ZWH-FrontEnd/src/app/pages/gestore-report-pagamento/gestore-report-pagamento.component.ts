import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { GestoreReportPaymentTableComponent } from '../../components/gestore-report-payment-table/gestore-report-payment-table.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { SegnalazionePagamentoService } from '../../services/Servizio-SegnalazionePagamento/segnalazione-pagamento.service';

@Component({
  selector: 'app-gestore-report-pagamento',
  standalone: true,
  imports: [HeaderComponent, GestoreReportPaymentTableComponent, FooterComponent],
  templateUrl: './gestore-report-pagamento.component.html',
  styleUrl: './gestore-report-pagamento.component.css',
})
export class GestoreReportPagamentoComponent {
  /**reports = [
    {
      info: [
        {
          descrizione: 'Segnalazione 1',
          utente: 'Mario Rossi',
          dataCreazione: new Date('2024-01-01'),
          statoSegnalazione: 'APERTA',
          dettagli: 'Questi sono i dettagli della segnalazione 1',
        },
      ],
    },
    {
      info: [
        {
          descrizione: 'Segnalazione 2',
          utente: 'Luigi Verdi',
          dataCreazione: new Date('2024-01-02'),
          statoSegnalazione: 'APERTA',
          dettagli: 'Questi sono i dettagli della segnalazione 2',
        },
      ],
    },
    {
      info: [
        {
          descrizione: 'Segnalazione 3',
          utente: 'Giulia Bianchi',
          dataCreazione: new Date('2024-01-03'),
          statoSegnalazione: 'IN_RISOLUZIONE',
          dettagli: 'Questi sono i dettagli della segnalazione 3',
        },
      ],
    },
  ];*/

  reports: any[] = [];

  constructor(private SegnalazionePagamentoService: SegnalazionePagamentoService) {}

  ngOnInit(): void {
    this.SegnalazionePagamentoService.getAllSegnalazioni().subscribe(data => {
      this.reports = data;
    });
  }
}
