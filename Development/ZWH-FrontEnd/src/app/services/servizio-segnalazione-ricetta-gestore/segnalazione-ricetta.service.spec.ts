import { TestBed } from '@angular/core/testing';

import { SegnalazioneRicettaService } from './segnalazione-ricetta.service';

describe('SegnalazioneRicettaService', () => {
  let service: SegnalazioneRicettaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SegnalazioneRicettaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
