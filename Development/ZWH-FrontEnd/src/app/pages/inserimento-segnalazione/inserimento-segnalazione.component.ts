import { Component } from '@angular/core';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-inserimento-segnalazione',
  standalone: true,
  imports: [BreadcrumbBasicDemo, DynamicFormComponent, FooterComponent, HeaderComponent],
  templateUrl: './inserimento-segnalazione.component.html',
  styleUrl: './inserimento-segnalazione.component.css',
})
export class InserimentoSegnalazioneComponent {
  // Proprietà per le voci del breadcrumb
  items: MenuItem[] = [
    { label: 'Ricette Community', routerLink: '/' },
    { label: 'Inserimento Segnalazione', routerLink: '/' },
  ];

  // Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  productFormFields = [
    { label: 'Identificativo Ricetta', type: 'text', value: '' },
    { label: 'Nome Ricetta', type: 'text' },
    { label: 'Stato Segnalazione', type: 'text' },
    { label: 'Tipo di Segnalazione', type: 'textarea', value: '' },
    { label: 'Descrizione Segnalazione', type: 'textarea', value: '' },
  ];

  page: string = 'Inserimento della Segnalazione';
  submitText = 'Invia Segnalazione';
}
