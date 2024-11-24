import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupFormSegnalazioneComponent } from './bottone-popup-segnalazione-integrato.component';

describe('BottonePopupSegnalazioneIntegrato', () => {
  let component: PopupFormSegnalazioneComponent;
  let fixture: ComponentFixture<PopupFormSegnalazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PopupFormSegnalazioneComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PopupFormSegnalazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
