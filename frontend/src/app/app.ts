// REPLACED DEFAULT ANGULAR STARTER: NO MORE SIGNAL OR ROUTEROUTLET HERE.
// ADDED FORMSMODULE SO TWO-WAY BINDING [(NGMODEL)] WORKS ON THE LOGIN INPUTS.
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-root',
  imports: [FormsModule],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  // FIELDS FOR THE LOGIN FORM (USERNAME AND PASSWORD BOXES).
  username = '';
  password = '';
  // SHOWN WHEN LOGIN FAILS (WRONG USERNAME OR PASSWORD).
  errorMessage = '';
  // SET TO 'STAFF' OR 'ADMINISTRATOR' AFTER A SUCCESSFUL LOGIN; NULL MEANS NOT LOGGED IN.
  currentRole: 'Staff' | 'Administrator' | null = null;

  // SIMPLE HARDCODED CHECK: STAFF USES USERNAME "STAFF" AND PASSWORD "12345".
  // ADMIN USES USERNAME "ADMIN" AND PASSWORD "12345" (NOT REAL SECURITY—FOR CLASS DEMO ONLY).
  login(): void {
    if (this.username === 'Staff' && this.password === '12345') {
      this.currentRole = 'Staff';
      this.errorMessage = '';
      return;
    }

    if (this.username === 'Admin' && this.password === '12345') {
      this.currentRole = 'Administrator';
      this.errorMessage = '';
      return;
    }

    this.currentRole = null;
    this.errorMessage = 'Invalid username or password.';
  }

  // CLEARS LOGIN STATE SO THE USER SEES THE LOGIN SCREEN AGAIN.
  logout(): void {
    this.currentRole = null;
    this.username = '';
    this.password = '';
    this.errorMessage = '';
  }
}
