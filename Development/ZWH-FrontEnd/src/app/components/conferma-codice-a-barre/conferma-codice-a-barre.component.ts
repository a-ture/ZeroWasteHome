import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa il modulo CommonModule per abilitare direttive come *ngIf e *ngFor
import { Router } from '@angular/router';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { InserisciProdottoModalService } from '../../services/servizio-inserisci-prodotto/inserisci-prodotto-modal.service';
import { FormsModule } from '@angular/forms'; // Importa il servizio per la gestione della modale

@Component({
  selector: 'app-conferma-codice-a-barre',
  standalone: true,
  imports: [CommonModule, navigationBtnComponent, FormsModule], // Aggiungi CommonModule qui
  templateUrl: './conferma-codice-a-barre.component.html',
  styleUrls: ['./conferma-codice-a-barre.component.css'],
})
export class ConfermaCodiceABarreComponent implements OnInit {
  // Variabile per tracciare la visibilità della modale
  isModalVisible: boolean = false;
  barcode: string = ''; // Memorizza il codice a barre inserito dall'utente
  modalRoute: string | null = null; // Variabile per tracciare il percorso associato alla modale

  // Costruttore: inietta il servizio per la gestione della modale e il router
  constructor(
    private modalService: InserisciProdottoModalService,
    private router: Router,
  ) {}

  // Metodo eseguito all'inizializzazione del componente
  ngOnInit(): void {
    // Sottoscrizione all'observable del servizio per aggiornare lo stato della modale
    this.modalService.modalVisible$.subscribe(isVisible => (this.isModalVisible = isVisible));

    // Sottoscrizione all'observable del percorso per aggiornare il percorso corrente
    this.modalService.modalRoute$.subscribe(route => (this.modalRoute = route));
  }

  // Metodo per chiudere la modale
  closeModal(): void {
    this.modalService.closeModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su false
  }

  // Metodo per gestire il clic sull'overlay (area scura attorno alla modale)
  onOverlayClick(event: MouseEvent): void {
    this.closeModal(); // Chiude la modale quando si clicca sull'overlay
  }

  // Metodo per navigare al percorso associato
  navigate(): void {
    if (this.modalRoute) {
      // Dividi il percorso in segmenti e verifica che non ci siano segmenti vuoti
      const routeSegments = this.modalRoute.split('/').filter(segment => segment.trim() !== '');

      if (routeSegments.length > 0) {
        console.log('Navigating to:', this.modalRoute); // Debug
        this.closeModal();
        this.router.navigate(routeSegments); // Usa i segmenti filtrati per la navigazione
      } else {
        console.error('Invalid route detected:', this.modalRoute); // Debug per percorsi non validi
      }
    } else {
      console.error('Modal route is null or undefined'); // Debug se modalRoute è nullo
    }
  }

  // Metodo per inviare il codice e navigare alla pagina successiva
  confirmBarcode() {
    if (!this.barcode) {
      alert('Inserire un codice valido!');
      return;
    }
    if (this.barcode.length < 8 || this.barcode.length > 16) {
      alert(
        'Il codice deve avere una lunghezza minima di 8 caratteri, massima di 16 e deve contenere solo cifre',
      );
      return;
    }
    this.modalService.getProductDetails(this.barcode).subscribe({
      next: productDetails => {
        // Naviga alla pagina successiva passando i dettagli del prodotto
        this.router.navigate([this.modalRoute], { state: { productDetails } });
        this.closeModal();
      },
      error: err => {
        const errorMessage = err.error?.message || 'Si è verificato un errore. Riprova piu tardi.';
        alert(errorMessage);
      },
    });
  }
}
