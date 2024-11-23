import { Component } from '@angular/core';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-inserimento-prodotto-dispensa',
  standalone: true,
  imports: [BreadcrumbBasicDemo, DynamicFormComponent, FooterComponent, HeaderComponent],
  templateUrl: './inserimento-prodotto-dispensa.component.html',
  styleUrl: './inserimento-prodotto-dispensa.component.css',
})
export class InserimentoProdottoDispensaComponent {
  // Proprietà per le voci del breadcrumb
  items: MenuItem[] = [
    { label: 'Alimenti Dispensa', routerLink: '/' },
    { label: 'Inserimento Prodotto Dispensa', routerLink: '/' },
  ];

  // Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  productFormFields = [
    { label: 'Nome Prodotto', type: 'text', value: '' },
    { label: 'Quantità', type: 'number' },
    { label: 'Scadenza', type: 'date' },
    { label: 'Valori nutrizionali', type: 'textarea', value: '' },
    { label: 'Note', type: 'textarea', value: '' },
  ];

  page: string = 'Inserisci Prodotto Dispensa';
}
