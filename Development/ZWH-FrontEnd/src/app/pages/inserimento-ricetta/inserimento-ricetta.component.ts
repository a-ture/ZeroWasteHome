import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-inserimento-ricetta',
  standalone: true,
  imports: [HeaderComponent, DynamicFormComponent, FooterComponent, BreadcrumbComponent],
  templateUrl: './inserimento-ricetta.component.html',
  styleUrl: './inserimento-ricetta.component.css',
})
export class InserimentoRicettaComponent {
  productFormFields = [
    { label: 'Nome Ricetta', type: 'text', value: '' },
    { label: 'Quantità per persona', type: 'number' },
    { label: 'Categoria ricetta', type: 'text' },
    { label: 'Ingredienti', type: 'textarea', value: '' },
    { label: 'Passaggi', type: 'textarea', value: '' },
  ];

  page: string = 'Informazioni Ricetta';
}
