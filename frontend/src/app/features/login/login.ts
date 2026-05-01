import { CommonModule } from '@angular/common';
import { HttpErrorResponse } from '@angular/common/http';
import { Component, inject, ChangeDetectorRef } from '@angular/core'; // 1. ADD ChangeDetectorRef
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
  private readonly cdr = inject(ChangeDetectorRef); // 2. INJECT IT HERE

  loginForm: FormGroup = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

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
        this.cdr.detectChanges(); // <-- 3a. UPDATE SCREEN IMMEDIATELY
        void this.router.navigate(['/dashboard']);
      },
      error: (err: unknown) => {
        this.submitting = false;
        if (err instanceof HttpErrorResponse && err.status === 401) {
          this.errorMessage = 'Invalid username or password.';
        } else {
          this.errorMessage = 'Unable to sign in. Is the backend running on port 8080?';
        }
        this.cdr.detectChanges(); // <-- 3b. UPDATE SCREEN IMMEDIATELY
      },
    });
  }
}

