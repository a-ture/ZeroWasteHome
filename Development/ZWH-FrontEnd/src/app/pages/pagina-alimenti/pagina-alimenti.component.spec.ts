import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PaginaAlimentiComponent } from './pagina-alimenti.component';

describe('PaginaAlimentiComponent', () => {
  let component: PaginaAlimentiComponent;
  let fixture: ComponentFixture<PaginaAlimentiComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PaginaAlimentiComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PaginaAlimentiComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
