import { Component, PLATFORM_ID, inject } from '@angular/core';
import { isPlatformBrowser } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  imports: [],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css',
})
export class Dashboard {
  private readonly platformId = inject(PLATFORM_ID);
  readonly role: string;

  constructor(private readonly router: Router) {
    this.role = isPlatformBrowser(this.platformId)
      ? (localStorage.getItem('role') ?? 'USER')
      : 'USER';
  }

  logout(): void {
    if (isPlatformBrowser(this.platformId)) {
      localStorage.removeItem('role');
    }

    void this.router.navigateByUrl('/login');
  }

  viewRopeLog(): void {
    void this.router.navigateByUrl('/rope-log');
  }

  vieweditroutes(): void {
    void this.router.navigateByUrl('/view-edit-routes');
  }

  morningchecks(): void {
    void this.router.navigateByUrl('/morning-checks');
  }

  viewinventory(): void {
    void this.router.navigateByUrl('/view-inventory');
  }
}
