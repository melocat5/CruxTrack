import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../auth/auth.service';
import { RopeService } from '../ropelog/rope.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './dashboard.html',
  styleUrl: './dashboard.css'
})
export class Dashboard implements OnInit {
  auth = inject(AuthService);
  ropeService = inject(RopeService);

  urgentReplacements = 0; // 100+ uses
  soonReplacements = 0;   // 90-99 uses

  ngOnInit() {
    this.ropeService.getRopes().subscribe(ropes => {
      this.urgentReplacements = ropes.filter(r => r.uses >= 100).length;
      this.soonReplacements = ropes.filter(r => r.uses >= 90 && r.uses < 100).length;
    });
  }
}
