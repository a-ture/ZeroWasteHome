import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { ShoppingListComponent } from '../../components/shopping-list/shopping-list.component';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { userBtnComponent } from '../../components/userBtn/userBtn.component';
import { AccordionBasicDemo } from '../../components/accordion-faq/accordion-faq.component';
import { MenuItem } from 'primeng/api';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';

@Component({
  selector: 'app-genera-lista',
  standalone: true,
  imports: [
    HeaderComponent,
    ShoppingListComponent,
    FooterComponent,
    BreadcrumbBasicDemo,
    userBtnComponent,
    AccordionBasicDemo,
    UtilityBarComponent,
  ],
  templateUrl: './genera-lista.component.html',
  styleUrl: './genera-lista.component.css',
})
export class GeneraListaComponent {
  items: MenuItem[] = [
    { label: 'Alimenti', routerLink: '/' },
    { label: 'Genera Lista', routerLink: '/' },
  ];
  //Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  tableTitle: string = 'Lista della Spesa';
  buttons = [{ label: 'Modifica Lista' }];
}
