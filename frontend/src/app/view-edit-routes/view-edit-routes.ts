import { Component, OnInit, signal } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';

interface Route {
  routeId: number;
  routename: string;
  grade: string;
  holdColor: string;
  isActive: boolean;
  routeType: string;
  starttype: string;
  routesetter: string;
  usageDate: string;
}

@Component({
  selector: 'app-view-edit-routes',
  imports: [CommonModule],
  templateUrl: './view-edit-routes.html',
  styleUrl: './view-edit-routes.css',
})
export class ViewEditRoutes implements OnInit {
  readonly routes = signal<Route[]>([]);
  readonly loading = signal(false);
  readonly error = signal('');

  constructor(
    private readonly router: Router,
    private readonly http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadRoutes();
  }

  loadRoutes(): void {
    this.loading.set(true);
    this.error.set('');
    this.http.get<Route[]>('/api/routes').subscribe({
      next: (data) => {
        this.routes.set(data || []);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load routes. Is backend running?');
        this.loading.set(false);
      }
    });
  }

  deleteRoute(routeId: number): void {
    if (!confirm('Are you sure you want to delete this route?')) {
      return;
    }
    this.http.delete(`/api/routes/${routeId}`).subscribe({
      next: () => {
        this.loadRoutes();
      },
      error: () => {
        this.error.set('Failed to delete route.');
      }
    });
  }

  goBack(): void {
    void this.router.navigateByUrl('/dashboard');
  }

  addRoute(): void {
    void this.router.navigateByUrl('/add-new-route');
  }
}