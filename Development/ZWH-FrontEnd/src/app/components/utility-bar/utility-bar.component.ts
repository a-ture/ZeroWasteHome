import { Component, Input } from '@angular/core';
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
  activeLabel: string | null = null;

  // Input per le etichette (testo statico)
  @Input() labels: { title: string; cliccable: boolean }[] = [];

  // se la label viene cliccata esegue un operazione se
  // label.cliccable === true
  onLabelClick(label: { title: string; cliccable: boolean }) {
    if (label.cliccable) {
      console.log(`Etichetta cliccata: ${label.title}`);
      this.activeLabel = label.title; // Aggiorna l'etichetta attiva
    }
  }

  // Input per i bottoni (oggetti con struttura definita)
  @Input() buttons: { label: string }[] = [];
}
