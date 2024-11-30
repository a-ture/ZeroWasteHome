import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottoneInserisciAlimentoComponent } from './bottone-inserisci-alimento.component';

describe('BottoneInserisciAlimentoComponent', () => {
  let component: BottoneInserisciAlimentoComponent;
  let fixture: ComponentFixture<BottoneInserisciAlimentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BottoneInserisciAlimentoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BottoneInserisciAlimentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
