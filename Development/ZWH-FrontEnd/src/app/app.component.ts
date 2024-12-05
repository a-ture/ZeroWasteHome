import { Component } from '@angular/core';
import { NavigationEnd, Router, RouterOutlet } from '@angular/router';
import { ExampleCardsListComponent } from './components/example-cards-list/example-cards-list.component';
import { ConfermaCodiceABarreComponent } from './components/conferma-codice-a-barre/conferma-codice-a-barre.component';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ExampleCardsListComponent, ConfermaCodiceABarreComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'ZWH-FrontEnd';
  constructor(private router: Router) {
    this.router.events.pipe(filter(event => event instanceof NavigationEnd)).subscribe(() => {
      window.scrollTo(0, 0); // Scorri sempre in alto
    });
  }
}
