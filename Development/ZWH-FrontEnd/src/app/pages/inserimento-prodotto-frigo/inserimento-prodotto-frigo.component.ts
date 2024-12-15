import { Component, OnInit } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { InserisciProdottoFrigoService } from '../../services/servizio-inserisci-prodotto-frigo/inserisci-prodotto-frigo.service';
import {
  Categoria,
  ProdottoReq,
} from '../../services/servizio-inserisci-prodotto-frigo/prodottoRequestDTO';

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

  constructor(private gestioneProdottoService: InserisciProdottoFrigoService) {}

  onSubmit(formData: any): void {
    const productDetails = history.state.productDetails;

    // Inizializza l'array
    const categorie: Categoria[] = [];
    if (productDetails != null) {
      if (productDetails.vegan != null && productDetails.vegan == 'SI') {
        categorie.push('VEGANO');
      }
      if (productDetails.glutenFree != null && productDetails.glutenFree == 'SI') {
        categorie.push('SENZA_GLUTINE');
      }
      if (productDetails.vegetarian != null && productDetails.vegetarian == 'SI') {
        categorie.push('VEGETARIANO');
      }
    }

    let tempBarcode = '100000000000000';
    //non esistono codici a barre a 15 cifre, il back end capirà come gestirlo

    if (productDetails != null) {
      tempBarcode = productDetails.barcode;
    }

    const prodottoInFrigo: ProdottoReq = {
      codiceBarre: tempBarcode,
      nomeProdotto: formData['Nome Prodotto'],
      dataScadenza: formData['Scadenza'],
      categoria: categorie,
      img: this.imageURL || 'https://placehold.jp/200x200.png',
      quantità: Number(formData['Quantità']),
    };

    console.log('Payload inviato:', prodottoInFrigo);

    prodottoInFrigo.nomeProdotto = this.normalizeName(prodottoInFrigo.nomeProdotto);

    this.gestioneProdottoService.aggiungiProdottoInFrigo(prodottoInFrigo).subscribe({
      next: response => {
        console.log('Prodotto aggiunto con successo:', response);
        alert('Prodotto aggiunto!');
      },
      error: error => {
        console.error("Errore durante l'aggiunta del prodotto:");
        alert(error);
      },
    });
  }

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
    // Imposta l'immagine
    this.imageURL = productDetails.imageUrl;
  }

  normalizeName(nomeProdotto: string): string {
    // Rimuove le lettere speciali, sostituendole con versioni normali
    const normalized = nomeProdotto.normalize('NFD').replace(/[\u0300-\u036f]/g, '');
    // Rimuove le parentesi tonde
    const noParentheses = normalized.replace(/[()]/g, '');
    // Ritorna il nome normalizzato
    return noParentheses;
  }
}
