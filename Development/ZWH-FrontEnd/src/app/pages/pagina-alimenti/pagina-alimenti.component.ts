import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { InserisciProdottoModalService } from '../../services/servizio-inserisci-prodotto/inserisci-prodotto-modal.service';
import { NavigationEnd, Router } from '@angular/router';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-pagina-alimenti',
  standalone: true,
  imports: [
    HeaderComponent,
    DynamicFormComponent,
    UtilityBarComponent,
    productTableComponent,
    FooterComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './pagina-alimenti.component.html',
  styleUrl: './pagina-alimenti.component.css',
})
export class PaginaAlimentiComponent {
  // Proprietà per le label selezionabili
  labels = [
    { title: 'Frigo', showVerticalLine: true },
    { title: 'Dispensa', showVerticalLine: false },
  ];

  // Proprietà per il titolo della tabella
  tableTitle: string = 'Informazioni sugli Alimenti';
  activeLabel: string = 'Frigo'; // Etichetta attiva di default

  // Proprietà per i buttons relativi alla tabella
  buttons = [
    {
      label: 'Genera Ricetta',
      action: () => this.router.navigate(['alimenti/inserimento-ricetta']),
    },
    { label: 'Genera Lista', action: () => this.router.navigate(['alimenti/genera-lista']) },
    { label: 'Add aliments', action: () => this.openInserisciAlimentoModal() },
  ];

  constructor(
    private modalService: InserisciProdottoModalService,
    private router: Router,
  ) {}

  openInserisciAlimentoModal(): void {
    const route =
      this.activeLabel === 'Frigo'
        ? 'alimenti/inserimento-prodotto-frigo'
        : 'alimenti/inserimento-prodotto-dispensa';

    console.log('Opening modal with route:', route); // Debug
    this.modalService.openModal(route);
  }

  // Dati per la lista dei prodotti
  productList = [
    {
      src: 'https://placehold.jp/200x200.png',
      info: [
        { name: 'Nome', val: 'Prodotto 1' },
        { name: 'Quantità', val: '1' },
        { name: 'Scadenza', val: '14/8/2025' },
      ],
    },
    {
      src: 'https://placehold.jp/200x200.png',
      info: [
        { name: 'Nome', val: 'Prodotto 2' },
        { name: 'Quantità', val: '2' },
        { name: 'Scadenza', val: '20/12/2025' },
      ],
    },
  ];

  // Proprietà per i buttons relativi ad ogni prodotto
  buttonList = ['Visualizza', 'Modifica', 'Elimina'];

  onLabelChange(label: string): void {
    this.activeLabel = label;
    console.log('Active Label:', this.activeLabel); // Debug
  }
}
