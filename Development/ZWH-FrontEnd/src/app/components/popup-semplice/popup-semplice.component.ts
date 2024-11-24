import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa CommonModule

@Component({
  selector: 'app-popup-semplice',
  standalone: true,
  templateUrl: './popup-semplice.component.html',
  styleUrls: ['./popup-semplice.component.css'],
  imports: [CommonModule],
})
export class PopupSempliceComponent {
  visible: boolean = false; // Determina se il popup è visibile
  message: string = ''; // Messaggio da visualizzare nel popup

  // Metodo per aprire il popup
  openPopup(message: string) {
    this.message = message; // Imposta il messaggio
    this.visible = true; // Mostra il popup
  }

  // Metodo per chiudere il popup
  closePopup() {
    this.visible = false; // Nasconde il popup
  }
}
