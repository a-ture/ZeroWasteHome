import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DettagliReportComponent } from './dettagli-report.component';

describe('DettagliReportComponent', () => {
  let component: DettagliReportComponent;
  let fixture: ComponentFixture<DettagliReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DettagliReportComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DettagliReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
