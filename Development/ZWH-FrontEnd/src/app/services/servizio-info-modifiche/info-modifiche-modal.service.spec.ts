import { TestBed } from '@angular/core/testing';

import { ConfermaAzioneModalService } from './info-modifiche-modal.service';

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
