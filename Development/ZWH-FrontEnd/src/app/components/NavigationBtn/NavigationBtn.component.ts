import { Component, Input } from '@angular/core';

@Component({
  selector: 'navigationBtn',
  standalone: true,
  imports: [],
  templateUrl: './navigationBtn.component.html',
  styleUrl: './navigationBtn.component.css',
})
export class navigationBtnComponent {
  @Input() label: string = '';
}
