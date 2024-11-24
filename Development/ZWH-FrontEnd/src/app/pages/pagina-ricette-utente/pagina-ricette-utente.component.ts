import { Component } from '@angular/core';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-pagina-ricette-utente',
  standalone: true,
  imports: [
    BreadcrumbBasicDemo,
    FooterComponent,
    HeaderComponent,
    UtilityBarComponent,
    productTableComponent,
  ],
  templateUrl: './pagina-ricette-utente.component.html',
  styleUrl: './pagina-ricette-utente.component.css',
})
export class PaginaRicetteUtenteComponent {
  // Proprietà per le voci del breadcrumb
  items: MenuItem[] = [
    { label: 'Area Personale', routerLink: '/' },
    { label: 'Le Mie Ricette', routerLink: '/' },
  ];

  // Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  // Proprietà per il titolo della tabella
  tableTitle: string = 'Le Mie Ricette';

  // Proprietà per i buttons relativi alla tabella
  buttons = [{ label: 'Aggiungi' }];

  // Dati per la lista dei prodotti
  recipeList = [
    {
      src: 'https://placehold.jp/200x200.png',
      info: [
        { name: 'Nome Ricetta', val: 'Ricetta 1' },
        { name: 'Tipologia Ricetta', val: 'Primo' },
      ],
    },
    {
      src: 'https://placehold.jp/200x200.png',
      info: [
        { name: 'Nome Ricetta', val: 'Ricetta 1' },
        { name: 'Tipologia Ricetta', val: 'Antipasto' },
      ],
    },
  ];

  // Proprietà per i buttons relativi ad ogni prodotto
  buttonList = ['Visualizza', 'Modifica', 'Condividi', 'Elimina'];
}
