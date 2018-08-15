import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CharsetsComponent } from './charsets.component';

describe('CharsetsComponent', () => {
  let component: CharsetsComponent;
  let fixture: ComponentFixture<CharsetsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CharsetsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CharsetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
