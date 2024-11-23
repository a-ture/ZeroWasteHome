import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottoneConfermaAzioneComponent } from './bottone-conferma-azione.component';

describe('BottoneConfermaAzioneComponent', () => {
  let component: BottoneConfermaAzioneComponent;
  let fixture: ComponentFixture<BottoneConfermaAzioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BottoneConfermaAzioneComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(BottoneConfermaAzioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
