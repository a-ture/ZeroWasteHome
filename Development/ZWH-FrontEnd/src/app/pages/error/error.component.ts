import { Component } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { RouterLink } from '@angular/router';
import { navigationBtnComponent } from '../../components/navigationBtn/navigationBtn.component';

@Component({
  selector: 'app-error',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, RouterLink, navigationBtnComponent],
  templateUrl: './error.component.html',
  styleUrl: './error.component.css',
})
export class ErrorComponent {}
