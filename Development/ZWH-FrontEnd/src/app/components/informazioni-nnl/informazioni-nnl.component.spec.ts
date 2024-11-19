import { ComponentFixture, TestBed } from '@angular/core/testing';
import { InformazioniNnlComponent } from './informazioni-nnl.component';

describe('InformazioniNnlComponent', () => {
  let component: InformazioniNnlComponent;
  let fixture: ComponentFixture<InformazioniNnlComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [InformazioniNnlComponent], // Importiamo il componente standalone
    }).compileComponents();

    fixture = TestBed.createComponent(InformazioniNnlComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have three sections', () => {
    expect(component.sections.length).toBe(3);
  });

  it('should display correct titles for each section', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const titles = Array.from(compiled.querySelectorAll('h2')).map(el => el.textContent?.trim());

    expect(titles).toEqual(['Chi siamo:', 'Cosa è ZeroWaste Home:', 'I nostri obiettivi:']);
  });

  it('should render images with correct alt text', () => {
    const compiled = fixture.nativeElement as HTMLElement;
    const images = Array.from(compiled.querySelectorAll('img')).map(img => img.alt);

    expect(images).toEqual(['Teamwork image', 'Zero Waste image', 'Goals image']);
  });
});
