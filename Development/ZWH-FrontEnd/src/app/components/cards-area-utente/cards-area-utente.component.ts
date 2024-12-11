import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { LogoutService } from '../../services/servizio-logout/logout.service';

@Component({
  selector: 'app-cards-area-utente',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './cards-area-utente.component.html',
  styleUrl: './cards-area-utente.component.css',
})
export class CardsAreaUtenteComponent {
  constructor(private logoutService: LogoutService) {}
  logout() {
    this.logoutService.logout();
  }
}
