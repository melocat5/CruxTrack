import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Rope {
  id?: number;
  ropeId: string;
  uses: number;
  notes: string;
}

@Injectable({ providedIn: 'root' })
export class RopeService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/ropes';

  getRopes(): Observable<Rope[]> {
    return this.http.get<Rope[]>(this.apiUrl);
  }

  addRope(rope: Partial<Rope>): Observable<Rope> {
    return this.http.post<Rope>(this.apiUrl, rope);
  }

  updateRope(updatedRope: Rope): Observable<Rope> {
    return this.http.put<Rope>(`${this.apiUrl}/${updatedRope.id}`, updatedRope);
  }

  deleteRope(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}