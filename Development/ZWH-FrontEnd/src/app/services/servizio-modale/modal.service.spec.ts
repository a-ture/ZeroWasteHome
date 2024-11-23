import { TestBed } from '@angular/core/testing';

import { InserisciProdottoModalService } from './modal.service';

describe('InserisciProdottoModalService', () => {
  let service: InserisciProdottoModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InserisciProdottoModalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
