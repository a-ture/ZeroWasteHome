import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { ShoppingListComponent } from '../../components/shopping-list/shopping-list.component';
import { userBtnComponent } from '../../components/userBtn/userBtn.component';
import { AccordionBasicDemo } from '../../components/accordion-faq/accordion-faq.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-genera-lista',
  standalone: true,
  imports: [
    HeaderComponent,
    ShoppingListComponent,
    FooterComponent,
    userBtnComponent,
    AccordionBasicDemo,
    UtilityBarComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './genera-lista.component.html',
  styleUrl: './genera-lista.component.css',
})
export class GeneraListaComponent {
  tableTitle: string = 'Lista della Spesa';
  buttons = [{ label: 'Modifica Lista' }];
}
