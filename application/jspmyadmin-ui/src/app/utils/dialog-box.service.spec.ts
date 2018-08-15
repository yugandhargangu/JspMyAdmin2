import { TestBed, inject } from '@angular/core/testing';

import { DialogBoxService } from './dialog-box.service';

describe('DialogBoxService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DialogBoxService]
    });
  });

  it('should be created', inject([DialogBoxService], (service: DialogBoxService) => {
    expect(service).toBeTruthy();
  }));
});
