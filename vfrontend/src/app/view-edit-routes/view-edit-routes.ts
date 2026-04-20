import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-view-edit-routes',
  imports: [],
  templateUrl: './view-edit-routes.html',
  styleUrl: './view-edit-routes.css',
})
export class ViewEditRoutes {
  constructor(private readonly router: Router) {}

  goBack(): void {
    void this.router.navigateByUrl('/dashboard');
  }
}
