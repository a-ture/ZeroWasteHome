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
  // lista prodotti
  @Input() products: {
    src: string;
    info: { name: string; val: string }[];
  }[] = [];

  // bottoni da stampare
  @Input() buttuns: string[] = [];
}
