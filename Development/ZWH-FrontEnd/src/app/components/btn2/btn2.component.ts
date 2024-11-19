import { Component, Input } from '@angular/core';

@Component({
  selector: 'btn2',
  standalone: true,
  imports: [],
  templateUrl: './btn2.component.html',
  styleUrl: './btn2.component.css',
})
export class Btn2Component {
  @Input() label: string = '';
}
