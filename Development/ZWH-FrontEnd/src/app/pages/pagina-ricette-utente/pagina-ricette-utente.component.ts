import { Component } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-pagina-ricette-utente',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    UtilityBarComponent,
    productTableComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './pagina-ricette-utente.component.html',
  styleUrl: './pagina-ricette-utente.component.css',
})
export class PaginaRicetteUtenteComponent {
  constructor(private router: Router) {}
  // Proprietà per il titolo della tabella
  tableTitle: string = 'Le Mie Ricette';

  // Proprietà per i buttons relativi alla tabella
  buttons = [
    {
      label: 'Aggiungi',
      action: () => this.router.navigate(['area-personale/le-mie-ricette/inserimento-ricetta']),
    },
  ];

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

  //l'id della ricetta va preso dinamicamente dal DB, bisogna avere gli id di tutte le
  //ricette, metterli in un array e assegnare ogni id all'evento
  private IdRicetta: number = 1;

  // Eventi per i bottoni
  buttonEvents = [
    () => this.router.navigate(['area-personale/le-mie-ricette/visualizza-ricetta']), // Visualizza
    () => console.log(`Modifica cliccato per ricetta ${this.IdRicetta}`), // Modifica
    () => console.log(`Condividi cliccato per ricetta ${this.IdRicetta}`), // Condividi
    () => console.log(`Elimina cliccato per ricetta ${this.IdRicetta}`), // Elimina
  ];
}
