import { Component } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { RouterLink } from '@angular/router';
import { navigationBtnComponent } from '../../components/navigationBtn/navigationBtn.component';

@Component({
  selector: 'app-donazioni',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, RouterLink, navigationBtnComponent],
  templateUrl: './donazioni.component.html',
  styleUrl: './donazioni.component.css',
})
export class DonazioniComponent {}
