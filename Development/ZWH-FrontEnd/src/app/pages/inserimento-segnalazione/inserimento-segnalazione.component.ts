import { Component } from '@angular/core';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-inserimento-segnalazione',
  standalone: true,
  imports: [DynamicFormComponent, FooterComponent, HeaderComponent, BreadcrumbComponent],
  templateUrl: './inserimento-segnalazione.component.html',
  styleUrl: './inserimento-segnalazione.component.css',
})
export class InserimentoSegnalazioneComponent {
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
