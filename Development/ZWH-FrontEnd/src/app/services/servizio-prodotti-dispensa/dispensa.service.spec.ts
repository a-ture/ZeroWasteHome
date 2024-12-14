import { TestBed } from '@angular/core/testing';

import { DispensaService } from './dispensa.service';

describe('DispensaService', () => {
  let service: DispensaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DispensaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
