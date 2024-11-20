import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-informazioni-nnl',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './informazioni-nnl.component.html',
  styleUrls: ['./informazioni-nnl.component.css'],
})
export class InformazioniNnlComponent {
  sections = [
    {
      title: 'Chi siamo:',
      description: 'Conoscerci sarà un piacere!',
      imageUrl: './assets/img/imgInfoNNL/ChiSiamo.jpg',
      altText: 'Teamwork image',
      invertion: { flexDirection: 'row' },
    },
    {
      title: 'Cosa è ZeroWaste Home:',
      description:
        'La tua guida smart per una gestione alimentare sostenibile, riducendo gli sprechi con facilità e creando una community che valorizza ogni ingrediente.',
      imageUrl: 'assets/img/imgInfoNNL/ZeroWaste.jpg',
      altText: 'Zero Waste image',
      invertion: { flexDirection: 'row-reverse' },
    },
    {
      title: 'I nostri obiettivi:',
      description: 'Ogni pasto conta, ogni spreco si evita!',
      imageUrl: 'assets/img/imgInfoNNL/Obiettivi.jpg',
      altText: 'Goals image',
      invertion: { flexDirection: 'row' },
    },
  ];
}
