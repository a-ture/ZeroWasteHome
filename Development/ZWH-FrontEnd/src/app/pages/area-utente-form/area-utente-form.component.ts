import { Component } from '@angular/core';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { ShoppingListComponent } from '../../components/shopping-list/shopping-list.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { MenuItem } from 'primeng/api';
import { AreaUtenteComponent } from '../../components/area-utente/area-utente.component';

@Component({
  selector: 'app-area-utente-form',
  standalone: true,
  imports: [
    BreadcrumbBasicDemo,
    FooterComponent,
    HeaderComponent,
    ShoppingListComponent,
    UtilityBarComponent,
    DynamicFormComponent,
    AreaUtenteComponent,
  ],
  templateUrl: './area-utente-form.component.html',
  styleUrl: './area-utente-form.component.css',
})
export class AreaUtenteFormComponent {
  items: MenuItem[] = [
    { label: 'Area Personale', routerLink: '/' },
    { label: 'Area Utente', routerLink: '/' },
  ];

  // Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };
}
