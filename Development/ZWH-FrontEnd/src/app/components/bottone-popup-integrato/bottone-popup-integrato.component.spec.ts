import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottonePerPopupSempliceComponent } from './bottone-popup-integrato.component';

describe('BottonePopupIntegratoComponent', () => {
  let component: BottonePerPopupSempliceComponent;
  let fixture: ComponentFixture<BottonePerPopupSempliceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BottonePerPopupSempliceComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BottonePerPopupSempliceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
