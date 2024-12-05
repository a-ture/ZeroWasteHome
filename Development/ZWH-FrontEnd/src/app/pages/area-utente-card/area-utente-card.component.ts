import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CardsAreaUtenteComponent } from '../../components/cards-area-utente/cards-area-utente.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-area-utente-card',
  standalone: true,
  imports: [HeaderComponent, FooterComponent, CardsAreaUtenteComponent, BreadcrumbComponent],
  templateUrl: './area-utente-card.component.html',
  styleUrl: './area-utente-card.component.css',
})
export class AreaUtenteCardComponent {}
