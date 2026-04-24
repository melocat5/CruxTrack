import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface RouteBox {
  routename: string;
  grade: string;
  holdColor: string;
  isActive: boolean;
  routeType: string;
  starttype: string;
  routesetter: string;
  usageDate: string;
  saving?: boolean;
  saved?: boolean;
  error?: string;
}

@Component({
  selector: 'app-add-new-route',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-new-route.html',
  styleUrl: './add-new-route.css',
})
export class AddNewRoute {
  rows: RouteBox[] = [
    {
      routename: '',
      grade: '',
      holdColor: '',
      isActive: false,
      routeType: '',
      starttype: '',
      routesetter: '',
      usageDate: new Date().toISOString().split('T')[0],
      saving: false,
      saved: false,
      error: ''
    }
  ];

  constructor(
    private readonly router: Router,
    private readonly http: HttpClient
  ) {}

  addRow(): void {
    this.rows.push({
      routename: '',
      grade: '',
      holdColor: '',
      isActive: false,
      routeType: '',
      starttype: '',
      routesetter: '',
      usageDate: new Date().toISOString().split('T')[0],
      saving: false,
      saved: false,
      error: ''
    });
  }

  removeRow(index: number): void {
    this.rows.splice(index, 1);
  }

  saveRow(row: RouteBox): void {
    row.saving = true;
    row.saved = false;
    row.error = '';
    this.http.post('/api/routes', {
      routename: row.routename,
      grade: row.grade,
      holdColor: row.holdColor,
      isActive: row.isActive,
      routeType: row.routeType,
      starttype: row.starttype,
      routesetter: row.routesetter,
      usageDate: row.usageDate
    }).subscribe({
      next: () => {
        row.saving = false;
        row.saved = true;
      },
      error: () => {
        row.saving = false;
        row.error = 'Save failed. Is backend running?';
      }
    });
  }

  saveAll(): void {
    this.rows.forEach((row) => this.saveRow(row));
  }

  goBack(): void {
      void this.router.navigateByUrl('/view-edit-routes');
    }
}
