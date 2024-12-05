import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { AccordionBasicDemo } from '../../components/accordion-faq/accordion-faq.component';
import { userBtnComponent } from '../../components/userBtn/userBtn.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';

@Component({
  selector: 'app-assistenza',
  standalone: true,
  imports: [
    HeaderComponent,
    FooterComponent,
    AccordionBasicDemo,
    userBtnComponent,
    BreadcrumbComponent,
  ],
  templateUrl: './assistenza.component.html',
  styleUrl: './assistenza.component.css',
})
export class AssistenzaComponent {
  label: string = 'Altro, inviaci più dettagli!';
}
