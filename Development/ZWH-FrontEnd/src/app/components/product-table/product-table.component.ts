import { Component, Input } from '@angular/core';
import { Btn1Component } from '../NavigationBtn/NavigationBtn.component';
import { NgForOf } from '@angular/common';

@Component({
  selector: 'product-table',
  standalone: true,
  imports: [Btn1Component, NgForOf],
  templateUrl: './product-table.component.html',
  styleUrls: ['./product-table.component.css'],
})
export class TableTemplateDemo {
  // lista prodotti
  @Input() products: {
    src: string;
    info: { name: string; val: string }[];
  }[] = [];

  // bottoni da stampare
  @Input() buttuns: string[] = [];
}
