import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DonazioniComponent } from './donazioni.component';

describe('DonazioniComponent', () => {
  let component: DonazioniComponent;
  let fixture: ComponentFixture<DonazioniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [DonazioniComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(DonazioniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
