// CRUXTRACK: ROUTE RULES — GUARDS CHECK WITH THE BACKEND WHETHER THE USER IS LOGGED IN BEFORE OPENING A PAGE
import { Routes } from '@angular/router';
import { authGuard, guestGuard } from './auth/auth.guard';
import { Login } from './features/login/login';
import { Dashboard } from './features/dashboard/dashboard';
import { RopeLog } from './features/ropelog/rope-log';
import { RouteManagement } from './features/route-management/route-management';
import { Inventory } from './features/inventory/inventory';
import { MorningChecks } from './features/morning-checks/morning-checks';
import { UserSettings } from './features/user-settings/user-settings';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  // CRUXTRACK: IF ALREADY LOGGED IN, GUESTGUARD SENDS YOU TO /DASHBOARD INSTEAD OF SHOWING LOGIN AGAIN
  { path: 'login', component: Login, canActivate: [guestGuard] },
  // CRUXTRACK: IF NOT LOGGED IN, AUTHGUARD SENDS YOU TO /LOGIN
  { path: 'dashboard', component: Dashboard, canActivate: [authGuard] },
  { path: 'ropelog', component: RopeLog, canActivate: [authGuard] },
  { path: 'routes', component: RouteManagement, canActivate: [authGuard] },
  { path: 'inventory', component: Inventory, canActivate: [authGuard] },
  { path: 'morning-checks', component: MorningChecks, canActivate: [authGuard] },
  { path: 'settings', component: UserSettings, canActivate: [authGuard] },
];
