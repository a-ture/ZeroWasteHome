import { TestBed } from '@angular/core/testing';

import { LoginGestoreService } from './login-gestore.service';

describe('LoginGestoreService', () => {
  let service: LoginGestoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LoginGestoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
