import { Component } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { ShoppingListComponent } from '../../components/shopping-list/shopping-list.component';
import { UtilityBarComponent } from '../../components/utility-bar/utility-bar.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { AreaUtenteComponent } from '../../components/area-utente/area-utente.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-area-utente-form',
  standalone: true,
  imports: [
    FooterComponent,
    HeaderComponent,
    ShoppingListComponent,
    UtilityBarComponent,
    DynamicFormComponent,
    AreaUtenteComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './area-utente-form.component.html',
  styleUrl: './area-utente-form.component.css',
})
export class AreaUtenteFormComponent {}
