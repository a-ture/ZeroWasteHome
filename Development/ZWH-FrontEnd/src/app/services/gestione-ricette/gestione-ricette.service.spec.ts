import { TestBed } from '@angular/core/testing';

import { GestioneRicetteService } from './gestione-ricette.service';

describe('GestioneRicetteService', () => {
  let service: GestioneRicetteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GestioneRicetteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
