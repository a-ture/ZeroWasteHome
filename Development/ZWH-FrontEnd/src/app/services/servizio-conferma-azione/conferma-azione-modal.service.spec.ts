import { TestBed } from '@angular/core/testing';

import { ConfermaAzioneModalService } from './conferma-azione-modal.service';

describe('ConfermaAzioneModalService', () => {
  let service: ConfermaAzioneModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConfermaAzioneModalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
