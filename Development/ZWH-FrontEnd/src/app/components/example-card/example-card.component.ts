import { Component, Input } from '@angular/core'; // Importa il decoratore Component per definire il componente e Input per passare i dati
import { Button } from 'primeng/button'; // Importa il modulo Button di PrimeNG per utilizzare i pulsanti
import { CardModule } from 'primeng/card'; // Importa CardModule di PrimeNG per visualizzare una card

@Component({
  selector: 'app-example-card', // Imposta il selettore per il componente come <app-example-card>
  standalone: true, // Indica che il componente è standalone, quindi non richiede un modulo esterno
  imports: [
    Button, // Importa il modulo PrimeNG Button per i pulsanti nel template
    CardModule, // Importa il modulo PrimeNG Card per la struttura della card
  ],
  templateUrl: './example-card.component.html', // Percorso del file HTML del template del componente
  styleUrl: './example-card.component.css', // Percorso del file CSS per gli stili del componente
})
export class ExampleCardComponent {
  // Definizione della classe del componente
  @Input() title: string = 'Default Title'; // Proprietà 'title' della card, con valore predefinito; può essere impostata dall'esterno
  @Input() subtitle: string = 'Default Subtitle'; // Proprietà 'subtitle' della card, con valore predefinito; può essere impostata dall'esterno
  @Input() content: string = 'Default content of the card.'; // Proprietà 'content' della card, con valore predefinito; può essere impostata dall'esterno
  @Input() imageUrl: string = 'https://via.placeholder.com/150'; // Proprietà 'imageUrl' per visualizzare un'immagine, con valore predefinito
}
