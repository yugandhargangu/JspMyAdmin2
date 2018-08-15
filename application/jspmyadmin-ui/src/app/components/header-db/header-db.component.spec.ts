import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderDbComponent } from './header-db.component';

describe('HeaderDbComponent', () => {
  let component: HeaderDbComponent;
  let fixture: ComponentFixture<HeaderDbComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderDbComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderDbComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
