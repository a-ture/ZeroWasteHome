import { TestBed } from '@angular/core/testing';

import { FrigoService } from './frigo.service';

describe('FrigoService', () => {
  let service: FrigoService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FrigoService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
