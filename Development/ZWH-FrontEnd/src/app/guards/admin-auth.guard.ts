import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { LoginModalService } from '../services/servizio-login/login-modal.service';

@Injectable({
  providedIn: 'root',
})
export class AdminAuthGuard implements CanActivate {
  constructor(private router: Router) {}
  canActivate(): boolean {
    const token = localStorage.getItem('admin_token'); // Recupera il token dal localStorage

    if (token) {
      // Token presente, l'utente è autenticato
      return true;
    } else {
      this.router.navigate(['/admin']);
      return false; // Blocco l'accesso alla rotta
    }
  }
}
