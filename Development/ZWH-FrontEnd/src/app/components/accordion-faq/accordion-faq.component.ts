import { Component } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';

@Component({
  selector: 'accordion-basic-demo',
  templateUrl: './accordion-faq.component.html',
  styleUrl: './accordion-faq.component.css',
  standalone: true,
  imports: [AccordionModule],
})
export class AccordionBasicDemo {}
