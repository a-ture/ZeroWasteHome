import { Component, Input } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { CommonModule } from '@angular/common'; // Importa il modulo CommonModule per abilitare direttive come *ngIf e *ngFor
import { FormSegnalazioneModalService } from '../../services/servizio-form-segnalazione/from-segnalazione-modal.service';
import { BottoneFormSegnalazioneComponent } from '../bottone-form-segnalazione/bottone-form-segnalazione.component';
import { SegnalazionePagamentoService } from '../../services/Servizio-SegnalazionePagamento/segnalazione-pagamento.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-form-segnalazione', // Selettore del componente, usato nel template HTML per inserirlo
  standalone: true, // Indica che il componente è standalone (non parte di un modulo Angular)
  imports: [navigationBtnComponent, CommonModule, BottoneFormSegnalazioneComponent, FormsModule], // Moduli e componenti importati per il funzionamento del componente
  templateUrl: './form-segnalazione.component.html', // Percorso al template HTML del componente
  styleUrl: './form-segnalazione.component.css', // Percorso al file CSS del componente
})
export class FormSegnalazioneComponent {
  // Input per ricevere il titolo della modale dal genitore
  @Input() title: string = 'Spiegaci il tuo problema'; // Titolo con valore predefinito

  // Input per ricevere il report selezionato dal componente padre
  @Input() report: {
    info: { id: number; dataCreazione: Date; stato: string; descrizioneProblema: string }[];
  } | null = null;

  // Variabile per tracciare la visibilità della modale
  isModalVisible: boolean = false;

  // Variabile per memorizzare il testo della risoluzione inserito nella textarea
  dettagliRisoluzione: string = '';

  // Costruttore che inietta i servizi necessari per la gestione della modale e delle segnalazioni
  constructor(
    private modalService: FormSegnalazioneModalService, // Gestisce la visibilità della modale
    private SegnalazionePagamentoService: SegnalazionePagamentoService, // Gestisce le operazioni legate alle segnalazioni di pagamento
  ) {}

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

  // Metodo per risolvere il report
  risoluzioneReport(): void {
    // Controlla che il report e l'ID della segnalazione siano validi
    if (this.report && this.report.info[0]?.id) {
      const idSegnalazione = this.report.info[0].id; // Ottiene l'ID della segnalazione
      const risoluzione = this.dettagliRisoluzione; // Ottiene i dettagli della risoluzione

      // Verifica che l'ID e la risoluzione siano presenti
      if (idSegnalazione && risoluzione) {
        // Chiama il servizio per risolvere la segnalazione
        this.SegnalazionePagamentoService.risolvereSegnalazione(
          idSegnalazione,
          risoluzione,
        ).subscribe(
          response => {
            console.log('Report risolto con successo:', response); // Log della risposta del server
            this.closeModal(); // Chiude la modale
            window.location.reload(); // Ricarica la pagina per aggiornare i dati
          },
          error => {
            console.error('Errore durante la risoluzione del report:', error); // Log dell'errore
            alert('Errore durante la risoluzione del report.'); // Mostra un messaggio di errore
          },
        );
      } else {
        // Mostra un messaggio se la risoluzione non è valida o l'ID non è presente
        alert('Risoluzione non valida o ID non trovato.');
      }
    } else {
      // Mostra un messaggio se nessun report è stato selezionato
      alert('Nessun report selezionato.');
    }
  }
}
