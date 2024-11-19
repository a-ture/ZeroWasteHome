import { Component } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ToastModule } from 'primeng/toast';
import { ButtonModule } from 'primeng/button';

@Component({
  selector: 'confirm-dialog-basic-demo',
  templateUrl: './confirm-dialog.component.html',
  standalone: true,
  imports: [ConfirmDialogModule, ToastModule, ButtonModule], // Import dei moduli PrimeNG richiesti
  providers: [ConfirmationService, MessageService], // Servizi necessari per dialoghi di conferma e notifiche
})
export class ConfirmDialogBasicDemo {
  constructor(
    private confirmationService: ConfirmationService, // Gestisce le finestre di dialogo di conferma
    private messageService: MessageService, // Mostra notifiche tramite Toast
  ) {}

  confirm1(event: Event) {
    // Mostra un dialogo di conferma
    this.confirmationService.confirm({
      target: event.target as EventTarget, // Imposta il target del dialogo
      message: 'Sei sicuro di voler procedere?', // Messaggio mostrato nel dialogo
      header: 'Conferma', // Titolo del dialogo
      icon: 'pi pi-exclamation-triangle', // Icona del dialogo
      acceptIcon: 'none', // Nasconde l'icona di accettazione
      rejectIcon: 'none', // Nasconde l'icona di rifiuto
      rejectButtonStyleClass: 'p-button-text', // Stile per il pulsante di rifiuto
      accept: () => {
        // Azione eseguita quando l'utente accetta
        this.messageService.add({
          severity: 'info', // Tipo di messaggio
          summary: 'Confermato!', // Titolo del messaggio
          detail: 'Sei abbonato! Benvenuto tra noi!', // Dettagli del messaggio
        });
      },
      reject: () => {
        // Azione eseguita quando l'utente rifiuta
        this.messageService.add({
          severity: 'error', // Tipo di messaggio
          summary: 'Rifiuto!', // Titolo del messaggio
          detail: 'Ritorno alla home page', // Dettagli del messaggio
          life: 3000, // Durata del messaggio (ms)
        });
      },
    });
  }
}
