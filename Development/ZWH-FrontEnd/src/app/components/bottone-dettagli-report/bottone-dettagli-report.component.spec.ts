import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottoneDettagliReportComponent } from './bottone-dettagli-report.component';

describe('BottoneDettagliReportComponent', () => {
  let component: BottoneDettagliReportComponent;
  let fixture: ComponentFixture<BottoneDettagliReportComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BottoneDettagliReportComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BottoneDettagliReportComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
