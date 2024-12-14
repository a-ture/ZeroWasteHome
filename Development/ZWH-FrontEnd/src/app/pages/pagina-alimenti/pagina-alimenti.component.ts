import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { InserisciProdottoModalService } from '../../services/servizio-inserisci-prodotto/inserisci-prodotto-modal.service';
import { NavigationEnd, Router } from '@angular/router';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { DispensaService } from '../../services/servizio-prodotti-dispensa/dispensa.service';
import { catchError, map, Observable, of } from 'rxjs';
import { AsyncPipe } from '@angular/common';
import { CommonModule } from '@angular/common';
import { Prodotto } from '../../services/servizio-prodotti-dispensa/prodotto';
@Component({
  selector: 'app-pagina-alimenti',
  standalone: true,
  imports: [
    CommonModule,
    HeaderComponent,
    DynamicFormComponent,
    UtilityBarComponent,
    productTableComponent,
    FooterComponent,
    BreadcrumbComponent,
    AsyncPipe,
  ],
  templateUrl: './pagina-alimenti.component.html',
  styleUrl: './pagina-alimenti.component.css',
})
export class PaginaAlimentiComponent {
  labels = [
    { title: 'Frigo', showVerticalLine: true },
    { title: 'Dispensa', showVerticalLine: false },
  ];
  tableTitle: string = 'Informazioni sugli Alimenti';
  activeLabel: string = 'Frigo';
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
    private modalService: InserisciProdottoModalService,
    private router: Router,
    private dispensaService: DispensaService,
  ) {}

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
