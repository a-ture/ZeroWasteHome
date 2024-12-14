import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestoreReportPaymentTableComponent } from './gestore-report-payment-table.component';

describe('GestoreReportPaymentTableComponent', () => {
  let component: GestoreReportPaymentTableComponent;
  let fixture: ComponentFixture<GestoreReportPaymentTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestoreReportPaymentTableComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GestoreReportPaymentTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
