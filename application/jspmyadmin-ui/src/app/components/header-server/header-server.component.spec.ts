import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderServerComponent } from './header-server.component';

describe('HeaderServerComponent', () => {
  let component: HeaderServerComponent;
  let fixture: ComponentFixture<HeaderServerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderServerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderServerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
