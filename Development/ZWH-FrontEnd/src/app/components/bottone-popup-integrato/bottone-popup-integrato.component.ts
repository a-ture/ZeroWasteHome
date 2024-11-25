import { Component, Input, ViewChild } from '@angular/core';
import { userBtnComponent } from '../userBtn/userBtn.component';
import { PopupSempliceComponent } from '../popup-semplice/popup-semplice.component';
import { FooterComponent } from '../footer/footer.component';

@Component({
  selector: 'app-bottone-popup-integrato',
  standalone: true,
  imports: [userBtnComponent, FooterComponent, PopupSempliceComponent],
  templateUrl: './bottone-popup-integrato.component.html',
  styleUrl: './bottone-popup-integrato.component.css',
})
export class BottonePerPopupSempliceComponent {
  @Input() messaggio: string = 'Messaggio non inserito';
  @ViewChild(PopupSempliceComponent) popup!: PopupSempliceComponent; // Riferimento al popup

  showPopup(messaggio: string) {
    this.popup.openPopup(messaggio);
  }
}
