import { Component } from '@angular/core';
import { AccordionModule } from 'primeng/accordion';

@Component({
  selector: 'accordion-basic-demo',
  templateUrl: './accordion-faq.component.html',
  standalone: true,
  imports: [AccordionModule],
})
export class AccordionBasicDemo {}
