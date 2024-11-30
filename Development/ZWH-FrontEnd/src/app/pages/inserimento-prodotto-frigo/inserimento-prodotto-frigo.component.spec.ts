import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoProdottoFrigoComponent } from './inserimento-prodotto-frigo.component';

describe('InserimentoProdottoFrigoComponent', () => {
  let component: InserimentoProdottoFrigoComponent;
  let fixture: ComponentFixture<InserimentoProdottoFrigoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InserimentoProdottoFrigoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InserimentoProdottoFrigoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
