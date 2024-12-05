import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-inserimento-prodotto-frigo',
  standalone: true,
  imports: [HeaderComponent, DynamicFormComponent, FooterComponent, BreadcrumbComponent],
  templateUrl: './inserimento-prodotto-frigo.component.html',
  styleUrl: './inserimento-prodotto-frigo.component.css',
})
export class InserimentoProdottoFrigoComponent {
  productFormFields = [
    { label: 'Nome Prodotto', type: 'text', value: '' },
    { label: 'Quantità', type: 'number' },
    { label: 'Scadenza', type: 'date' },
    { label: 'Valori nutrizionali', type: 'textarea', value: '' },
    { label: 'Note', type: 'textarea', value: '' },
  ];

  page: string = 'Inserisci Prodotto Frigo';
}
