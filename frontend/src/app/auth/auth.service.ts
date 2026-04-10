import { isPlatformBrowser } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { inject, Injectable, PLATFORM_ID, signal } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, map, Observable, of, tap } from 'rxjs';

import { UserInfo } from './auth.models';

// CRUXTRACK: SINGLE PLACE THAT CALLS THE BACKEND /API/AUTH/* ENDPOINTS AND STORES WHO IS LOGGED IN
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly platformId = inject(PLATFORM_ID);

  // CRUXTRACK: UI COMPONENTS READ THIS TO SHOW USERNAME / ROLE
  readonly user = signal<UserInfo | null>(null);

  constructor() {
    // CRUXTRACK: SKIP HTTP DURING SERVER RENDER (NO BROWSER COOKIES THERE) — CLIENT WILL REFRESH AFTER LOAD
    if (isPlatformBrowser(this.platformId)) {
      this.refreshMe().subscribe();
    }
  }

  // CRUXTRACK: CALLS GET /API/AUTH/ME — TRUE IF SESSION COOKIE IS STILL VALID
  refreshMe(): Observable<boolean> {
    return this.http.get<UserInfo>('/api/auth/me').pipe(
      tap((u) => this.user.set(u)),
      map(() => true),
      catchError(() => {
        this.user.set(null);
        return of(false);
      }),
    );
  }

  // CRUXTRACK: POST /API/AUTH/LOGIN — SERVER SETS SESSION COOKIE ON SUCCESS
  login(username: string, password: string): Observable<UserInfo> {
    return this.http.post<UserInfo>('/api/auth/login', { username, password }).pipe(tap((u) => this.user.set(u)));
  }

  // CRUXTRACK: POST /API/AUTH/LOGOUT — CLEARS SERVER SESSION AND RETURNS TO LOGIN PAGE
  logout(): void {
    this.http.post<void>('/api/auth/logout', {}).subscribe({
      next: () => {
        this.user.set(null);
        void this.router.navigate(['/login']);
      },
      error: () => {
        this.user.set(null);
        void this.router.navigate(['/login']);
      },
    });
  }
}
