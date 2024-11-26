import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneraListaComponent } from './genera-lista.component';

describe('GeneraListaComponent', () => {
  let component: GeneraListaComponent;
  let fixture: ComponentFixture<GeneraListaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GeneraListaComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(GeneraListaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
