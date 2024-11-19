import { Component } from '@angular/core';
import { GalleriaModule } from 'primeng/galleria';

@Component({
  selector: 'app-carosello',
  standalone: true,
  imports: [GalleriaModule],
  templateUrl: './carosello.component.html',
  styleUrls: ['./carosello.component.css'],
})
export class CaroselloComponent {
  images = [
    {
      itemImageSrc: 'assets/img/imgCarosello/Immagine1.jpg',
      alt: 'Description 1',
      title: 'Title 1',
    },
    {
      itemImageSrc: 'assets/img/imgCarosello/Immagine2.jpg',
      alt: 'Description 2',
      title: 'Title 2',
    },
    {
      itemImageSrc: 'assets/img/imgCarosello/Immagine3.jpg',
      alt: 'Description 3',
      title: 'Title 3',
    },
    {
      itemImageSrc: 'assets/img/imgCarosello/Immagine4.jpg',
      alt: 'Description 4',
      title: 'Title 4',
    },
    {
      itemImageSrc: 'assets/img/imgCarosello/Immagine5.jpg',
      alt: 'Description 5',
      title: 'Title 5',
    },
  ];

  responsiveOptions = [
    {
      breakpoint: '1024px',
      numVisible: 5,
    },
    {
      breakpoint: '768px',
      numVisible: 3,
    },
    {
      breakpoint: '560px',
      numVisible: 1,
    },
  ];
}
