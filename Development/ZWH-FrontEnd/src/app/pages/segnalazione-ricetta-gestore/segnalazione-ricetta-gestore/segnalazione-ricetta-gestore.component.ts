import { Component, OnInit } from '@angular/core';
import { SegnalazioneRicettaService } from '../../../services/servizio-segnalazione-ricetta-gestore/segnalazione-ricetta.service';
import { HeaderComponent } from '../../../components/header/header.component';
import { BreadcrumbComponent } from '../../../components/breadcrumb/breadcrumb.component';
import { FooterComponent } from '../../../components/footer/footer.component';
import { CommonModule } from '@angular/common';
import { navigationBtnComponent } from '../../../components/navigationBtn/navigationBtn.component';

@Component({
  selector: 'app-segnalazione-ricetta-gestore',
  templateUrl: './segnalazione-ricetta-gestore.component.html',
  styleUrls: ['./segnalazione-ricetta-gestore.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    HeaderComponent,
    BreadcrumbComponent,
    FooterComponent,
    navigationBtnComponent,
  ],
})
export class SegnalazioneRicettaGestoreComponent implements OnInit {
  segnalazioni: any[] = []; // Array per le segnalazioni

  constructor(private segnalazioneService: SegnalazioneRicettaService) {}

  ngOnInit() {
    this.caricaSegnalazioni();
  }

  caricaSegnalazioni() {
    this.segnalazioneService.getSegnalazioni().subscribe({
      next: data => {
        this.segnalazioni = data; // Popola l'array con le segnalazioni
      },
      error: error => {
        console.error('Errore durante il caricamento delle segnalazioni:', error);
      },
    });
  }

  onBloccaAutoreClick(id: number) {
    const motivoBlocco = prompt('Inserisci il motivo del blocco:') || '';
    if (!motivoBlocco.trim()) {
      alert('Motivo del blocco obbligatorio!');
      return;
    }
    console.log(motivoBlocco);
    this.segnalazioneService.risolviSegnalazione(id, motivoBlocco).subscribe({
      next: response => {
        console.log('Segnalazione risolta:', response);
        alert('Segnalazione risolta con successo!');
        this.caricaSegnalazioni(); // Aggiorna la lista delle segnalazioni
      },
      error: error => {
        console.error('Errore durante la risoluzione della segnalazione:', error);
        alert('Errore durante la risoluzione della segnalazione!');
      },
    });
  }
}
