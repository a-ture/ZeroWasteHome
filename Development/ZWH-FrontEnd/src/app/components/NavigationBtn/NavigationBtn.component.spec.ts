import { ComponentFixture, TestBed } from '@angular/core/testing';

import { navigationBtnComponent } from './navigationBtn.component';

describe('navigationBtnComponent', () => {
  let component: navigationBtnComponent;
  let fixture: ComponentFixture<navigationBtnComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [navigationBtnComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(navigationBtnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
