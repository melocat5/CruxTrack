import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface RopeRow {
  status: string;
  usesChange: number;
  totalUses: number;
  notes: string;
  usageDate: string;
  saving?: boolean;
  saved?: boolean;
  error?: string;
}

@Component({
  selector: 'app-rop-log',
  imports: [CommonModule, FormsModule],
  templateUrl: './rop-log.html',
  styleUrl: './rop-log.css',
})
export class RopLog {
  rows: RopeRow[] = [
    {
      status: '',
      usesChange: 0,
      totalUses: 0,
      notes: '',
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
      status: '',
      usesChange: 0,
      totalUses: 0,
      notes: '',
      usageDate: new Date().toISOString().split('T')[0],
      saving: false,
      saved: false,
      error: ''
    });
  }

  removeRow(index: number): void {
    this.rows.splice(index, 1);
  }

  saveRow(row: RopeRow): void {
    row.saving = true;
    row.saved = false;
    row.error = '';
    this.http.post('/api/ropes', {
      status: row.status,
      usesChange: row.usesChange,
      totalUses: row.totalUses,
      notes: row.notes,
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
    void this.router.navigateByUrl('/dashboard');
  }
}

