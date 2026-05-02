import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UserProfile {
  id?: number;
  username: string;
  firstName: string;
  lastName: string;
  preferredName: string;
  role: string;
  isActive: boolean;
}

@Injectable({ providedIn: 'root' })
export class UserSettingsService {
  private readonly http = inject(HttpClient);
  private readonly apiUrl = '/api/users';

  // --- Regular User Endpoints ---
  getMyProfile(): Observable<UserProfile> {
    return this.http.get<UserProfile>(`${this.apiUrl}/me`);
  }

  updateProfile(profile: Partial<UserProfile>): Observable<UserProfile> {
    return this.http.put<UserProfile>(`${this.apiUrl}/me`, profile);
  }

  changePassword(passwordData: any): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/me/change-password`, passwordData);
  }

  // --- Admin Endpoints ---
  getAllUsers(): Observable<UserProfile[]> {
    return this.http.get<UserProfile[]>(this.apiUrl);
  }

  createUser(newUser: any): Observable<UserProfile> {
    return this.http.post<UserProfile>(this.apiUrl, newUser);
  }

  deactivateUser(id: number): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${id}/deactivate`, {});
  }
}