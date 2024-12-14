import { TestBed } from '@angular/core/testing';

import { ServizioDettgliReportService } from './servizio-dettgli-report.service';

describe('ServizioDettgliReportService', () => {
  let service: ServizioDettgliReportService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServizioDettgliReportService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
