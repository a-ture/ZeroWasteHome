import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { MenuItem } from 'primeng/api';
@Component({
  selector: 'app-inserimento-ricetta',
  standalone: true,
  imports: [HeaderComponent, BreadcrumbBasicDemo, DynamicFormComponent, FooterComponent],
  templateUrl: './inserimento-ricetta.component.html',
  styleUrl: './inserimento-ricetta.component.css',
})
export class InserimentoRicettaComponent {
  // Proprietà per le voci del breadcrumb
  items: MenuItem[] = [
    { label: 'Area Personale', routerLink: '/' },
    { label: 'Le Mie Ricette', routerLink: '/' },
    { label: 'Inserimento Ricetta', routerLink: '/' },
  ];

  // Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  productFormFields = [
    { label: 'Nome Ricetta', type: 'text', value: '' },
    { label: 'Quantità per persona', type: 'number' },
    { label: 'Categoria ricetta', type: 'text' },
    { label: 'Ingredienti', type: 'textarea', value: '' },
    { label: 'Passaggi', type: 'textarea', value: '' },
  ];

  page: string = 'Informazioni Ricetta';
}
