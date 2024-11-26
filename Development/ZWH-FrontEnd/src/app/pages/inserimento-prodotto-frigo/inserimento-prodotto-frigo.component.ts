import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-inserimento-prodotto-frigo',
  standalone: true,
  imports: [HeaderComponent, BreadcrumbBasicDemo, DynamicFormComponent, FooterComponent],
  templateUrl: './inserimento-prodotto-frigo.component.html',
  styleUrl: './inserimento-prodotto-frigo.component.css',
})
export class InserimentoProdottoFrigoComponent {
  // Proprietà per le voci del breadcrumb
  items: MenuItem[] = [
    { label: 'Alimenti Frigo', routerLink: '/' },
    { label: 'Inserimento Prodotto Frigo', routerLink: '/' },
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

  page: string = 'Inserisci Prodotto Frigo';
}
