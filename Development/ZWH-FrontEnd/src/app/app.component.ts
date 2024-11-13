import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { ExampleCardsListComponent } from './components/example-cards-list/example-cards-list.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ExampleCardsListComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'ZWH-FrontEnd';
}
