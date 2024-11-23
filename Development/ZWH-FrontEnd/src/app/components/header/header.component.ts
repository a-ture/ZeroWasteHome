import { ChangeDetectorRef, Component, input, Input, OnInit, SimpleChanges } from '@angular/core';
import { MenubarModule } from 'primeng/menubar';
import { BadgeModule } from 'primeng/badge';
import { AvatarModule } from 'primeng/avatar';
import { InputTextModule } from 'primeng/inputtext';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MenubarModule, BadgeModule, AvatarModule, InputTextModule, CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css',
})
export class HeaderComponent implements OnInit {
  //activelink permette di selezionare la voce del menu direttamente,
  //in altre parole il menu appare con una voce già selezionata
  //i valori che accetta sono "alimeni","donazioni","community"
  // se non si sbaglia a scrivere non seleziona nulla
  @Input() activeLink: string = '';
  // se barra_ricerca vale 'si' la visualizza , altrimenti no
  @Input() barra_ricerca: string = '';
  //se loggato vale "si" il menu a tendina che appare cliccando sull'icona
  //dell'utente cambia i propri contenuti
  // 'si' -> logout
  // 'no' -> login,registrazione
  @Input() loggato: string = '';

  circleTransform: string = 'translateX(0px) translateY(0px)';
  circleDisplay: string = 'none';
  // Variabile per gestire la visibilità del menu utente
  userMenuVisible: boolean = false;

  constructor(private cdr: ChangeDetectorRef) {}

  // Funzione per aprire/chiudere il menu utente
  toggleUserMenu() {
    console.log('Menu cliccato, visibilità menu prima:', this.userMenuVisible);
    this.userMenuVisible = !this.userMenuVisible;
    console.log('Visibilità menu dopo:', this.userMenuVisible);
  }

  setActiveLink(link: string) {
    this.activeLink = link;

    //forzo di continuare solo dopo aver rilevato il cambiamento di selezione
    this.cdr.detectChanges();

    this.circleDisplay = 'block';

    //forzo di continuare solo dopo aver rilevato il cambiamento di selezione
    this.cdr.detectChanges();

    this.updateCirclePosition();
  }

  updateCirclePosition() {
    const activeElement = document.querySelector('.navbar-links li.active');
    if (activeElement) {
      const rect = activeElement.getBoundingClientRect();
      const navbarRect = document.querySelector('.navbar-links')!.getBoundingClientRect();
      const movingCircle = document.querySelector('.moving-circle')!.getBoundingClientRect();

      // Calcola l'offset al centro del link attivo
      const offset = rect.left - navbarRect.left + rect.width / 2 - movingCircle.width / 2; // 20 è la metà del diametro del cerchio
      this.circleTransform = `translateX(${offset}px) translateY(0%)`;
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['activeLink'] && changes['activeLink'].currentValue) {
      if (['alimenti', 'community', 'donazioni'].includes(this.activeLink)) {
        this.circleDisplay = 'block';
        setTimeout(() => {
          this.updateCirclePosition();
        }, 0);
      }
    }
  }

  ngOnInit() {
    this.updateCirclePosition();
    window.addEventListener('resize', () => this.updateCirclePosition());
    window.addEventListener('onload', () => this.setActiveLink(this.activeLink));
  }

  // Funzione per gestire la navigazione al login
  navigateToLogin() {
    // Inserisci la logica per navigare alla pagina di login
    console.log('Naviga alla pagina di login');
  }

  // Funzione per gestire la navigazione alla registrazione
  navigateToRegister() {
    // Inserisci la logica per navigare alla pagina di registrazione
    console.log('Naviga alla pagina di registrazione');
  }
}
