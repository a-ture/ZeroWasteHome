import { Component } from '@angular/core';
import { LoginFormGestoreComponent } from '../../components/login-form-gestore/login-form-gestore.component';

@Component({
  selector: 'app-login-gestore',
  standalone: true,
  imports: [LoginFormGestoreComponent],
  templateUrl: './login-gestore.component.html',
  styleUrl: './login-gestore.component.css',
})
export class LoginGestoreComponent {}
