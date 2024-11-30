import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoRicettaComponent } from './inserimento-ricetta.component';

describe('InserimentoRicettaComponent', () => {
  let component: InserimentoRicettaComponent;
  let fixture: ComponentFixture<InserimentoRicettaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InserimentoRicettaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InserimentoRicettaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
