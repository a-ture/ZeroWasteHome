import { Routes } from '@angular/router';
import { HomeUtenteNonLoggatoComponent } from './pages/home-utente-non-loggato/home-utente-non-loggato.component';
import { AreaUtenteCardComponent } from './pages/area-utente-card/area-utente-card.component';
import { AreaUtenteFormComponent } from './pages/area-utente-form/area-utente-form.component';
import { AssistenzaComponent } from './pages/assistenza/assistenza.component';
import { GeneraListaComponent } from './pages/genera-lista/genera-lista.component';
import { HomeComponent } from './pages/home/home.component';
import { InserimentoProdottoDispensaComponent } from './pages/inserimento-prodotto-dispensa/inserimento-prodotto-dispensa.component';
import { InserimentoProdottoFrigoComponent } from './pages/inserimento-prodotto-frigo/inserimento-prodotto-frigo.component';
import { InserimentoRicettaComponent } from './pages/inserimento-ricetta/inserimento-ricetta.component';
import { InserimentoSegnalazioneComponent } from './pages/inserimento-segnalazione/inserimento-segnalazione.component';
import { PaginaAlimentiComponent } from './pages/pagina-alimenti/pagina-alimenti.component';
import { PaginaRicetteUtenteComponent } from './pages/pagina-ricette-utente/pagina-ricette-utente.component';
import { SegnalazioneRicettaComponent } from './pages/segnalazione-ricetta/segnalazione-ricetta.component';

export const routes: Routes = [
  { path: '', component: HomeUtenteNonLoggatoComponent }, // Home page

  { path: 'home', component: HomeComponent },
  { path: 'alimenti', component: PaginaAlimentiComponent, data: { breadcrumb: 'Alimenti' } },
  {
    path: 'alimenti/genera-lista',
    component: GeneraListaComponent,
    data: { breadcrumb: 'Genera Lista' },
  },
  {
    path: 'alimenti/inserimento-ricetta',
    component: InserimentoRicettaComponent,
    data: { breadcrumb: 'Inserisci Ricetta' },
  },
  {
    path: 'area-personale',
    component: AreaUtenteCardComponent,
    data: { breadcrumb: 'Area Personale' },
  },
  {
    path: 'area-personale/profilo',
    component: AreaUtenteFormComponent,
    data: { breadcrumb: 'Profilo' },
  },
  { path: 'assistenza', component: AssistenzaComponent, data: { breadcrumb: 'Assistenza' } },
  {
    path: 'alimenti/inserimento-prodotto-dispensa',
    component: InserimentoProdottoDispensaComponent,
    data: { breadcrumb: 'Prodotto Dispensa' },
  },
  {
    path: 'alimenti/inserimento-prodotto-frigo',
    component: InserimentoProdottoFrigoComponent,
    data: { breadcrumb: 'Prodotto Frigo' },
  },
  {
    path: 'inserimento/segnalazione',
    component: InserimentoSegnalazioneComponent,
    data: { breadcrumb: 'Inserisci Segnalazione' },
  },
  {
    path: 'le-mie-ricette',
    component: PaginaRicetteUtenteComponent,
    data: { breadcrumb: 'Le Mie Ricette' },
  },
  {
    path: 'segnalazione-ricetta',
    component: SegnalazioneRicettaComponent,
    data: { breadcrumb: 'Segnalazione Ricetta' },
  },
  { path: '**', redirectTo: 'home' }, // Redirect per percorsi non trovati
];
