import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewEditRoutes } from './view-edit-routes';

describe('ViewEditRoutes', () => {
  let component: ViewEditRoutes;
  let fixture: ComponentFixture<ViewEditRoutes>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ViewEditRoutes]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewEditRoutes);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
