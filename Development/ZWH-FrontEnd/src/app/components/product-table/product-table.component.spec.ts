import { ComponentFixture, TestBed } from '@angular/core/testing';

import { productTableComponent } from './product-table.component';

describe('ProductTableComponent', () => {
  let component: productTableComponent;
  let fixture: ComponentFixture<productTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [productTableComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(productTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
