import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AccordionFaqComponent } from './accordion-faq.component';

describe('AccordionFaqComponent', () => {
  let component: AccordionFaqComponent;
  let fixture: ComponentFixture<AccordionFaqComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AccordionFaqComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AccordionFaqComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
