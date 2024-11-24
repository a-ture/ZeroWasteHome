import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupSempliceComponent } from './popup-semplice.component';

describe('PopupSempliceComponent', () => {
  let component: PopupSempliceComponent;
  let fixture: ComponentFixture<PopupSempliceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PopupSempliceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopupSempliceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
