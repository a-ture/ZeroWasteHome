import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestoreReportPagamentoComponent } from './gestore-report-pagamento.component';

describe('GestoreReportPagamentoComponent', () => {
  let component: GestoreReportPagamentoComponent;
  let fixture: ComponentFixture<GestoreReportPagamentoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestoreReportPagamentoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestoreReportPagamentoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
