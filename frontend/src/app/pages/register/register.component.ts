import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';
import {
  MatFormFieldModule
} from '@angular/material/form-field';
import {
  MatInputModule
} from '@angular/material/input';
import {
  MatCardModule
} from '@angular/material/card';
import {
  MatButtonModule
} from '@angular/material/button';

@Component({
  selector: 'app-register',
  standalone: true,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  imports: [
    ReactiveFormsModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatCardModule,
    MatButtonModule,
    MatSnackBarModule
  ]
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private snackBar: MatSnackBar
  ) {
    this.registerForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  onSubmit() {
    if (this.registerForm.valid) {
      this.http.post('/api/register', this.registerForm.value).subscribe({
        next: () => this.showSuccess(),
        error: () => this.showError()
      });
    }
  }

  showSuccess() {
    this.snackBar.open('Registration successful!', 'Close', { duration: 3000 });
  }

  showError() {
    this.snackBar.open('Registration failed. Please try again.', 'Close', { duration: 3000 });
  }
}