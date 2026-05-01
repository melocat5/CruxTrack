import { Component, inject, OnInit, ChangeDetectorRef, PLATFORM_ID } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { RouteService, ClimbRoute } from './route.service';
import { AuthService } from '../../auth/auth.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-route-management',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './route-management.html',
  styleUrl: './route-management.css'
})
export class RouteManagement implements OnInit {
  routeService = inject(RouteService);
  auth = inject(AuthService);
  cdr = inject(ChangeDetectorRef);
  platformId = inject(PLATFORM_ID);

  routes: ClimbRoute[] = [];
  
  // Variables for the Add Route form
  newName = '';
  newGrade = '';
  newHoldColor = '';
  newClimbType = 'Bouldering';
  newStartType = 'Matched';
  newSetter = '';

  // Tracks which row is currently in "Edit Mode"
  editingId: number | null = null;

 ngOnInit() {

    if (isPlatformBrowser(this.platformId)) {
      this.loadRoutes();
      
      const user = this.auth.user();
      if (user) {
         this.newSetter = user.username;
      }
    }
  }

  loadRoutes() {
    this.routeService.getRoutes().subscribe(data => {
      this.routes = data;
      this.cdr.detectChanges();
    });
  }

  addRoute() {
    if (!this.newName.trim()) return;
    
    this.routeService.addRoute({
      routeName: this.newName,
      grade: this.newGrade,
      holdColor: this.newHoldColor,
      climbType: this.newClimbType,
      startType: this.newStartType,
      routeSetter: this.newSetter,
      dateSet: new Date().toISOString().split('T')[0],
      isActive: true
    }).subscribe(() => {
      // Clear inputs after saving
      this.newName = '';
      this.newGrade = '';
      this.newHoldColor = '';
      this.loadRoutes();
    });
  }

  deleteRoute(id: number | undefined) {
    if (id && confirm('Are you sure you want to remove this route?')) {
      this.routeService.deleteRoute(id).subscribe(() => this.loadRoutes());
    }
  }

  editRoute(id: number | undefined) {
    if (id) {
       this.editingId = id;
       this.cdr.detectChanges();
    }
  }

  saveEdit(route: ClimbRoute) {
    this.routeService.updateRoute(route).subscribe(() => {
      this.editingId = null; // Turns off edit mode
      this.cdr.detectChanges();
    });
  }

  toggleActiveStatus(route: ClimbRoute) {
    // Flip the boolean
    route.isActive = !route.isActive;
    // Save to the database
    this.routeService.updateRoute(route).subscribe(() => {
      this.cdr.detectChanges();
    });
  }
}