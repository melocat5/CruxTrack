import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../auth/auth.service';
import { RopeService } from '../ropelog/rope.service';
import { RouteService } from '../route-management/route.service'; 
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {
  auth = inject(AuthService);
  ropeService = inject(RopeService);
  routeService = inject(RouteService); 
  platformId = inject(PLATFORM_ID);

  urgentReplacements = 0;
  soonReplacements = 0;
  
  activeRoutesCount = 0; 

ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.ropeService.getRopes().subscribe(ropes => {
        this.urgentReplacements = ropes.filter(r => r.uses >= 100).length;
        this.soonReplacements = ropes.filter(r => r.uses >= 90 && r.uses < 100).length;
      });

      this.routeService.getRoutes().subscribe(routes => {
        this.activeRoutesCount = routes.length;
      });
    }
  }
}

