import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AreaUtenteComponent } from './area-utente.component';

describe('AreaUtenteComponent', () => {
  let component: AreaUtenteComponent;
  let fixture: ComponentFixture<AreaUtenteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AreaUtenteComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(AreaUtenteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
