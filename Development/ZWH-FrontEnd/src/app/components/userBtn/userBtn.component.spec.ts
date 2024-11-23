import { ComponentFixture, TestBed } from '@angular/core/testing';

import { userBtnComponent } from './userBtn.component';

describe('userBtnComponent', () => {
  let component: userBtnComponent;
  let fixture: ComponentFixture<userBtnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [userBtnComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(userBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
