import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';

export interface Rope {
  id?: number;
  ropeId: string;
  uses: number;
  notes: string;
}

@Injectable({ providedIn: 'root' })
export class RopeService {
  
  // MOCK DATA AND METHODS TO SIMULATE BACKEND INTERACTIONS — REPLACE WITH REAL HTTP CALLS LATER
  private mockRopes: Rope[] = [
    { id: 1, ropeId: 'A', uses: 12, notes: 'Brand new, looks good.' },
    { id: 2, ropeId: 'B', uses: 96, notes: 'Nearing end of life, check core.' },
    { id: 3, ropeId: 'C', uses: 105, notes: 'Needs replacement immediately.' } 
  ];
  
  private nextId = 4;

  //  SIMULATE GET REQUEST
  getRopes(): Observable<Rope[]> {
    return of([...this.mockRopes]); 
  }

  //  SIMULATE POST REQUEST
  addRope(rope: Partial<Rope>): Observable<Rope> {
    const newRope: Rope = {
      id: this.nextId++,
      ropeId: rope.ropeId || 'Unknown',
      uses: rope.uses || 0,
      notes: rope.notes || ''
    };
    this.mockRopes.push(newRope);
    return of(newRope);
  }

  //  SIMULATE PUT REQUEST
  updateRope(updatedRope: Rope): Observable<Rope> {
    const index = this.mockRopes.findIndex(r => r.id === updatedRope.id);
    if (index > -1) {
      this.mockRopes[index] = { ...updatedRope };
    }
    return of(updatedRope);
  }

  //  SIMULATE DELETE REQUEST
  deleteRope(id: number): Observable<void> {
    this.mockRopes = this.mockRopes.filter(r => r.id !== id);
    return of(void 0);
  }
}