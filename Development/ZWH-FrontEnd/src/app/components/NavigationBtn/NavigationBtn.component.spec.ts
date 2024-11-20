import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Btn1Component } from './NavigationBtn.component';

describe('Btn1Component', () => {
  let component: Btn1Component;
  let fixture: ComponentFixture<Btn1Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Btn1Component],
    }).compileComponents();

    fixture = TestBed.createComponent(Btn1Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
