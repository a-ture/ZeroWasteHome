import { Component } from '@angular/core';
import { AccordionBasicDemo } from '../../components/accordion-faq/accordion-faq.component';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { userBtnComponent } from '../../components/userBtn/userBtn.component';
import { MenuItem } from 'primeng/api';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { productTableComponent } from '../../components/product-table/product-table.component';

@Component({
  selector: 'app-segnalazione-ricetta',
  standalone: true,
  imports: [
    BreadcrumbBasicDemo,
    FooterComponent,
    HeaderComponent,
    UtilityBarComponent,
    productTableComponent,
  ],
  templateUrl: './segnalazione-ricetta.component.html',
  styleUrl: './segnalazione-ricetta.component.css',
})
export class SegnalazioneRicettaComponent {
  //Proprietà per le voci del breadcrumb
  items: MenuItem[] = [
    { label: 'Ricette Community', routerLink: '/' },
    { label: 'Segnalazioni Ricette', routerLink: '/' },
  ];

  //Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  //Proprietà per l'utility bar
  tableTitle: string = 'Le Ricette Segnalate da ME';
  buttons = [{ label: 'Filtra Segnalazioni' }];
}
