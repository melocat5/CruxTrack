import { Component, OnInit, signal  } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';

interface RopeRow {
  id?: number; // 👈 ADD THIS
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
export class RopLog implements OnInit {
  readonly loading = signal(false);
  readonly error = signal('');
  readonly rows = signal<RopeRow[]>([
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
  ]);

  readonly savedRows = signal<RopeRow[]>([]);

  constructor(
    private readonly router: Router,
    private readonly http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadRopes();
  }

  addRow(): void {
    this.rows.update(arr => [...arr, {
      status: '',
      usesChange: 0,
      totalUses: 0,
      notes: '',
      usageDate: new Date().toISOString().split('T')[0],
      saving: false,
      saved: false,
      error: ''
    }]);
  }

  loadRopes(): void {
    this.loading.set(true);
    this.error.set('');
    this.http.get<RopeRow[]>('/api/ropes').subscribe({
      next: (data) => {
        this.savedRows.set(data || []);
        this.loading.set(false);
      },
      error: () => {
        this.error.set('Failed to load ropes. Is backend running?');
        this.loading.set(false);
      }
    });
  }

  removeRow(index: number): void {
    this.rows.update(arr => arr.filter((_, i) => i !== index));
  }

  saveRow(row: RopeRow, index: number): void {
    row.saving = true;
    row.saved = false;
    row.error = '';

    this.http.post<RopeRow>('/api/ropes', {
      status: row.status,
      usesChange: row.usesChange,
      totalUses: row.totalUses,
      notes: row.notes,
      usageDate: row.usageDate
    }).subscribe({
      next: (saved) => {
        row.saving = false;
        row.saved = true;
        row.id = saved.id;
        row.totalUses = saved.totalUses;

        this.savedRows.update(arr => [{
          id: saved.id,
          status: saved.status,
          usesChange: saved.usesChange,
          totalUses: saved.totalUses,
          notes: saved.notes,
          usageDate: saved.usageDate,
          saved: true,
          saving: false,
          error: ''
        }, ...arr]);
      },
      error: () => {
        row.saving = false;
        row.error = 'Save failed. Is backend running?';
      }
    });
  }

  updateRow(row: RopeRow): void {
    this.http.put<RopeRow>(`/api/ropes/${row.id}`, row).subscribe({
      next: (updated) => {
        row.totalUses = updated.totalUses;
        row.usesChange = updated.usesChange;
        this.savedRows.update(arr =>
          arr.map(r => r.id === updated.id ? { ...r, totalUses: row.totalUses, usesChange: updated.usesChange } : r)
        );
      },
      error: () => {
        console.error('Update failed');
      }
    });
  }

  deleteSavedRow(row: RopeRow): void {
    if (row.id == null) return;
    this.http.delete(`/api/ropes/${row.id}`).subscribe({
      next: () => {
        this.savedRows.update(arr => arr.filter(r => r.id !== row.id));
      },
      error: () => {
        console.error('Delete failed');
      }
    });
  }

  saveAll(): void {
    this.rows().forEach((row, index) => this.saveRow(row, index));
  }

  goBack(): void {
    void this.router.navigateByUrl('/dashboard');
  }
}
