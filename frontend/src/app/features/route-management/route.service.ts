import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ClimbRoute {
  id?: number;
  routeName: string;
  grade: string;
  holdColor: string;
  climbType: string; 
  startType: string; 
  routeSetter: string;
  dateSet: string;
  isActive: boolean;
}

@Injectable({ providedIn: 'root' })
export class RouteService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = 'http://localhost:8080/api/routes';

  getRoutes(): Observable<ClimbRoute[]> {
    return this.http.get<ClimbRoute[]>(this.apiUrl);
  }

  addRoute(route: Partial<ClimbRoute>): Observable<ClimbRoute> {
    return this.http.post<ClimbRoute>(this.apiUrl, route);
  }

  updateRoute(updatedRoute: ClimbRoute): Observable<ClimbRoute> {
    return this.http.put<ClimbRoute>(`${this.apiUrl}/${updatedRoute.id}`, updatedRoute);
  }

  deleteRoute(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}