import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfermaModificheComponent } from './info-modifiche.component';

describe('ConfermaModificheComponent', () => {
  let component: ConfermaModificheComponent;
  let fixture: ComponentFixture<ConfermaModificheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ConfermaModificheComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ConfermaModificheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
