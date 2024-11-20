import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CaroselloComponent } from './carosello.component';

describe('CaroselloComponent', () => {
  let component: CaroselloComponent;
  let fixture: ComponentFixture<CaroselloComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CaroselloComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CaroselloComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have a predefined list of images', () => {
    expect(component.images).toBeDefined();
    expect(component.images.length).toBe(5);
    expect(component.images[0].itemImageSrc).toBe('assets/img/imgCarosello/Immagine1.jpg');
  });

  it('should have responsive options', () => {
    expect(component.responsiveOptions).toBeDefined();
    expect(component.responsiveOptions.length).toBeGreaterThan(0);
  });
});
