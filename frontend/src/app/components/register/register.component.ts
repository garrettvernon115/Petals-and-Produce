import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBar, MatSnackBarModule } from '@angular/material/snack-bar';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    HttpClientModule,
    MatCardModule,
    MatButtonModule,
    MatSnackBarModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  email = '';
  name = '';
  username = '';
  password = '';

  constructor(private http: HttpClient, private snackBar: MatSnackBar) {}

  onSubmit() {
    const body = {
      email: this.email,
      name: this.name,
      username: this.username,
      password: this.password

    };

this.http.post('/api/register', body, { responseType: 'text' }).subscribe({
  next: (response) => {
    this.snackBar.open('Successfully registered. Redirecting to login...', 'Close', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'center'
    });

    setTimeout(() => {
      window.location.href = '/login';
    }, 3000);
  },
  error: (err) => {
    const message =
      typeof err.error === 'string'
        ? err.error
        : err.error?.message || 'Registration failed.';

    this.snackBar.open(message, 'Close', {
      duration: 3000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
});
}
}