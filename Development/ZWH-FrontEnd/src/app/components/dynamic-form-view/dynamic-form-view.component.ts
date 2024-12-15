import { Component, Input, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule, NgForOf, NgIf } from '@angular/common';

@Component({
  selector: 'app-dynamic-form-view',
  standalone: true,
  imports: [FormsModule, NgIf, NgForOf],
  templateUrl: './dynamic-form-view.component.html',
  styleUrl: './dynamic-form-view.component.css',
})
export class DynamicFormViewComponent implements OnInit {
  // Ogni oggetto di questo array rappresenta un input del form,
  // nome campo, tipo, valore default, opzioni fra cui scegliere
  @Input() fields: { label: string; type: string; value?: string; options?: string[] }[] = [];

  @Input() page!: string;

  // Stato per gestire l'immagine caricata
  @Input() imagePreview!: string;

  // per i campi di input e textarea
  focusedField: string | null = null;

  onFocus(label: string): void {
    this.focusedField = label;
  }

  onBlur(label: string): void {
    // Rimuovi il focus solo se il campo è vuoto
    if (!this.fields.find(field => field.label === label)?.value) {
      this.focusedField = null;
    }
  }

  ngOnInit() {
    if (!this.imagePreview) {
      this.imagePreview = 'https://placehold.jp/3d4070/ffffff/200x200.png';
    }
  }
}
