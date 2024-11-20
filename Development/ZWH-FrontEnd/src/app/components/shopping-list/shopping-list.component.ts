import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgForOf } from '@angular/common';

interface ShoppingItem {
  name: string;
  quantita: number;
  checked: boolean;
}

@Component({
  selector: 'app-shopping-list',
  standalone: true,
  templateUrl: './shopping-list.component.html',
  imports: [FormsModule, NgForOf],
  styleUrls: ['./shopping-list.component.css'],
})
export class ShoppingListComponent {
  // items si aspetta una lista di oggetti ShoppingItem composti dal
  //nome del prodotto, dalla quantità e dallo stato della checkbox
  // esempio
  /*
  <app-shopping-list
    [items]="[
      { name: 'Pane', quantita: 1, checked: true },
      { name: 'Latte', quantita: 2, checked: true },
      { name: 'Uova', quantita: 3, checked: false },
      { name: 'Caviale', quantita: 2, checked: false },
    ]"
  ></app-shopping-list>
  */
  @Input() items: ShoppingItem[] = [];

  toggleSelection(item: ShoppingItem): void {
    item.checked = !item.checked;
  }
}
