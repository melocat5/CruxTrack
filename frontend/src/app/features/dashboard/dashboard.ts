import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';

import { AuthService } from '../../auth/auth.service';

// CRUXTRACK: MAIN SCREEN AFTER LOGIN — READS AUTHSERVICE FOR USER INFO AND LOGOUT
@Component({
  selector: 'app-dashboard',
  imports: [CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  protected readonly auth = inject(AuthService);
}
