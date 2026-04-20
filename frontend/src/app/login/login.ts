import { Component, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css'
})
export class Login {
  private readonly loginUrl = 'http://localhost:8080/api/auth/login';

  readonly username = signal('');
  readonly password = signal('');
  readonly isSubmitting = signal(false);
  readonly errorMessage = signal('');
  readonly successMessage = signal('');

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  onSubmit(): void {
    this.errorMessage.set('');
    this.successMessage.set('');

    const username = this.username().trim();
    const password = this.password();

    if (!username || !password) {
      this.errorMessage.set('Please enter both username and password.');
      return;
    }

    this.isSubmitting.set(true);
    this.http
      .post<{ success: boolean; message: string; role: string | null; redirectUrl: string | null }>(
        this.loginUrl,
        {
          username,
          password
        }
      )
      .subscribe({
        next: (response) => {
          this.isSubmitting.set(false);
          if (!response.success) {
            this.errorMessage.set(response.message || 'Invalid username or password.');
            return;
          }

          const roleLabel = response.role === 'ADMIN' ? 'Admin' : response.role === 'USER' ? 'User' : 'User';
          this.successMessage.set(`Welcome ${roleLabel}. Login successful.`);
          this.password.set('');

          const redirectUrl = response.redirectUrl || '/dashboard';
          const normalizedUrl = redirectUrl.startsWith('/') ? redirectUrl : `/${redirectUrl}`;
          void this.router.navigateByUrl(normalizedUrl);
        },
        error: (err) => {
          this.isSubmitting.set(false);
          if (err?.status === 401) {
            this.errorMessage.set('Invalid username or password.');
            return;
          }

          this.errorMessage.set('Could not reach backend. Start backend on port 8080.');
        }
      });
  }
}
