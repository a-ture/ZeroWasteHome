import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoProdottoDispensaComponent } from './inserimento-prodotto-dispensa.component';

describe('InserimentoProdottoDispensaComponent', () => {
  let component: InserimentoProdottoDispensaComponent;
  let fixture: ComponentFixture<InserimentoProdottoDispensaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InserimentoProdottoDispensaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InserimentoProdottoDispensaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
