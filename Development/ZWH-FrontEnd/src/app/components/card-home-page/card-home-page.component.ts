import { Component } from '@angular/core';
import { NgForOf } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-card-home-page',
  standalone: true,
  imports: [NgForOf, RouterLink],
  templateUrl: './card-home-page.component.html',
  styleUrls: ['./card-home-page.component.css'],
})
export class CardHomePageComponent {
  sections = [
    {
      text: 'Area Personale',
      imageUrl: '',
      altText: 'Placeholder image',
      routerLink: '/area-personale',
    },
    {
      text: 'Prodotti in dispensa',
      imageUrl: '',
      altText: 'Placeholder image',
      routerLink: '/alimenti',
    },
    {
      text: 'Prodotti in frigo',
      imageUrl: '',
      altText: 'Placeholder image',
      routerLink: '/alimenti',
    },
    {
      text: 'Condividi Ricetta',
      imageUrl: '',
      altText: 'Placeholder image',
      routerLink: '/home',
    },
    {
      text: 'Genera lista della spesa',
      imageUrl: '',
      altText: 'Placeholder image',
      routerLink: '/alimenti/genera-lista',
    },
    {
      text: 'Assistenza',
      imageUrl: '',
      altText: 'Placeholder image',
      routerLink: '/assistenza',
    },
  ];
}
