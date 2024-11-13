import { Component } from '@angular/core'; // Importa il decoratore Component per definire un componente Angular
import { ExampleCardComponent } from '../example-card/example-card.component'; // Importa il componente ExampleCardComponent
import { CardModule } from 'primeng/card'; // Importa CardModule di PrimeNG per le funzionalità di visualizzazione delle card
import { NgFor } from '@angular/common'; // Importa NgFor per la direttiva *ngFor

@Component({
  selector: 'app-example-cards-list', // Imposta il selettore per il componente
  standalone: true, // Indica che questo componente è standalone (non necessita di un modulo esterno)
  imports: [
    ExampleCardComponent, // Importa ExampleCardComponent per l'utilizzo nelle template
    CardModule, // Importa CardModule per utilizzare il layout delle card di PrimeNG
    NgFor, // Importa NgFor per poter iterare su array con *ngFor
  ],
  templateUrl: './example-cards-list.component.html', // Percorso del file HTML del template
  styleUrl: './example-cards-list.component.css', // Percorso del file CSS per gli stili
})
export class ExampleCardsListComponent {
  // Definizione della classe del componente
  cards = [
    // Definisce un array di oggetti card con proprietà per titolo, sottotitolo, contenuto e immagine
    {
      title: 'Card 1',
      subtitle: 'Sottotitolo 1',
      content: 'Contenuto della prima card.',
      image: 'https://primefaces.org/cdn/primeng/images/card-ng.jpg',
    },
    {
      title: 'Card 2',
      subtitle: 'Sottotitolo 2',
      content: 'Contenuto della seconda card.',
      image: 'https://primefaces.org/cdn/primeng/images/card-ng.jpg',
    },
    {
      title: 'Card 3',
      subtitle: 'Sottotitolo 3',
      content: 'Contenuto della terza card.',
      image: 'https://primefaces.org/cdn/primeng/images/card-ng.jpg',
    },
  ];
}
