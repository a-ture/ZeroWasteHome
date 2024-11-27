import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SegnalazioneRicettaComponent } from './segnalazione-ricetta.component';

describe('SegnalazioneRicettaComponent', () => {
  let component: SegnalazioneRicettaComponent;
  let fixture: ComponentFixture<SegnalazioneRicettaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SegnalazioneRicettaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SegnalazioneRicettaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
