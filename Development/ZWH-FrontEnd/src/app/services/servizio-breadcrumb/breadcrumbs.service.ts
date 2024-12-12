import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';
import { MenuItem } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class BreadcrumbsService {
  private _items = new BehaviorSubject<MenuItem[]>([]);
  items = this._items.asObservable();

  // Definizione della gerarchia predefinita dei breadcrumb
  private breadcrumbHierarchy: { [key: string]: string[] } = {
    '/alimenti': ['Alimenti'],
    '/alimenti/genera-lista': ['Alimenti', 'Genera Lista'],
    '/alimenti/inserimento-ricetta': ['Alimenti', 'Inserisci Ricetta'],
    '/alimenti/inserimento-prodotto-frigo': ['Alimenti', 'Prodotto Frigo'],
    '/alimenti/inserimento-prodotto-dispensa': ['Alimenti', 'Prodotto Dispensa'],
    '/area-personale': ['Area Personale'],
    '/area-personale/profilo': ['Area Personale', 'Profilo'],
    '/area-personale/le-mie-ricette': ['Area Personale', 'Le Mie Ricette'],
    '/area-personale/le-mie-ricette/inserimento-ricetta': [
      'Area Personale',
      'Le Mie Ricette',
      'Inserimento Ricetta',
    ],
    '/area-personale/assistenza': ['Area Personale', 'Assistenza'],
    '/assistenza': ['Assistenza'],
    '/community/inserimento-segnalazione': ['Community', 'Inserisci Segnalazione'],
    '/home/le-mie-ricette': ['Le Mie Ricette'],
  };

  constructor(
    private router: Router,
    private activatedRoute: ActivatedRoute,
  ) {
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
      const breadcrumbs = this.buildBreadcrumb(this.router.url);
      this._items.next(breadcrumbs); // Aggiorna i breadcrumb
    });
  }

  private buildBreadcrumb(url: string): MenuItem[] {
    const breadcrumbs: MenuItem[] = [];
    const hierarchy = this.breadcrumbHierarchy[url];

    if (hierarchy) {
      let accumulatedUrl = '';
      hierarchy.forEach(label => {
        accumulatedUrl = this.getPathForLabel(label, accumulatedUrl);
        breadcrumbs.push({ label, routerLink: accumulatedUrl });
      });
    }

    console.log('Generated Breadcrumbs:', breadcrumbs); // Debug
    return breadcrumbs;
  }

  private getPathForLabel(label: string, currentPath: string): string {
    if (label === 'Alimenti') {
      return '/alimenti';
    } else if (label === 'Genera Lista') {
      return '/alimenti/genera-lista';
    } else if (label === 'Inserisci Ricetta') {
      return '/alimenti/inserimento-ricetta';
    } else if (label === 'Prodotto Frigo') {
      return '/alimenti/inserimento-prodotto-frigo';
    } else if (label === 'Prodotto Dispensa') {
      return '/alimenti/inserimento-prodotto-dispensa';
    } else if (label === 'Area Personale') {
      return '/area-personale';
    } else if (label === 'Le Mie Ricette') {
      return '/area-personale/le-mie-ricette';
    }

    // Se nessuna corrispondenza è trovata, restituisci il percorso attuale
    return currentPath;
  }
}
