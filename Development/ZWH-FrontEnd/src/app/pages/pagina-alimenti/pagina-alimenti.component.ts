import { Component } from '@angular/core';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { HeaderComponent } from '../../components/header/header.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { MenuItem } from 'primeng/api';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { FooterComponent } from '../../components/footer/footer.component';

@Component({
  selector: 'app-pagina-alimenti',
  standalone: true,
  imports: [
    BreadcrumbBasicDemo,
    HeaderComponent,
    DynamicFormComponent,
    UtilityBarComponent,
    productTableComponent,
    FooterComponent,
  ],
  templateUrl: './pagina-alimenti.component.html',
  styleUrl: './pagina-alimenti.component.css',
})
export class PaginaAlimentiComponent {
  // Proprietà per le voci del breadcrumb
  items: MenuItem[] = [{ label: 'Alimenti Frigo', routerLink: '/' }];

  // Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  // Proprietà per le label selezionabili
  labels = [
    { title: 'Frigo', showVerticalLine: true },
    { title: 'Dispensa', showVerticalLine: false },
  ];

  // Proprietà per il titolo della tabella
  tableTitle: string = 'Informazioni sugli Alimenti';

  // Proprietà per i buttons relativi alla tabella
  buttons = [{ label: 'Genera Ricetta' }, { label: 'Genera Lista' }, { label: 'Add aliments' }];

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

  onLabelChange(newLabel: string) {
    this.items = [
      { label: newLabel === 'Frigo' ? 'Alimenti Frigo' : 'Alimenti Dispensa', routerLink: '/' },
    ];
  }
}
