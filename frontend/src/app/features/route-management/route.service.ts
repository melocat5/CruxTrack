import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

export interface ClimbRoute {
  id?: number;
  routeName: string;
  grade: string;
  holdColor: string;
  climbType: string; 
  startType: string; 
  routeSetter: string;
  dateSet: string;
}

@Injectable({ providedIn: 'root' })
export class RouteService {
  
  // MOCK DATABASE FOR FRONTEND TESTING
  private mockRoutes: ClimbRoute[] = [
    { id: 1, routeName: 'ABC', grade: 'V4', holdColor: 'Red', climbType: 'Bouldering', startType: 'Matched', routeSetter: 'admin', dateSet: '2026-04-20' },
    { id: 2, routeName: 'XYZ', grade: '5.9', holdColor: 'Blue', climbType: 'Tower', startType: 'Two-handed', routeSetter: 'user', dateSet: '2026-04-18' }
  ];
  
  private nextId = 3;

  getRoutes(): Observable<ClimbRoute[]> {
    return of([...this.mockRoutes]); 
  }

  addRoute(route: Partial<ClimbRoute>): Observable<ClimbRoute> {
    const newRoute: ClimbRoute = {
      id: this.nextId++,
      routeName: route.routeName || 'Unnamed Route',
      grade: route.grade || 'V0',
      holdColor: route.holdColor || 'Unknown',
      climbType: route.climbType || 'Bouldering',
      startType: route.startType || 'Matched',
      routeSetter: route.routeSetter || 'Unknown',
      dateSet: route.dateSet || new Date().toISOString().split('T')[0] // Defaults to today
    };
    this.mockRoutes.push(newRoute);
    return of(newRoute);
  }

  updateRoute(updatedRoute: ClimbRoute): Observable<ClimbRoute> {
    const index = this.mockRoutes.findIndex(r => r.id === updatedRoute.id);
    if (index > -1) {
      this.mockRoutes[index] = { ...updatedRoute };
    }
    return of(updatedRoute);
  }

  deleteRoute(id: number): Observable<void> {
    this.mockRoutes = this.mockRoutes.filter(r => r.id !== id);
    return of(void 0);
  }
}