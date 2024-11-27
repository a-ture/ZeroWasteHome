import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InserimentoSegnalazioneComponent } from './inserimento-segnalazione.component';

describe('InserimentoSegnalazioneComponent', () => {
  let component: InserimentoSegnalazioneComponent;
  let fixture: ComponentFixture<InserimentoSegnalazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InserimentoSegnalazioneComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(InserimentoSegnalazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
