import { TestBed } from '@angular/core/testing';

import { InserisciProdottoFrigoService } from './inserisci-prodotto-frigo.service';

describe('InserisciProdottoModalService', () => {
  let service: InserisciProdottoFrigoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InserisciProdottoFrigoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
