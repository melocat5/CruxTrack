import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../auth/auth.service';

// CRUXTRACK: LOGIN FORM — SENDS USERNAME/PASSWORD TO SPRING BOOT AND NAVIGATES TO DASHBOARD ON SUCCESS
@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {
  private readonly formBuilder = inject(FormBuilder);
  private readonly router = inject(Router);
  private readonly auth = inject(AuthService);

  loginForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  // CRUXTRACK: SHOWN WHEN THE SERVER RETURNS 401 OR THE REQUEST FAILS (E.G. BACKEND OFFLINE)
  errorMessage = '';
  submitting = false;

  onSubmit() {
    if (!this.loginForm.valid || this.submitting) {
      return;
    }
    const { username, password } = this.loginForm.getRawValue() as { username: string; password: string };
    this.errorMessage = '';
    this.submitting = true;
    this.auth.login(username, password).subscribe({
      next: () => {
        this.submitting = false;
        void this.router.navigate(['/dashboard']);
      },
      error: (err: unknown) => {
        this.submitting = false;
        // CRUXTRACK: 401 = WRONG USERNAME OR PASSWORD (SPRING REJECTED LOGIN)
        if (err instanceof HttpErrorResponse && err.status === 401) {
          this.errorMessage = 'Invalid username or password.';
        } else {
          // CRUXTRACK: OFTEN MEANS NETWORK ERROR OR BACKEND NOT STARTED — CHECK TERMINAL RUNNING MVN SPRING-BOOT:RUN
          this.errorMessage = 'Unable to sign in. Is the backend running on port 8080?';
        }
      },
    });
  }
}

