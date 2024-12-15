import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';

@Injectable({
  providedIn: 'root',
})
export class LogoutService {
  constructor(
    private router: Router,
    private messageService: MessageService,
  ) {}

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('admin_token');
    this.navigateToRoot();

    this.messageService.add({
      severity: 'info',
      summary: 'Logout effettuato',
      detail: 'Sei uscito con successo dal sistema.',
      life: 4000, // tempo in millisecondi (4 secondi)
    });
  }

  navigateToRoot(): void {
    if (this.router.url === '/') {
      // Se l'utente è già sulla root, forza il reload
      this.router.navigateByUrl('/assistenza', { skipLocationChange: true }).then(() => {
        this.router.navigate(['/']);
      });
    } else {
      // Naviga normalmente alla root
      this.router.navigate(['/']);
    }
  }
}
