import { Component } from '@angular/core';
//Needed for Location returner "Location"
import { Location } from '@angular/common';

@Component({
  selector: 'app-morning-checks',
  imports: [],
  templateUrl: './morning-checks.html',
  styleUrl: './morning-checks.css',
})
//Compare this to rop-log
export class MorningChecks {
  constructor(private readonly location: Location) {}
  goBack(): void {
    this.location.back();//Returns to previous page
  }
}