import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Btn2Component } from './btn2.component';

describe('Btn2Component', () => {
  let component: Btn2Component;
  let fixture: ComponentFixture<Btn2Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Btn2Component],
    }).compileComponents();

    fixture = TestBed.createComponent(Btn2Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
