import {
  ChangeDetectorRef,
  Component,
  input,
  Input,
  OnInit,
  SimpleChanges,
  ViewChild,
} from '@angular/core';
import { MenubarModule } from 'primeng/menubar';
import { BadgeModule } from 'primeng/badge';
import { AvatarModule } from 'primeng/avatar';
import { InputTextModule } from 'primeng/inputtext';
import { CommonModule } from '@angular/common';
import { FormSegnalazioneModalService } from '../../services/servizio-form-segnalazione/from-segnalazione-modal.service';
import { LoginModalService } from '../../services/servizio-login/login-modal.service';
import { LoginComponent } from '../login/login.component';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [
    MenubarModule,
    BadgeModule,
    AvatarModule,
    InputTextModule,
    CommonModule,
    LoginComponent,
    navigationBtnComponent,
  ],
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
  menuVisible: boolean = false;
  isSmallScreen: boolean = false;
  isOpen: boolean = false; // Stato per la classe "open"

  constructor(
    private cdr: ChangeDetectorRef,
    private modalService: LoginModalService,
  ) {}

  // Funzione per aprire/chiudere il menu utente
  toggleUserMenu() {
    if (!this.isSmallScreen) {
      console.log('Menu cliccato, visibilità menu prima:', this.userMenuVisible);
      this.userMenuVisible = !this.userMenuVisible;
      console.log('Visibilità menu dopo:', this.userMenuVisible);
    }
  }
  //per l'animazinìone dell'amburgher
  toggleOpen() {
    this.isOpen = !this.isOpen; // Cambia lo stato della classe "open"
  }
  toggleMenu(): void {
    this.menuVisible = !this.menuVisible;
  }

  setActiveLink(link: string) {
    this.activeLink = link;

    //forzo di continuare solo dopo aver rilevato il cambiamento di selezione
    this.cdr.detectChanges();

    if (!this.isSmallScreen) {
      this.circleDisplay = 'block';
    }

    //forzo di continuare solo dopo aver rilevato il cambiamento di selezione
    this.cdr.detectChanges();

    this.updateCirclePosition();
  }

  updateCirclePosition() {
    if (!this.isSmallScreen) {
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
  }

  // Metodo per aprire la modale
  openModal(): void {
    this.modalService.openModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su true
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
    // Chiama la funzione per verificare la dimensione dello schermo all'inizio
    this.checkScreenSize();

    // Aggiungi un listener per monitorare i cambiamenti delle dimensioni dello schermo
    window.matchMedia('(max-width: 1000px)').addEventListener('change', e => {
      this.isSmallScreen = e.matches;
      this.onScreenSizeChange();
    });
  }

  checkScreenSize() {
    // Controlla la dimensione dello schermo
    this.isSmallScreen = window.innerWidth <= 1000;
    this.onScreenSizeChange();
  }

  onScreenSizeChange() {
    if (this.isSmallScreen) {
      console.log('Lo schermo è più piccolo di 760px');
      this.userMenuVisible = true;
      this.circleTransform = `translateX(-100%) translateY(0%)`;

      // Esegui la tua funzione o logica qui
    } else {
      console.log('Lo schermo è più grande di 1000px');
      this.updateCirclePosition();
      window.addEventListener('resize', () => this.updateCirclePosition());
      window.addEventListener('onload', () => this.setActiveLink(this.activeLink));
    }
  }
}
