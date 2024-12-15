import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { Router } from '@angular/router';
import { GestioneRicetteService } from '../../services/gestione-ricette/gestione-ricette.service';

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
export class PaginaRicetteUtenteComponent implements OnInit {
  tableTitle: string = 'Le Mie Ricette';
  buttons = [
    {
      label: 'Aggiungi',
      action: () => this.router.navigate(['area-personale/le-mie-ricette/inserimento-ricetta']),
    },
  ];
  recipeList: any[] = [];

  constructor(
    private router: Router,
    private gestioneRicetteService: GestioneRicetteService,
  ) {}

  // Proprietà per i buttons relativi ad ogni prodotto
  buttonList = ['Visualizza', 'Modifica', 'Condividi', 'Elimina'];

  // Eventi per i bottoni
  buttonEvents = [
    (id: number) => this.visualizzaRicetta(id), // Visualizza
    (id: number) => console.log(), // Modifica
    (id: number) => console.log(), // Condividi
    (id: number) => console.log(), // Elimina
  ];

  visualizzaRicetta(id: number): void {
    this.gestioneRicetteService.getRicettaById(id).subscribe({
      next: data => {
        // Naviga alla pagina di visualizzazione della ricetta, passando l'ID
        this.router.navigate([`/area-personale/le-mie-ricette/visualizza-ricetta`, id]);
      },
      error: err => console.error('Errore durante il recupero della ricetta:', err),
    });
  }

  ngOnInit() {
    this.loadRicette();
  }

  loadRicette(): void {
    this.gestioneRicetteService.getRicetteByUtente().subscribe({
      next: data => {
        console.log(data);
        this.recipeList = data.map(ricetta => ({
          id: ricetta.id, // Aggiungi l'ID della ricetta
          src: 'https://placehold.jp/200x200.png',
          info: [
            { name: 'Nome Ricetta', val: ricetta.nome },
            { name: 'Tipologia Ricetta', val: ricetta.categoria },
          ],
        }));
      },
      error: err => console.error('Errore durante il caricamento delle ricette:', err),
    });
  }
}
