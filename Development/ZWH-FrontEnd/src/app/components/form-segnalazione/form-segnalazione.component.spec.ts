import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormSegnalazioneComponent } from './form-segnalazione.component';

describe('FormSegnalazioneComponent', () => {
  let component: FormSegnalazioneComponent;
  let fixture: ComponentFixture<FormSegnalazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormSegnalazioneComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(FormSegnalazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
