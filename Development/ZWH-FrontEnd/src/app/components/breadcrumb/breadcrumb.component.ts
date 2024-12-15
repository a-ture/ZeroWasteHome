import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { BreadcrumbsService } from '../../services/servizio-breadcrumb/breadcrumbs.service';
import { BreadcrumbModule } from 'primeng/breadcrumb';

@Component({
  selector: 'app-breadcrumb',
  standalone: true,
  imports: [BreadcrumbModule],
  templateUrl: './breadcrumb.component.html',
  styleUrls: ['./breadcrumb.component.css'],
})
export class BreadcrumbComponent implements OnInit {
  home: MenuItem = { icon: 'pi pi-home', label: 'Home', routerLink: '/home' };
  items: MenuItem[] = [];

  constructor(private service: BreadcrumbsService) {}

  ngOnInit(): void {
    this.service.items.subscribe((arr: MenuItem[]) => {
      this.items = [...arr]; // Forza Angular a rilevare il cambiamento
    });
  }
}
