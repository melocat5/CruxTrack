import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddNewRoute } from './add-new-route';

describe('AddNewRoute', () => {
  let component: AddNewRoute;
  let fixture: ComponentFixture<AddNewRoute>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AddNewRoute]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddNewRoute);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
