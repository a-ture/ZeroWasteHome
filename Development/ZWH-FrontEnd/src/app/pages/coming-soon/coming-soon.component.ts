import { Component } from '@angular/core';
import { FooterComponent } from '../../components/footer/footer.component';
import { HeaderComponent } from '../../components/header/header.component';
import { RouterLink } from '@angular/router';
import { navigationBtnComponent } from '../../components/navigationBtn/navigationBtn.component';

@Component({
  selector: 'app-coming-soon',
  standalone: true,
  imports: [FooterComponent, HeaderComponent, RouterLink, navigationBtnComponent],
  templateUrl: './coming-soon.component.html',
  styleUrl: './coming-soon.component.css',
})
export class ComingSoonComponent {}
