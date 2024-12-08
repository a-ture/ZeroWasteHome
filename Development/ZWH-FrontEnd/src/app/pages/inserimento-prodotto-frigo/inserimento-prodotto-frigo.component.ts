import { Component, OnInit } from '@angular/core';
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
export class InserimentoProdottoFrigoComponent implements OnInit {
  productFormFields = [
    { label: 'Nome Prodotto', type: 'text', value: '' },
    { label: 'Quantità', type: 'number' },
    { label: 'Scadenza', type: 'date' },
    { label: 'Valori nutrizionali', type: 'textarea', value: '' },
    { label: 'Note', type: 'textarea', value: '' },
  ];

  page: string = 'Inserisci Prodotto Frigo';

  imageURL!: string;

  ngOnInit(): void {
    // Recupera i dati dalla navigazione
    const productDetails = history.state.productDetails;

    // Se i dati esistono, popolano i campi del form
    if (productDetails) {
      this.productFormFields[0].value = productDetails.productName || '';
      this.productFormFields[1].value = '1';
      this.productFormFields[3].value = productDetails.nutrition || '';
      this.productFormFields[4].value = productDetails.notes || '';
      this.imageURL = productDetails.imageUrl;
    }
  }
}
