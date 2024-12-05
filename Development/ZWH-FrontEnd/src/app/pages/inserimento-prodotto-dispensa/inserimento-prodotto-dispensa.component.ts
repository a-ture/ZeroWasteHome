import { Component } from '@angular/core';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-inserimento-prodotto-dispensa',
  standalone: true,
  imports: [DynamicFormComponent, FooterComponent, HeaderComponent, BreadcrumbComponent],
  templateUrl: './inserimento-prodotto-dispensa.component.html',
  styleUrl: './inserimento-prodotto-dispensa.component.css',
})
export class InserimentoProdottoDispensaComponent {
  productFormFields = [
    { label: 'Nome Prodotto', type: 'text', value: '' },
    { label: 'Quantità', type: 'number' },
    { label: 'Scadenza', type: 'date' },
    { label: 'Valori nutrizionali', type: 'textarea', value: '' },
    { label: 'Note', type: 'textarea', value: '' },
  ];

  page: string = 'Inserisci Prodotto Dispensa';
}
