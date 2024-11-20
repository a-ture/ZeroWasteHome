import { ComponentFixture, TestBed } from '@angular/core/testing';

import { navigationBtn } from './navigationBtn.component';

describe('navigationBtn', () => {
  let component: navigationBtn;
  let fixture: ComponentFixture<navigationBtn>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [navigationBtn],
    }).compileComponents();

    fixture = TestBed.createComponent(navigationBtn);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
