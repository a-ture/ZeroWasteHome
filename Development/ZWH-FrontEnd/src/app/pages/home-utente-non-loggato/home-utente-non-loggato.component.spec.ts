import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeUtenteNonLoggatoComponent } from './home-utente-non-loggato.component';

describe('HomeUtenteNonLoggatoComponent', () => {
  let component: HomeUtenteNonLoggatoComponent;
  let fixture: ComponentFixture<HomeUtenteNonLoggatoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HomeUtenteNonLoggatoComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(HomeUtenteNonLoggatoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
