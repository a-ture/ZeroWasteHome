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
