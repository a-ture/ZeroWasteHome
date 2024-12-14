import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SegnalazioneRicettaGestoreComponent } from './segnalazione-ricetta-gestore.component';

describe('SegnalazioneRicettaGestoreComponent', () => {
  let component: SegnalazioneRicettaGestoreComponent;
  let fixture: ComponentFixture<SegnalazioneRicettaGestoreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SegnalazioneRicettaGestoreComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(SegnalazioneRicettaGestoreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
