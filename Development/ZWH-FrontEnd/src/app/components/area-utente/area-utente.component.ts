import { Component, Input } from '@angular/core';
import { userBtnComponent } from '../userBtn/userBtn.component';

@Component({
  selector: 'app-area-utente',
  standalone: true,
  templateUrl: './area-utente.component.html',
  imports: [userBtnComponent],
  styleUrls: ['./area-utente.component.css'],
})
export class AreaUtenteComponent {
  /*
  Questo componente permette di implementare direttamente il componente grafico "area utente"
  e puo essere utilizzato sia per la visualizzazione che come form per la modifica dei dati personali.
  prevede diversi parametri di input, di seguito riporto un esempio pronto all'uso per essere visualizzato.

  esempio:
  <app-area-utente
    username="username"
    email="emailgenerica@gmail.com"
    nome="pietro"
    cognome="rossi"
    dataNascita="12-9-2000"
    fabbisogno="2000"
    livelloAttivita="moderato"
  ></app-area-utente>
  */
  @Input() username: string = 'JohnDoe';
  @Input() email: string = 'john.doe@example.com';
  @Input() nome: string = 'John';
  @Input() cognome: string = 'Doe';
  @Input() dataNascita: string = '12/8/2000';
  @Input() livelloAttivita: string = 'medio';
  @Input() fabbisogno: string = '2000';

  isEditing = false;

  onEdit() {
    this.isEditing = true;
  }

  onSave() {
    this.isEditing = false;
    console.log('Dati aggiornati:', {
      username: this.username,
      email: this.email,
      nome: this.nome,
      cognome: this.cognome,
      age: this.dataNascita,
      livelloAttivita: this.livelloAttivita,
      fabbisogno: this.fabbisogno,
    });
    // Qui puoi aggiungere la logica per salvare i dati
  }
}
