import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { BreadcrumbBasicDemo } from '../../components/breadcrumb/breadcrumb.component';
import { MenuItem } from 'primeng/api';
import { AccordionBasicDemo } from '../../components/accordion-faq/accordion-faq.component';
import { userBtnComponent } from '../../components/userBtn/userBtn.component';

@Component({
  selector: 'app-assistenza',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    BreadcrumbBasicDemo,
    AccordionBasicDemo,
    userBtnComponent,
  ],
  templateUrl: './assistenza.component.html',
  styleUrl: './assistenza.component.css',
})
export class AssistenzaComponent {
  //Proprietà per le voci del breadcrumb
  items: MenuItem[] = [
    { label: 'Area Personale', routerLink: '/' },
    { label: 'Assistenza', routerLink: '/' },
  ];

  //Proprietà per il pulsante Home
  home: MenuItem = { label: 'Home', routerLink: '/' };

  label: string = 'Altro, inviaci più dettagli!';
}
