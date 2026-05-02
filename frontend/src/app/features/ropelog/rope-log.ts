import { Component, inject, OnInit, ChangeDetectorRef, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router'; 
import { RopeService, Rope } from './rope.service';
import { AuthService } from '../../auth/auth.service'; 

@Component({
  selector: 'app-rope-log',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], 
  templateUrl: './rope-log.html',
  styleUrl: './rope-log.css'
})
export class RopeLog implements OnInit {
  ropeService = inject(RopeService);
  auth = inject(AuthService);
  cdr = inject(ChangeDetectorRef);
  platformId = inject(PLATFORM_ID);

  ropes: Rope[] = [];
  newRopeId: string = '';

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.loadRopes();
    }
  }

  loadRopes() {
    this.ropeService.getRopes().subscribe(data => {
      this.ropes = data;
      this.cdr.detectChanges(); 
    });
  }

  addRope() {
    if (!this.newRopeId.trim()) return;
    this.ropeService.addRope({ ropeId: this.newRopeId, uses: 0, notes: '' }).subscribe(() => {
      this.newRopeId = '';
      this.loadRopes(); 
    });
  }

  deleteRope(id: number | undefined) {
    if (id && confirm('Are you sure you want to completely delete this rope?')) {
      this.ropeService.deleteRope(id).subscribe(() => this.loadRopes());
    }
  }

  changeUses(rope: Rope, amount: number) {
    rope.uses += amount;
    if (rope.uses < 0) rope.uses = 0;
    this.saveRope(rope);
  }

  replaceRope(rope: Rope) {
    if (confirm(`Confirm replacement of Rope ${rope.ropeId}? This will restart uses at 0.`)) {
      rope.uses = 0;
      this.saveRope(rope);
    }
  }

  saveRope(rope: Rope) {
    this.ropeService.updateRope(rope).subscribe(() => {
      this.cdr.detectChanges(); 
    });
  }
}