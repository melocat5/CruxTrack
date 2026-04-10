import { isPlatformBrowser } from '@angular/common';
import { inject, PLATFORM_ID } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { map } from 'rxjs';

import { AuthService } from './auth.service';

// CRUXTRACK: BLOCKS /DASHBOARD UNTIL /API/AUTH/ME SAYS YOU ARE LOGGED IN
export const authGuard: CanActivateFn = () => {
  const platformId = inject(PLATFORM_ID);
  if (!isPlatformBrowser(platformId)) {
    return true;
  }
  const auth = inject(AuthService);
  const router = inject(Router);
  return auth.refreshMe().pipe(map((ok) => (ok ? true : router.parseUrl('/login'))));
};

// CRUXTRACK: BLOCKS /LOGIN IF YOU ARE ALREADY LOGGED IN (SENDS YOU TO DASHBOARD)
export const guestGuard: CanActivateFn = () => {
  const platformId = inject(PLATFORM_ID);
  if (!isPlatformBrowser(platformId)) {
    return true;
  }
  const auth = inject(AuthService);
  const router = inject(Router);
  return auth.refreshMe().pipe(map((ok) => (ok ? router.parseUrl('/dashboard') : true)));
};
