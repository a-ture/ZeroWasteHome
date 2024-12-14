import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { LoginGestoreService } from '../../services/servizio-login-gestore/login-gestore.service';

@Component({
  selector: 'app-login-form-gestore',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login-form-gestore.component.html',
  styleUrls: ['./login-form-gestore.component.css'],
})
export class LoginFormGestoreComponent {
  loginForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private loginGestoreService: LoginGestoreService,
    private messageService: MessageService,
  ) {
    // Inizializza il form con campi e validazioni
    this.loginForm = this.fb.group({
      email: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  // Metodo per gestire il submit del form
  onLogin() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value; // Estrai i valori
      this.loginGestoreService.login(email, password).subscribe({
        next: () => {
          console.log('Login effettuato con successo');
        },
        error: error => {
          this.messageService.add({
            severity: 'error',
            summary: 'Errore di login',
            detail: error.message || 'Credenziali non valide',
            life: 4000, // tempo in millisecondi
          });
        },
      });
    } else {
      console.log('Il form non è valido');
    }
  }
}
