import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { BreadcrumbModule } from 'primeng/breadcrumb';

@Component({
  selector: 'breadcrumb-basic-demo',
  templateUrl: './breadcrumb.component.html',
  standalone: true,
  imports: [BreadcrumbModule], // Importa BreadcrumbModule per il componente breadcrumb
})
export class BreadcrumbBasicDemo implements OnInit {
  items: MenuItem[] | undefined; // Elementi del breadcrumb
  home: MenuItem | undefined; // Icona e link per la home

  ngOnInit() {
    // Inizializza le voci del breadcrumb
    this.items = [
      { label: 'Pagina1' },
      { label: 'Pagina2' },
      { label: 'Pagina3' },
      { label: 'Pagina4' },
      { label: 'Pagina5' },
    ];

    // Configura il pulsante Home
    this.home = { icon: 'pi pi-home', routerLink: '/' };
  }
}
