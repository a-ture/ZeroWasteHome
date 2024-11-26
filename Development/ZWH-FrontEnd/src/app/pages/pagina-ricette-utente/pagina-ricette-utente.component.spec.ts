import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaRicetteUtenteComponent } from './pagina-ricette-utente.component';

describe('PaginaRicetteUtenteComponent', () => {
  let component: PaginaRicetteUtenteComponent;
  let fixture: ComponentFixture<PaginaRicetteUtenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaRicetteUtenteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PaginaRicetteUtenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
