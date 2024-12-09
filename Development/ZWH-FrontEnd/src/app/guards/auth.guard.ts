import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LoginModalService } from '../services/servizio-login/login-modal.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private modalService: LoginModalService,
    private router: Router,
  ) {}
  canActivate(): boolean {
    const token = localStorage.getItem('token'); // Recupera il token dal localStorage

    if (token) {
      // Token presente, l'utente è autenticato
      return true;
    } else {
      // Token assente, apri la modale di login
      this.modalService.openModal();
      this.router.navigate(['/']);
      return false; // Blocco l'accesso alla rotta
    }
  }
}
