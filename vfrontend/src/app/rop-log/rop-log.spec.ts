import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RopLog } from './rop-log';

describe('RopLog', () => {
  let component: RopLog;
  let fixture: ComponentFixture<RopLog>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RopLog]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RopLog);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
