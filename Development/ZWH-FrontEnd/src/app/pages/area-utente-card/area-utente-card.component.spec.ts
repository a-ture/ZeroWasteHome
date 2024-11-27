import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaUtenteCardComponent } from './area-utente-card.component';

describe('AreaUtenteCardComponent', () => {
  let component: AreaUtenteCardComponent;
  let fixture: ComponentFixture<AreaUtenteCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaUtenteCardComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AreaUtenteCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
