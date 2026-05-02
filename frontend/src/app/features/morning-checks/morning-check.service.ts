import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface MorningCheckSubmission {
  id?: number;
  timestamp: string;
  notes: string;
  submittedBy: string;
}

export interface MorningTask {
  id?: number;
  taskName: string;
  isActive: boolean;
}

@Injectable({ providedIn: 'root' })
export class MorningCheckService {
  private readonly http = inject(HttpClient);
  
  private readonly apiUrl = '/api/morning-checks';
  private readonly tasksUrl = '/api/morning-tasks';

  getSubmissions(): Observable<MorningCheckSubmission[]> {
    return this.http.get<MorningCheckSubmission[]>(this.apiUrl);
  }

  submitCheck(submission: Partial<MorningCheckSubmission>): Observable<MorningCheckSubmission> {
    return this.http.post<MorningCheckSubmission>(this.apiUrl, submission);
  }

  getTasks(): Observable<MorningTask[]> {
    return this.http.get<MorningTask[]>(this.tasksUrl);
  }

  addTask(task: Partial<MorningTask>): Observable<MorningTask> {
    return this.http.post<MorningTask>(this.tasksUrl, task);
  }

  deleteTask(id: number): Observable<void> {
    return this.http.delete<void>(`${this.tasksUrl}/${id}`);
  }
}