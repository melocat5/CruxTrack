import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-inventory',
  imports: [],
  templateUrl: './view-inventory.html',
  styleUrl: './view-inventory.css',
})
export class ViewInventory {
  constructor(private readonly router: Router) {}

  goBack(): void {
    void this.router.navigateByUrl('/dashboard');
  }
}
