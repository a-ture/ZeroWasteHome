import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { NgClass, NgForOf } from '@angular/common';

@Component({
  selector: 'utility-bar',
  templateUrl: './utility-bar.component.html',
  standalone: true,
  styleUrls: ['./utility-bar.component.css'],
  imports: [navigationBtnComponent, NgForOf, NgClass],
})
export class UtilityBarComponent {
  // Etichetta attiva inizialmente nulla


      console.log(`Etichetta cliccata: ${label.title}`);
      this.activeLabel = label.title; // Aggiorna l'etichetta attiva
    }
  }

  // Input per i bottoni (oggetti con struttura definita)
}
