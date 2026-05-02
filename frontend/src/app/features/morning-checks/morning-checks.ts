import { Component, inject, OnInit, ChangeDetectorRef, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { MorningCheckService, MorningCheckSubmission, MorningTask } from './morning-check.service';
import { InventoryService, InventoryItem } from '../inventory/inventory.service';
import { AuthService } from '../../auth/auth.service';

interface TaskItem {
  id?: number;
  name: string;
}

type EquipmentPresentChoice = '' | 'yes' | 'no';

@Component({
  selector: 'app-morning-checks',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './morning-checks.html',
  styleUrl: './morning-checks.css'
})
export class MorningChecks implements OnInit {
  morningCheckService = inject(MorningCheckService);
  inventoryService = inject(InventoryService);
  auth = inject(AuthService);
  cdr = inject(ChangeDetectorRef);
  platformId = inject(PLATFORM_ID);

  tasks: TaskItem[] = [];
  newTaskName: string = '';

  /** Empty until the user selects Yes or No; submit is enabled once set. */
  equipmentPresent: EquipmentPresentChoice = '';

  notes = '';
  inventoryItems: InventoryItem[] = [];
  submissions: MorningCheckSubmission[] = [];

  get isAdmin(): boolean {
    const user = this.auth.user();
    return user?.primaryRole?.toUpperCase() === 'ADMIN' || user?.roles?.includes('ADMIN') || false;
  }

  get canSubmitChecks(): boolean {
    return this.equipmentPresent === 'yes' || this.equipmentPresent === 'no';
  }

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.loadInventory();
      if (this.isAdmin) {
        this.loadTasks();
        this.loadSubmissions();
      }
    }
  }

  loadTasks() {
    this.morningCheckService.getTasks().subscribe(data => {
      this.tasks = data.map(t => ({ id: t.id, name: t.taskName }));
      this.cdr.detectChanges();
    });
  }

  addTask() {
    if (!this.newTaskName.trim() || !this.isAdmin) return;
    this.morningCheckService.addTask({ 
      taskName: this.newTaskName, 
      isActive: true 
    }).subscribe(() => {
      this.newTaskName = '';
      this.loadTasks();
    });
  }

  deleteTask(id: number | undefined) {
    if (id && this.isAdmin && confirm('Are you sure you want to permanently delete this task?')) {
      this.morningCheckService.deleteTask(id).subscribe(() => this.loadTasks());
    }
  }

  
  loadInventory() {
    this.inventoryService.getInventory().subscribe(data => {
      this.inventoryItems = data;
      this.cdr.detectChanges();
    });
  }

  loadSubmissions() {
    this.morningCheckService.getSubmissions().subscribe(data => {
      this.submissions = data.reverse();
      this.cdr.detectChanges();
    });
  }

  private buildSubmissionNotes(): string {
    const answer = this.equipmentPresent === 'yes' ? 'Yes' : 'No';
    let text = `All equipment present: ${answer}`;
    const detail = this.notes.trim();
    if (detail) {
      text += '\n\n' + detail;
    }
    return text;
  }

  submitForm() {
    if (!this.canSubmitChecks) return;

    const user = this.auth.user();
    const submission = {
      timestamp: new Date().toISOString(),
      notes: this.buildSubmissionNotes(),
      submittedBy: user?.username || 'Unknown'
    };

    this.morningCheckService.submitCheck(submission).subscribe(() => {
      this.equipmentPresent = '';
      this.notes = '';
      if (this.isAdmin) {
        this.loadSubmissions();
      }
      alert('Daily inventory check submitted successfully!');
      this.cdr.detectChanges();
    });
  }
}