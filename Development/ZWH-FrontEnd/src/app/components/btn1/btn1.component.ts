import { Component, Input } from '@angular/core';

@Component({
  selector: 'btn1',
  standalone: true,
  imports: [],
  templateUrl: './btn1.component.html',
  styleUrl: './btn1.component.css',
})
export class Btn1Component {
  @Input() label: string = '';
}
