import { Component } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-segnalazione-ricetta',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    UtilityBarComponent,
    productTableComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './segnalazione-ricetta.component.html',
  styleUrl: './segnalazione-ricetta.component.css',
})
export class SegnalazioneRicettaComponent {
  //Proprietà per l'utility bar
  tableTitle: string = 'Le Ricette Segnalate da ME';
  buttons = [{ label: 'Filtra Segnalazioni' }];
}
