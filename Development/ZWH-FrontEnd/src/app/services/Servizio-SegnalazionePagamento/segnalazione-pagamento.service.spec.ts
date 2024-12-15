import { TestBed } from '@angular/core/testing';

import { SegnalazionePagamentoService } from './segnalazione-pagamento.service';

describe('SegnalazionePagamentoService', () => {
  let service: SegnalazionePagamentoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SegnalazionePagamentoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
