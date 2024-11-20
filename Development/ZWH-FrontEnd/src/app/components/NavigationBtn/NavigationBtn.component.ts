import { Component, Input } from '@angular/core';

@Component({
  selector: 'btn1',
  standalone: true,
  imports: [],
  templateUrl: './NavigationBtn.component.html',
  styleUrl: './NavigationBtn.component.css',
})
export class NavigationBtnComponent {
  @Input() label: string = '';
}
