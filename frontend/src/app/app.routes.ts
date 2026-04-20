import { Routes } from '@angular/router';
import { Login } from './login/login';
import { Dashboard } from './dashboard/dashboard';
import { RopLog } from './rop-log/rop-log';
import { ViewEditRoutes } from './view-edit-routes/view-edit-routes';
import { MorningChecks } from './morning-checks/morning-checks';
import { ViewInventory } from './view-inventory/view-inventory';

export const routes: Routes = [
	{ path: '', redirectTo: 'login', pathMatch: 'full' },
	{ path: 'login', component: Login },
	{ path: 'dashboard', component: Dashboard },
	{ path: 'rope-log', component: RopLog },
	{ path: 'view-edit-routes', component: ViewEditRoutes },
	{ path: 'morning-checks', component: MorningChecks },
	{ path: 'view-inventory', component: ViewInventory },
	{ path: '**', redirectTo: 'login' }
];
