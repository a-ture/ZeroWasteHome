import { Component, Input, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { BreadcrumbModule } from 'primeng/breadcrumb';

@Component({
  selector: 'breadcrumb-basic-demo',
  templateUrl: './breadcrumb.component.html',
  standalone: true,
  imports: [BreadcrumbModule], // Importa BreadcrumbModule per il componente breadcrumb
})
export class BreadcrumbBasicDemo implements OnInit {
  @Input() items: MenuItem[] | undefined; // Elementi del breadcrumb
  @Input() home: MenuItem | undefined; // Icona e link per la home

  ngOnInit() {
    // Inizializza le voci del breadcrumb
    if (!this.items) {
      this.items = [
        { label: 'Pagina1', routerLink: '/pagina1' },
        { label: 'Pagina2', routerLink: '/pagina2' },
        { label: 'Pagina3', routerLink: '/pagina3' },
      ];
    }

    // Configura il pulsante Home
    if (!this.home) {
      this.home = { icon: 'pi pi-home', routerLink: '/' };
    }
  }
}

export class BreadcrumbComponent {}
