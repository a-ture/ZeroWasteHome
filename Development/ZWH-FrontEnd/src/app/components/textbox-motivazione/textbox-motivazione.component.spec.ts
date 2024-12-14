import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TextboxMotivazioneComponent } from './textbox-motivazione.component';

describe('TextboxMotivazioneComponent', () => {
  let component: TextboxMotivazioneComponent;
  let fixture: ComponentFixture<TextboxMotivazioneComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TextboxMotivazioneComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(TextboxMotivazioneComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
