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
      imageUrl: './assets/img/homePageLoggatoCard/area_personale.jpg',
      altText: 'Placeholder image',
      routerLink: '/area-personale',
    },
    {
      text: 'I Miei Prodotti',
      imageUrl: './assets/img/homePageLoggatoCard/i_miei_prodotti.jpg',
      altText: 'Placeholder image',
      routerLink: '/alimenti',
    },
    {
      text: 'Le Mie Ricette',
      imageUrl: './assets/img/homePageLoggatoCard/ricette.jpg',
      altText: 'Placeholder image',
      routerLink: '/area-personale/le-mie-ricette',
    },
    {
      text: 'Condividi Ricetta',
      imageUrl: './assets/img/homePageLoggatoCard/condividi_ricetta.jpg',
      altText: 'Placeholder image',
      routerLink: '/home',
    },
    {
      text: 'Genera lista della spesa',
      imageUrl: './assets/img/homePageLoggatoCard/genera_lista_della_spesa.jpg',
      altText: 'Placeholder image',
      routerLink: '/alimenti/genera-lista',
    },
    {
      text: 'Assistenza',
      imageUrl: './assets/img/homePageLoggatoCard/assistenza.jpg',
      altText: 'Placeholder image',
      routerLink: '/assistenza',
    },
  ];
}
