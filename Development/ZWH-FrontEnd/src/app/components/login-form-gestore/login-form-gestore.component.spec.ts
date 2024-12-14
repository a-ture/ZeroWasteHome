import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoginFormGestoreComponent } from './login-form-gestore.component';

describe('LoginFormGestoreComponent', () => {
  let component: LoginFormGestoreComponent;
  let fixture: ComponentFixture<LoginFormGestoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginFormGestoreComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginFormGestoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
