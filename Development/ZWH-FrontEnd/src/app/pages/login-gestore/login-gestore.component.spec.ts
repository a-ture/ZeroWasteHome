import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginGestoreComponent } from './login-gestore.component';

describe('LoginGestoreComponent', () => {
  let component: LoginGestoreComponent;
  let fixture: ComponentFixture<LoginGestoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginGestoreComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginGestoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
