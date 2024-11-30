import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaUtenteFormComponent } from './area-utente-form.component';

describe('AreaUtenteFormComponent', () => {
  let component: AreaUtenteFormComponent;
  let fixture: ComponentFixture<AreaUtenteFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaUtenteFormComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AreaUtenteFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
