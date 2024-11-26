import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BottoneInfoModificheComponent } from './bottone-info-modifiche.component';

describe('BottoneInfoModificheComponent', () => {
  let component: BottoneInfoModificheComponent;
  let fixture: ComponentFixture<BottoneInfoModificheComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BottoneInfoModificheComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(BottoneInfoModificheComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
