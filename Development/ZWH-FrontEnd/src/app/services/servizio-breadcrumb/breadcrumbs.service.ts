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
    '/area-personale/le-mie-ricette/visualizza-ricetta/:id': [
      'Area Personale',
      'Le Mie Ricette',
      'Visualizza Ricetta',
    ],
    '/area-personale/assistenza': ['Area Personale', 'Assistenza'],
    '/assistenza': ['Assistenza'],
    '/community/inserimento-segnalazione': ['Community', 'Inserisci Segnalazione'],
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

    // Controlla se l'URL corrisponde a un percorso con parametro dinamico
    const hierarchy = this.getBreadcrumbForUrl(url);

    if (hierarchy) {
      let accumulatedUrl = '';
      hierarchy.forEach(label => {
        accumulatedUrl = this.getPathForLabel(label, accumulatedUrl, url); // Passa anche l'URL per il controllo dinamico
        breadcrumbs.push({ label, routerLink: accumulatedUrl });
      });
    }

    console.log('Generated Breadcrumbs:', breadcrumbs); // Debug
    return breadcrumbs;
  }

  private getBreadcrumbForUrl(url: string): string[] | undefined {
    // Cerca nel percorso di breadcrumb per l'URL
    for (let path in this.breadcrumbHierarchy) {
      const regex = new RegExp(`^${path.replace(':id', '\\d+$')}$`);
      if (regex.test(url)) {
        return this.breadcrumbHierarchy[path];
      }
    }
    return undefined; // Se non trova corrispondenze
  }

  private getPathForLabel(label: string, currentPath: string, url: string): string {
    // Gestisci i vari casi come prima, con l'aggiunta della logica per :id
    if (label === 'Visualizza Ricetta') {
      // Gestisci la sostituzione del parametro :id con l'ID reale
      const idMatch = url.match(/\/area-personale\/le-mie-ricette\/visualizza-ricetta\/(\d+)/);
      if (idMatch) {
        // Sostituisci :id con l'ID estratto dalla URL
        return `/area-personale/le-mie-ricette/visualizza-ricetta/${idMatch[1]}`;
      }
    }

    // Se nessun caso specifico, continua con il comportamento normale
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
    } else if (label === 'Inserimento Ricetta') {
      return '/area-personale/le-mie-ricette/inserimento-ricetta';
    } else if (label === 'Profilo') {
      return '/area-personale/profilo';
    } else if (label === 'Assistenza') {
      if (this.router.url == '/area-personale/assistenza') {
        return '/area-personale/assistenza';
      } else {
        return '/assistenza';
      }
    }

    // Se nessuna corrispondenza è trovata, restituisci il percorso attuale
    return currentPath;
  }
}
