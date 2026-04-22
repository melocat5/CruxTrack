import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  // The hardcoded mock login logic has been removed.
  // Authentication is now handled by AuthService and the Login component.
}
