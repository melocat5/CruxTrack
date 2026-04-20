import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewInventory } from './view-inventory';

describe('ViewInventory', () => {
  let component: ViewInventory;
  let fixture: ComponentFixture<ViewInventory>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewInventory]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewInventory);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
