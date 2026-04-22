// CRUXTRACK: ROUTE RULES — GUARDS CHECK WITH THE BACKEND WHETHER THE USER IS LOGGED IN BEFORE OPENING A PAGE
import { Routes } from '@angular/router';
import { authGuard, guestGuard } from './auth/auth.guard';
import { Login } from './features/login/login';
import { Dashboard } from './features/dashboard/dashboard';
import { RopeLog } from './features/ropelog/rope-log';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  // CRUXTRACK: IF ALREADY LOGGED IN, GUESTGUARD SENDS YOU TO /DASHBOARD INSTEAD OF SHOWING LOGIN AGAIN
  { path: 'login', component: Login, canActivate: [guestGuard] },
  // CRUXTRACK: IF NOT LOGGED IN, AUTHGUARD SENDS YOU TO /LOGIN
  { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
  { path: 'ropelog', component: RopeLog, canActivate: [authGuard] },
];
