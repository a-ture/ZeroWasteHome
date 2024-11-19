import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CardHomePageComponent } from './card-home-page.component';

describe('CardHomePageComponent', () => {
  let component: CardHomePageComponent;
  let fixture: ComponentFixture<CardHomePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CardHomePageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CardHomePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
