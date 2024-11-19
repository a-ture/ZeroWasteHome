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
  @Input() items: ShoppingItem[] = [];

  toggleSelection(item: ShoppingItem): void {
    item.checked = !item.checked;
  }
}
