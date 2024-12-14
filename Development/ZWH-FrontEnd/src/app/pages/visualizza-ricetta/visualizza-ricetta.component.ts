import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { productTableComponent } from '../../components/product-table/product-table.component';

@Component({
  selector: 'app-visualizza-ricetta',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    BreadcrumbComponent,
    UtilityBarComponent,
    productTableComponent,
  ],
  templateUrl: './visualizza-ricetta.component.html',
  styleUrl: './visualizza-ricetta.component.css',
})
export class VisualizzaRicettaComponent {}
