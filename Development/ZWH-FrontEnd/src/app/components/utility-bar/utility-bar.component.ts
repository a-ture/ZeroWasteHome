import { Component, EventEmitter, Input, Output } from '@angular/core';
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
  activeLabel: string | null = 'Frigo';

  // Input per le etichette selezionabili
  @Input() labels: { title: string; showVerticalLine: boolean }[] = [];

  @Input() title: string = ''; // Proprietà per il titolo

  @Output() labelChanged = new EventEmitter<string>();

  // se la label viene cliccata esegue un operazione che aggiorna activeLabel
  onLabelClick(label: { title: string }) {
    if (this.activeLabel !== label.title) {
      console.log(`Etichetta cliccata: ${label.title}`);
      this.activeLabel = label.title; // Aggiorna l'etichetta attiva
      this.labelChanged.emit(this.activeLabel); // Notifica al genitore
    }
  }

  // Input per i bottoni (oggetti con struttura definita)
  @Input() buttons: { label: string }[] = [];
}
