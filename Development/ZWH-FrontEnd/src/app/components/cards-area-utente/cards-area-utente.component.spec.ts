import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardsAreaUtenteComponent } from './cards-area-utente.component';

describe('CardsAreaUtenteComponent', () => {
  let component: CardsAreaUtenteComponent;
  let fixture: ComponentFixture<CardsAreaUtenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardsAreaUtenteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CardsAreaUtenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
