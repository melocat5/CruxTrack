import { Component, inject, OnInit, ChangeDetectorRef, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { InventoryService, InventoryItem } from './inventory.service';
import { AuthService } from '../../auth/auth.service';

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './inventory.html',
  styleUrl: './inventory.css'
})
export class Inventory implements OnInit {
  inventoryService = inject(InventoryService);
  auth = inject(AuthService);
  cdr = inject(ChangeDetectorRef);
  platformId = inject(PLATFORM_ID);

  items: InventoryItem[] = [];
  newItemName: string = '';
  newItemQuantity: number = 0;

  get isAdmin(): boolean {
    const user = this.auth.user();
    // Checks if the user has an admin role to toggle UI elements
    return user?.primaryRole?.toUpperCase() === 'ADMIN' || user?.roles?.includes('ADMIN') || false;
  }

ngOnInit() {
    if (isPlatformBrowser(this.platformId)) {
      this.loadInventory();
    }
  }

  loadInventory() {
    this.inventoryService.getInventory().subscribe(data => {
      this.items = data;
      this.cdr.detectChanges();
    });
  }

  addItem() {
    if (!this.newItemName.trim() || !this.isAdmin) return;
    this.inventoryService.addItem({ 
      itemName: this.newItemName, 
      quantity: this.newItemQuantity, 
      notes: '' 
    }).subscribe(() => {
      this.newItemName = '';
      this.newItemQuantity = 0;
      this.loadInventory();
    });
  }

  deleteItem(id: number | undefined) {
    if (id && this.isAdmin && confirm('Are you sure you want to completely remove this item?')) {
      this.inventoryService.deleteItem(id).subscribe(() => this.loadInventory());
    }
  }

  changeQuantity(item: InventoryItem, amount: number) {
    if (!this.isAdmin) return; // Failsafe if a regular user somehow triggers this
    item.quantity += amount;
    if (item.quantity < 0) item.quantity = 0;
    this.saveItem(item);
  }

  saveItem(item: InventoryItem) {
    this.inventoryService.updateItem(item).subscribe(() => {
      this.cdr.detectChanges();
    });
  }
}