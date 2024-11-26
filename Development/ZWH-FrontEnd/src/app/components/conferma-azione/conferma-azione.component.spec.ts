import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfermaAzioneComponent } from './conferma-azione.component';

describe('ConfermaAzioneComponent', () => {
  let component: ConfermaAzioneComponent;
  let fixture: ComponentFixture<ConfermaAzioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfermaAzioneComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ConfermaAzioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
