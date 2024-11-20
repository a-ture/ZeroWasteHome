import { Component, Input } from '@angular/core';

@Component({
  selector: 'userBtn',
  standalone: true,
  imports: [],
  templateUrl: './userBtn.component.html',
  styleUrl: './userBtn.component.css',
})
export class UserBtnComponent {
  @Input() label: string = '';
}
