import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ExampleCardsListComponent } from './example-cards-list.component';

describe('ExampleCardsListComponent', () => {
  let component: ExampleCardsListComponent;
  let fixture: ComponentFixture<ExampleCardsListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ExampleCardsListComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(ExampleCardsListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
