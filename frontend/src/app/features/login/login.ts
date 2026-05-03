import { CommonModule } from '@angular/common';
import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { Component, inject, ChangeDetectorRef } from '@angular/core'; 
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AuthService } from '../../auth/auth.service';

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
  private readonly cdr = inject(ChangeDetectorRef);
  private readonly http = inject(HttpClient); 

  loginForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  // Forgot Password Form & State
  forgotPasswordMode = false;
  fpStep = 1;
  fpErrorMessage = '';
  fpSuccessMessage = '';

  fpForm: FormGroup = this.formBuilder.group({
    targetUsername: ['', Validators.required],
    adminUsername: ['', Validators.required],
    adminPassword: ['', Validators.required],
    newPassword: ['', Validators.required],
    confirmPassword: ['', Validators.required],
  });

  errorMessage = '';
  submitting = false;

  get step1Valid() {
    return !!this.fpForm.get('targetUsername')?.value;
  }

  get step2Valid() {
    return !!this.fpForm.get('adminUsername')?.value && !!this.fpForm.get('adminPassword')?.value;
  }

  get step3Valid() {
    const p1 = this.fpForm.get('newPassword')?.value;
    const p2 = this.fpForm.get('confirmPassword')?.value;
    return !!p1 && !!p2 && p1 === p2;
  }

  get passwordsMatch() {
    return this.fpForm.get('newPassword')?.value === this.fpForm.get('confirmPassword')?.value;
  }

  toggleForgotPassword() {
    this.forgotPasswordMode = !this.forgotPasswordMode;
    this.fpStep = 1;
    this.fpForm.reset();
    this.fpErrorMessage = '';
    this.fpSuccessMessage = '';
    this.errorMessage = '';
  }

  nextStep(step: number) {
    this.fpErrorMessage = '';
    this.fpStep = step;
  }

  onResetPassword() {
    if (!this.step3Valid) {
      this.fpErrorMessage = 'Passwords do not match.';
      return;
    }

    this.submitting = true;
    this.fpErrorMessage = '';

    const payload = this.fpForm.getRawValue();

    // Call to a designated admin reset endpoint
    this.http.post('/api/auth/admin-reset-password', payload).subscribe({
      next: () => {
        this.submitting = false;
        this.fpSuccessMessage = 'Password successfully reset!';
        this.fpStep = 4; // Move to success step
        this.cdr.detectChanges();
      },
      error: () => {
        this.submitting = false;
        this.fpErrorMessage = 'Failed to reset password. Ensure admin credentials are correct.';
        this.cdr.detectChanges();
      }
    });
  }

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
        this.cdr.detectChanges(); 
        void this.router.navigate(['/dashboard']);
      },
      error: (err: unknown) => {
        this.submitting = false;
        if (err instanceof HttpErrorResponse && err.status === 401) {
          this.errorMessage = 'Invalid username or password.';
        } else {
          this.errorMessage = 'Unable to sign in. Is the backend running on port 8080?';
        }
        this.cdr.detectChanges(); 
      },
    });
  }
}

