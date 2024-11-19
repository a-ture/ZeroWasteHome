import { Component } from '@angular/core';
import { NgForOf } from '@angular/common';

@Component({
  selector: 'app-card-home-page',
  standalone: true,
  imports: [NgForOf],
  templateUrl: './card-home-page.component.html',
  styleUrls: ['./card-home-page.component.css'],
})
export class CardHomePageComponent {
  sections = [
    { text: 'Area Personale', imageUrl: '', altText: 'Placeholder image' },
    {
      text: 'Prodotti in dispensa',
      imageUrl: '',
      altText: 'Placeholder image',
    },
    { text: 'Prodotti in frigo', imageUrl: '', altText: 'Placeholder image' },
    {
      text: 'Condividi Ricetta',
      imageUrl: '',
      altText: 'Placeholder image',
    },
    {
      text: 'Genera lista della spesa',
      imageUrl: '',
      altText: 'Placeholder image',
    },
    {
      text: 'Assistenza',
      imageUrl: '',
      altText: 'Placeholder image',
    },
  ];
}
