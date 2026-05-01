import { Component, inject, OnInit, ChangeDetectorRef, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { UserSettingsService, UserProfile } from './user-settings.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-user-settings',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './user-settings.html',
  styleUrl: './user-settings.css'
})
export class UserSettings implements OnInit {
  settingsService = inject(UserSettingsService);
  auth = inject(AuthService);
  cdr = inject(ChangeDetectorRef);
  platformId = inject(PLATFORM_ID);

  activeTab: 'profile' | 'create' | 'manage' = 'profile';

  // Profile State
  profile: UserProfile | null = null;
  pwCurrent = '';
  pwNew = '';
  pwConfirm = '';
  pwMessage = '';
  profileMessage = '';

  // Admin: Create User State
  newAcc = { username: '', firstName: '', lastName: '', password: '', role: 'USER' };
  createMessage = '';

  // Admin: Manage Users State
  allUsers: UserProfile[] = [];

  get isAdmin(): boolean {
    const user = this.auth.user();
    return user?.primaryRole?.toUpperCase() === 'ADMIN' || user?.roles?.includes('ADMIN') || false;
  }

  ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.loadMyProfile();
    }
  }

  setTab(tab: 'profile' | 'create' | 'manage') {
    this.activeTab = tab;
    if (tab === 'manage' && this.isAdmin) {
      this.loadAllUsers();
    }
  }

  // --- Profile Methods ---
  loadMyProfile() {
    this.settingsService.getMyProfile().subscribe(data => {
      this.profile = data;
      this.cdr.detectChanges();
    });
  }

  saveProfile() {
    if (!this.profile) return;
    this.settingsService.updateProfile({
      username: this.profile.username,
      preferredName: this.profile.preferredName
    }).subscribe({
      next: () => {
        this.profileMessage = 'Profile updated successfully.';
        setTimeout(() => this.profileMessage = '', 3000);
        this.auth.refreshMe().subscribe(); // Refresh global auth state if username changed
        this.cdr.detectChanges();
      }
    });
  }

  updatePassword() {
    if (this.pwNew !== this.pwConfirm) {
      this.pwMessage = 'New passwords do not match!';
      return;
    }
    if (this.pwNew.length < 6) {
      this.pwMessage = 'Password must be at least 6 characters.';
      return;
    }
    
    this.settingsService.changePassword({ current: this.pwCurrent, new: this.pwNew }).subscribe({
      next: () => {
        this.pwMessage = 'Password changed successfully.';
        this.pwCurrent = '';
        this.pwNew = '';
        this.pwConfirm = '';
        this.cdr.detectChanges();
      },
      error: () => {
        this.pwMessage = 'Failed to change password. Is your current password correct?';
        this.cdr.detectChanges();
      }
    });
  }

  // --- Admin Methods ---
  loadAllUsers() {
    this.settingsService.getAllUsers().subscribe(data => {
      this.allUsers = data;
      this.cdr.detectChanges();
    });
  }

  createNewAccount() {
    if (!this.newAcc.username || !this.newAcc.password) return;
    
    this.settingsService.createUser(this.newAcc).subscribe({
      next: () => {
        this.createMessage = `Account for ${this.newAcc.username} created successfully!`;
        this.newAcc = { username: '', firstName: '', lastName: '', password: '', role: 'USER' };
        this.cdr.detectChanges();
      },
      error: () => {
        this.createMessage = 'Failed to create account. Username might be taken.';
        this.cdr.detectChanges();
      }
    });
  }

  deactivateAccount(user: UserProfile) {
    if (user.id && confirm(`Are you sure you want to deactivate ${user.username}? They will no longer be able to log in.`)) {
      this.settingsService.deactivateUser(user.id).subscribe(() => {
        this.loadAllUsers(); // Reload list
      });
    }
  }
}