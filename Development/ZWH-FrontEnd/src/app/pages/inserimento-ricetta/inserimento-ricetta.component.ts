import { Component } from '@angular/core';
import { HeaderComponent } from '../../components/header/header.component';
import { DynamicFormComponent } from '../../components/dynamic-form/dynamic-form.component';
import { FooterComponent } from '../../components/footer/footer.component';
import { BreadcrumbComponent } from '../../components/breadcrumb/breadcrumb.component';
import { GestioneRicetteService } from '../../services/gestione-ricette/gestione-ricette.service';
import { Ricetta } from '../../services/gestione-ricette/ricetta';

@Component({
  selector: 'app-inserimento-ricetta',
  standalone: true,
  imports: [HeaderComponent, DynamicFormComponent, FooterComponent, BreadcrumbComponent],
  templateUrl: './inserimento-ricetta.component.html',
  styleUrl: './inserimento-ricetta.component.css',
})
export class InserimentoRicettaComponent {
  productFormFields = [
    { label: 'Nome Ricetta', type: 'text', value: '' },
    { label: 'Quantità per persona', type: 'number' },
    { label: 'Categoria ricetta', type: 'select',  value: '', options: ["ANTIPASTO", "PRIMO", "SECONDO", "CONTORNO", "DOLCE"]},
    { label: 'Ingredienti', type: 'textarea', value: '' },
    { label: 'Passaggi', type: 'textarea', value: '' },
  ];

  page: string = 'Informazioni Ricetta';

  constructor(private gestioneRicetteService: GestioneRicetteService) {}

  onSubmit(formData: any): void {
    const fileInput = document.querySelector('input[type="file"]') as HTMLInputElement;
    let imageFileName = '';

    if (fileInput && fileInput.files && fileInput.files[0]) {
      imageFileName = fileInput.files[0].name; // Ottieni il nome del file caricato
    }

    const ricetta: Ricetta = {
      nome: formData['Nome Ricetta'],
      quantitaPerPersona: parseInt(formData['Quantità per persona'], 10),
      categoria: formData['Categoria ricetta'] as Ricetta['categoria'], // Cast necessario per TypeScript
      ingredienti: formData['Ingredienti']
        ? formData['Ingredienti'].split(',').map((ingrediente: string) => ingrediente.trim())
        : [],
      istruzioni: formData['Passaggi'],
      img: imageFileName, // Usa il nome del file
    };

    console.log('Payload inviato:', ricetta);

    this.gestioneRicetteService.aggiungiRicetta(ricetta).subscribe({
      next: response => {
        console.log('Ricetta aggiunta con successo:', response);
        alert('Ricetta aggiunta!');
      },
      error: error => {
        console.error("Errore durante l'aggiunta della ricetta:", error);
        alert(error);
      },
    });
  }
}
