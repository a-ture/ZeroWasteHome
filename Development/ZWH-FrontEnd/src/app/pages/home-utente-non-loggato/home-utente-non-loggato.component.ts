import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { CaroselloComponent } from '../../components/carosello/carosello.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { CardHomePageComponent } from '../../components/card-home-page/card-home-page.component';
import { HomeComponent } from '../home/home.component';
import { InformazioniNnlComponent } from '../../components/informazioni-nnl/informazioni-nnl.component';

@Component({
  selector: 'app-home-utente-non-loggato',
  standalone: true,
  imports: [
    HeaderComponent,
    CaroselloComponent,
    FooterComponent,
    CardHomePageComponent,
    HomeComponent,
    InformazioniNnlComponent,
  ],
  templateUrl: './home-utente-non-loggato.component.html',
  styleUrl: './home-utente-non-loggato.component.css',
})
export class HomeUtenteNonLoggatoComponent {}
