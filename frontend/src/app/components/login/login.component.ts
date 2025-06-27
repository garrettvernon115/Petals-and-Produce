import { Component } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatSnackBarModule, MatSnackBar, MatSnackBarConfig } from '@angular/material/snack-bar';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';


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
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username = '';
  password = '';

  constructor(private snackBar: MatSnackBar,     private http: HttpClient,     private router: Router) {}


  onSubmit() {
    const loginData = {
      username: this.username,
      password: this.password
    };

    this.http.post('/api/auth/login', loginData, { responseType: 'text' })
      .subscribe({
        next: (token) => {
          localStorage.setItem('authToken', token);
          this.snackBar.open('Login successful!', 'Close', { duration: 3000 });
          this.router.navigate(['/home']); //update later to admin for admins and products for others
        },
        error: (err) => {
          this.snackBar.open('Login failed. Please try again.', 'Close', { duration: 3000 });
        }
      });
  }
}
