import { TestBed } from '@angular/core/testing';

import { TextboxMotivazioneModalService } from './textbox-motivazione-blocco.service';

describe('TextboxMotivazioneBloccoService', () => {
  let service: TextboxMotivazioneModalService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TextboxMotivazioneModalService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
