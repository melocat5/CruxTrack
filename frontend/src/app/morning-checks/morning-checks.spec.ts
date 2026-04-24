import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MorningChecks } from './morning-checks';

describe('MorningChecks', () => {
  let component: MorningChecks;
  let fixture: ComponentFixture<MorningChecks>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MorningChecks]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MorningChecks);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
