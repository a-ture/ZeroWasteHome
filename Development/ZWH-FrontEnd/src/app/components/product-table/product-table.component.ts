import { Component, Input } from '@angular/core';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';
import { NgForOf } from '@angular/common';

@Component({
  selector: 'product-table',
  standalone: true,
  imports: [navigationBtnComponent, NgForOf],
  templateUrl: './product-table.component.html',
  styleUrls: ['./product-table.component.css'],
})
export class productTableComponent {
  // Lista prodotti
  @Input() products: {
    src: string;
    info: { name: string; val: string }[];
  }[] = [];

  // ID dei prodotti
  @Input() productIds: number[] = [];

  // Bottoni da stampare
  @Input() buttuns: string[] = [];

  // Eventi dinamici
  @Input() events: ((id: number) => void)[] = [];

  // Struttura che associa bottoni, eventi e ID
  structButtons: { button: string; event: (id: number) => void }[] = [];

  ngOnInit(): void {
    // Associa i bottoni agli eventi
    this.structButtons = this.buttuns.map((button, index) => ({
      button: button,
      event: this.events[index] || (() => {}),
    }));
  }
}
