import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { InserisciProdottoModalService } from '../../services/servizio-inserisci-prodotto/inserisci-prodotto-modal.service';
import { ProdottiService } from '../../services/servizio-ricerca-prodotti/prodotti.service';
import { Prodotto as RicercaProdotto } from '../../services/servizio-ricerca-prodotti/prodotto';
import { HeaderComponent } from '../../components/header/header.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { DispensaService } from '../../services/servizio-prodotti-dispensa/dispensa.service';
import { catchError, map, Observable, of } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { Prodotto } from '../../services/servizio-prodotti-dispensa/prodotto';

@Component({
  selector: 'app-pagina-alimenti',
  standalone: true,
  templateUrl: './pagina-alimenti.component.html',
  styleUrl: './pagina-alimenti.component.css',
  imports: [
    CommonModule,
    HeaderComponent,
    BreadcrumbComponent,
    UtilityBarComponent,
    productTableComponent,
    FooterComponent,
    BreadcrumbComponent,
    AsyncPipe,
  ],
})
export class PaginaAlimentiComponent implements OnInit {
  labels = [
    { title: 'Frigo', showVerticalLine: true },
    { title: 'Dispensa', showVerticalLine: false },
  ];

  tableTitle: string = 'Informazioni sugli Alimenti';
  activeLabel: string = 'Frigo';
  searchQuery: string = '';

  buttons = [
    {
      label: 'Genera Ricetta',
      action: () => this.router.navigate(['alimenti/inserimento-ricetta']),
    },
    { label: 'Genera Lista', action: () => this.router.navigate(['alimenti/genera-lista']) },
    { label: 'Add aliments', action: () => this.openInserisciAlimentoModal() },
  ];
  productList: any[] = []; // Cambiato in array normale
  buttonList = ['Visualizza', 'Modifica', 'Elimina'];

  constructor(
    private prodottiService: ProdottiService,
    private router: Router,
    private dispensaService: DispensaService,
    private modalService: InserisciProdottoModalService,
  ) {}

  ngOnInit(): void {}

  /**
   * Trasforma la lista di prodotti nel formato richiesto per `productList`.
   * @param prodotti Lista di prodotti.
   * @returns Lista trasformata.
   */
  transformProductList(
    prodotti: RicercaProdotto[],
  ): { src: string; info: { name: string; val: string }[] }[] {
    return prodotti.map(prodotto => ({
      src: 'https://placehold.jp/200x200.png', // URL placeholder
      info: [
        { name: 'Nome', val: prodotto.nomeProdotto },
        { name: 'Quantità', val: prodotto.quantità.toString() },
        { name: 'Scadenza', val: prodotto.dataScadenza },
      ],
    }));
  }

  onSearchInput(event: Event): void {
    const input = (event.target as HTMLInputElement).value.trim();
    this.searchQuery = input;

    if (this.activeLabel === 'Dispensa' && this.searchQuery) {
      this.cercaProdotti(this.searchQuery);
    } else if (!this.searchQuery) {
      this.fetchDispensaProducts();
    }
  }

  cercaProdotti(query: string): void {
    this.prodottiService.ricercaProdottiPerNome(query).subscribe({
      next: prodotti => {
        this.productList = this.transformProductList(prodotti);
      },
      error: err => {
        console.error('Errore nella ricerca:', err);
        this.productList = []; // Svuota la lista in caso di errore
      },
    });
  }

  openInserisciAlimentoModal(): void {
    const route =
      this.activeLabel === 'Frigo'
        ? 'alimenti/inserimento-prodotto-frigo'
        : 'alimenti/inserimento-prodotto-dispensa';
    this.modalService.openModal(route);
  }

  onLabelChange(label: string): void {
    this.activeLabel = label;
    if (label === 'Dispensa') {
      this.fetchDispensaProducts();
    } else {
      this.productList = []; // Resetta la lista per la vista 'Frigo'
    }
  }

  private fetchDispensaProducts(): void {
    this.dispensaService.visualizzaDispensa().subscribe({
      next: (products: Prodotto[]) => {
        this.productList = products.map(product => ({
          src: 'https://via.placeholder.com/200', // Placeholder per immagini prodotto
          info: [
            { name: 'Nome', val: product.nomeProdotto },
            { name: 'Quantità', val: product.quantità },
            { name: 'Scadenza', val: product.dataScadenza },
          ],
        }));
      },
      error: error => {
        console.error('Errore durante il caricamento dei prodotti:', error);
        this.productList = []; // Resetta la lista in caso di errore
      },
    });
  }
}
