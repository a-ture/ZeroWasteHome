import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CaroselloComponent } from '../../components/carosello/carosello.component';
import { CardHomePageComponent } from '../../components/card-home-page/card-home-page.component';
import { CardsAreaUtenteComponent } from '../../components/cards-area-utente/cards-area-utente.component';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    CaroselloComponent,
    CardHomePageComponent,
    CardsAreaUtenteComponent,
  ],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css',
})
export class HomeComponent {}
