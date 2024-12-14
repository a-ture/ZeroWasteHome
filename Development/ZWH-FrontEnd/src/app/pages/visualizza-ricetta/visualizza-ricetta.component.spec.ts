import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VisualizzaRicettaComponent } from './visualizza-ricetta.component';

describe('VisualizzaRicettaComponent', () => {
  let component: VisualizzaRicettaComponent;
  let fixture: ComponentFixture<VisualizzaRicettaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [VisualizzaRicettaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VisualizzaRicettaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
