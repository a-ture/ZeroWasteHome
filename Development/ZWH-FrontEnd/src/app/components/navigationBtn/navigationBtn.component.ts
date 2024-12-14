import { Component, Input, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'navigationBtn',
  standalone: true,
  imports: [],
  templateUrl: './navigationBtn.component.html',
  styleUrl: './navigationBtn.component.css',
})
export class navigationBtnComponent {
  @Input() label: string = '';
  @Output() event: EventEmitter<void> = new EventEmitter();

  // Metodo per emettere l'evento
  onClick(): void {
    this.event.emit();
  }
}