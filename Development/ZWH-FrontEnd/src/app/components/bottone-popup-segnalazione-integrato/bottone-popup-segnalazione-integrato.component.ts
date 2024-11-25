import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { navigationBtnComponent } from '../navigationBtn/navigationBtn.component'; // Importa CommonModule

@Component({
  selector: 'app-bottone-popup-segnalazione-integrato',
  standalone: true,
  imports: [CommonModule, navigationBtnComponent],
  templateUrl: './bottone-popup-segnalazione-integrato.component.html',
  styleUrl: './bottone-popup-segnalazione-integrato.component.css',
})
export class PopupFormSegnalazioneComponent {
  visible: boolean = false; // Determina se il popup è visibile

  // Metodo per aprire il popup
  openPopup() {
    this.visible = true; // Mostra il popup
  }

  // Metodo per chiudere il popup
  closePopup() {
    this.visible = false; // Nasconde il popup
  }
}
