import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { GestioneRicetteService } from '../../services/gestione-ricette/gestione-ricette.service';
import { Ricetta } from '../../services/gestione-ricette/ricetta';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { DynamicFormViewComponent } from '../../components/dynamic-form-view/dynamic-form-view.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { HeaderComponent } from '../../components/header/header.component';

@Component({
  selector: 'app-visualizza-ricetta',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    BreadcrumbComponent,
    UtilityBarComponent,
    productTableComponent,
    DynamicFormViewComponent,
  ],
  templateUrl: './visualizza-ricetta.component.html',
  styleUrls: ['./visualizza-ricetta.component.css'],
})
export class VisualizzaRicettaComponent implements OnInit {
  productFormFields = [
    { label: 'Nome Ricetta', type: 'text', value: '' },
    { label: 'Quantità per persona', type: 'number' },
    {
      label: 'Categoria ricetta',
      type: 'select',
      value: '',
      options: ['ANTIPASTO', 'PRIMO', 'SECONDO', 'CONTORNO', 'DOLCE'],
    },
    { label: 'Ingredienti', type: 'textarea', value: '' },
    { label: 'Passaggi', type: 'textarea', value: '' },
  ];

  page: string = 'Informazioni Ricetta';

  constructor(
    private route: ActivatedRoute,
    private gestioneRicetteService: GestioneRicetteService,
  ) {}

  ngOnInit(): void {
    // Ottieni l'ID della ricetta dalla route
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.gestioneRicetteService.getRicettaById(id).subscribe({
        next: (data: Ricetta) => {
          // Setta i dati della ricetta nel form
          this.productFormFields = [
            { label: 'Nome Ricetta', type: 'text', value: data.nome },
            {
              label: 'Quantità per persona',
              type: 'number',
              value: data.quantitaPerPersona.toString(),
            },
            {
              label: 'Categoria ricetta',
              type: 'select',
              value: data.categoria,
              options: ['ANTIPASTO', 'PRIMO', 'SECONDO', 'CONTORNO', 'DOLCE'],
            },
            { label: 'Ingredienti', type: 'textarea', value: data.ingredienti.join(', ') },
            { label: 'Passaggi', type: 'textarea', value: data.istruzioni },
          ];
        },
        error: err => console.error('Errore durante il recupero della ricetta:', err),
      });
    }
  }
}
