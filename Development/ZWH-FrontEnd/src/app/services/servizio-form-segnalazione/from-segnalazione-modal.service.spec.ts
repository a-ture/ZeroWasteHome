import { TestBed } from '@angular/core/testing';

import { FormSegnalazioneModalService } from './from-segnalazione-modal.service';

describe('FormSegnalazione', () => {
  let service: FormSegnalazioneModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FormSegnalazioneModalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
