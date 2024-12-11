import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { DialogModule } from 'primeng/dialog';
import { LoginModalService } from '../../services/servizio-login/login-modal.service';
import { MessageService } from 'primeng/api';

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
  isModalVisible: boolean = false;

  constructor(
    private fb: FormBuilder,
    private modalService: LoginModalService,
    private messageService: MessageService,
  ) {
    // Inizializza il form con campi e validazioni
    this.loginForm = this.fb.group({
      email: ['', Validators.required], // Campo email con validazione per campo obbligatorio
      password: ['', Validators.required], // Campo password con validazione per campo obbligatorio
    });
  }

  // Metodo eseguito al submit del form
  onLogin() {
    if (this.loginForm.valid) {
      const { email, password } = this.loginForm.value; // Estrai i valori
      this.modalService.login(email, password).subscribe({
        next: response => {
          console.log('Token:', response);
          this.modalService.closeModal();
        },
        error: error => {
          this.messageService.add({
            severity: 'error',
            summary: 'Login fallito',
            detail: error,
            life: 4000, // tempo in millisecondi (4 secondi)
          });
        },
      });
    } else {
      console.log('Il form non è valido');
    }
  }

  ngOnInit(): void {
    // Sottoscrizione all'observable del servizio per aggiornare lo stato della modale
    this.modalService.modalVisible$.subscribe(
      isVisible => (this.isModalVisible = isVisible), // Aggiorna lo stato della variabile isModalVisible
    );
  }

  // Metodo per chiudere la modale
  closeModal(): void {
    this.modalService.closeModal(); // Chiama il metodo del servizio per impostare la visibilità della modale su false
  }

  // Metodo per gestire il clic sull'overlay (area scura attorno alla modale)
  onOverlayClick(event: MouseEvent): void {
    this.closeModal(); // Chiude la modale quando si clicca sull'overlay
  }
  // Metodo per gestire la navigazione alla pagina di registrazione
  onRegister() {
    console.log('Navigazione alla pagina di registrazione...');
  }
}
