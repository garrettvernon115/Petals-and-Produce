import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';

import { MatSnackBarModule, MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';



@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatInputModule,
    MatButtonModule,
    MatSnackBarModule,
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private snackBar: MatSnackBar) {}


onSubmit() {
  const config: MatSnackBarConfig = {
    duration: 3000,
    verticalPosition: 'top' as const,
    horizontalPosition: 'center' as const
  };

  if (this.username === 'test' && this.password === 'password') {
    this.snackBar.open('Login successful!', 'Close', config);
  } else {
    this.snackBar.open('Login failed. Please try again.', 'Close', config);
  }
}
}
