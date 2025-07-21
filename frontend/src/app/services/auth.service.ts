import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient, private router: Router) {}

  login(username: string, password: string, snackBar?: MatSnackBar) {
    this.http.post<any>('/api/auth/login', { username, password }).subscribe({
      next: (res) => {
        localStorage.setItem('token', res.token); 
        localStorage.setItem('role', res.role);
        localStorage.setItem('loggedIn', 'true');

        if (snackBar) snackBar.open('Login successful!', 'Close', { duration: 3000 });

        const role = res.role?.toUpperCase();
        this.router.navigate([role === 'ADMIN' ? '/admin' : '/orders']);
      },
      error: () => {
        if (snackBar) snackBar.open('Login failed.', 'Close', { duration: 3000 });
      }
    });
  }

  isLoggedIn(): boolean {
    return localStorage.getItem('loggedIn') === 'true';
  }

  getUserRole(): string {
    return localStorage.getItem('role') || '';
  }

  getToken(): string | null {
    return localStorage.getItem('token');  
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/login']);
  }
}
