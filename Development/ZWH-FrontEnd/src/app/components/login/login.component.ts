import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog'; // Importa il modulo per il dialog

@Component({
  standalone: true,
  imports: [
    CommonModule, // Modulo base di Angular
    ReactiveFormsModule, // Necessario per l'uso dei reactive forms
    ButtonModule, // Modulo per i pulsanti PrimeNG
    InputTextModule, // Modulo per gli input di testo PrimeNG
    DialogModule, // Modulo per i dialoghi PrimeNG
  ],
  selector: 'app-login',
  templateUrl: './login.component.html', // Template HTML del componente
  styleUrls: ['./login.component.css'], // Stili CSS associati al componente
})
export class LoginComponent {
  loginForm: FormGroup; // Reactive form per gestire il login
  showDialog: boolean = false; // Controlla la visibilità del dialogo di conferma

  constructor(private fb: FormBuilder) {
    // Inizializza il form con campi e validazioni
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]], // Campo email con validazione
      password: ['', [Validators.required, Validators.minLength(8)]], // Campo password con validazione
    });
  }

  // Metodo eseguito al submit del form
  onLogin() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value; // Estrai i valori dal form
      console.log('Dati di login:', { email, password }); // Stampa i dati di login
      this.showDialog = true; // Mostra il dialogo di conferma
    } else {
      console.error('Form non valido'); // Messaggio di errore in caso di form non valido
    }
  }

  // Metodo per gestire la navigazione alla pagina di registrazione
  onRegister() {
    console.log('Navigazione alla pagina di registrazione...');
  }
}
