import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottoneFormSegnalazioneComponent } from './bottone-form-segnalazione.component';

describe('BottoneFormSegnalazioneComponent', () => {
  let component: BottoneFormSegnalazioneComponent;
  let fixture: ComponentFixture<BottoneFormSegnalazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BottoneFormSegnalazioneComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BottoneFormSegnalazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
