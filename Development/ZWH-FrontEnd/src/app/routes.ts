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
import { AuthGuard } from './guards/auth.guard';
import { LoginGestoreComponent } from './pages/login-gestore/login-gestore.component';
import { SegnalazioneRicettaGestoreComponent } from './pages/segnalazione-ricetta-gestore/segnalazione-ricetta-gestore/segnalazione-ricetta-gestore.component';

export const routes: Routes = [
  { path: '', component: HomeUtenteNonLoggatoComponent }, // Home page

  { path: 'home', component: HomeComponent, canActivate: [AuthGuard] },
  {
    path: 'alimenti',
    component: PaginaAlimentiComponent,
    data: { breadcrumb: 'Alimenti' },
    canActivate: [AuthGuard],
  },
  {
    path: 'alimenti/genera-lista',
    component: GeneraListaComponent,
    data: { breadcrumb: 'Genera Lista' },
    canActivate: [AuthGuard],
  },
  {
    path: 'alimenti/inserimento-ricetta',
    component: InserimentoRicettaComponent,
    data: { breadcrumb: 'Inserisci Ricetta' },
    canActivate: [AuthGuard],
  },
  {
    path: 'area-personale',
    component: AreaUtenteCardComponent,
    data: { breadcrumb: 'Area Personale' },
    canActivate: [AuthGuard],
  },
  {
    path: 'area-personale/profilo',
    component: AreaUtenteFormComponent,
    data: { breadcrumb: 'Profilo' },
    canActivate: [AuthGuard],
  },
  {
    path: 'assistenza',
    component: AssistenzaComponent,
    data: { breadcrumb: 'Assistenza' },
  },
  {
    path: 'area-personale/assistenza',
    component: AssistenzaComponent,
    data: { breadcrumb: 'Assistenza' },
  },
  {
    path: 'alimenti/inserimento-prodotto-dispensa',
    component: InserimentoProdottoDispensaComponent,
    data: { breadcrumb: 'Prodotto Dispensa' },
    canActivate: [AuthGuard],
  },
  {
    path: 'alimenti/inserimento-prodotto-frigo',
    component: InserimentoProdottoFrigoComponent,
    data: { breadcrumb: 'Prodotto Frigo' },
    canActivate: [AuthGuard],
  },
  {
    path: 'community/inserimento-segnalazione',
    component: InserimentoSegnalazioneComponent,
    data: { breadcrumb: 'Inserisci Segnalazione' },
    canActivate: [AuthGuard],
  },
  {
    path: 'home/le-mie-ricette',
    component: PaginaRicetteUtenteComponent,
    data: { breadcrumb: 'Le Mie Ricette' },
    canActivate: [AuthGuard],
  },
  {
    path: 'home/le-mie-ricette/inserimento-ricetta',
    component: InserimentoRicettaComponent,
    data: { breadcrumb: 'Le Mie Ricette' },
    canActivate: [AuthGuard],
  },
  {
    path: 'area-personale/le-mie-ricette',
    component: PaginaRicetteUtenteComponent,
    data: { breadcrumb: 'Le Mie Ricette' },
    canActivate: [AuthGuard],
  },
  {
    path: 'segnalazione-ricetta',
    component: SegnalazioneRicettaComponent,
    data: { breadcrumb: 'Segnalazione Ricetta' },
    canActivate: [AuthGuard],
  },
  {
    path: 'pagina-gestore',
    component: SegnalazioneRicettaGestoreComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'area-personale/le-mie-ricette/inserimento-ricetta',
    component: InserimentoRicettaComponent,
    data: { breadcrumb: 'Inserimento Ricetta' },
    canActivate: [AuthGuard],
  },
  {
    path: 'admin',
    component: LoginGestoreComponent,
  },
  { path: '**', redirectTo: 'home' }, // Redirect per percorsi non trovati
];
