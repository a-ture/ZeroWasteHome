import { Component, Input } from '@angular/core';

@Component({
  selector: 'btn2',
  standalone: true,
  imports: [],
  templateUrl: './UserBtn.component.html',
  styleUrl: './UserBtn.component.css',
})
export class UserBtnComponent {
  @Input() label: string = '';
}
