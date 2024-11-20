import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgForOf, NgIf } from '@angular/common';
import { UserBtnComponent } from '../UserBtn/UserBtn.component';
import { FloatLabelModule } from 'primeng/floatlabel';
import { InputTextModule } from 'primeng/inputtext';
import { Btn1Component } from '../NavigationBtn/NavigationBtn.component';

@Component({
  selector: 'dynamic-form',
  templateUrl: './dynamic-form.component.html',
  styleUrls: ['./dynamic-form.component.css'],
  standalone: true,
  imports: [
    FormsModule,
    NgForOf,
    NgIf,
    UserBtnComponent,
    FloatLabelModule,
    InputTextModule,
    Btn1Component,
  ],
})
export class DynamicFormComponent {
  // Ogni oggetto di questo array rappresenta un input del form,
  //                nome campo,      tipo,        valore default
  @Input() fields: { label: string; type: string; value?: string }[] = [];

  @Input() page!: string;

  // Evento per emettere i dati raccolti dal form
  @Output() formSubmit = new EventEmitter<any>();

  // Stato per gestire l'immagine caricata
  imagePreview: string | null = 'assets/img/dynamic-form/form-image-placeholder.svg';

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

  // Metodo per gestire il caricamento dell'immagine
  onImageUpload(event: Event): void {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
      this.imagePreview = URL.createObjectURL(file); // Genera un URL temporaneo per l'anteprima
    }
  }

  // Metodo per inviare i dati del form
  submitForm(formValues: any) {
    this.formSubmit.emit({ ...formValues, image: this.imagePreview });
  }

  // Metodo per simulare il click del campo input nascosto
  triggerFileInput(fileInput: HTMLInputElement) {
    fileInput.click();
  }
}
