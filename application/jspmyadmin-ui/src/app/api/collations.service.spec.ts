import { TestBed, inject } from '@angular/core/testing';

import { CollationsService } from './collations.service';

describe('CollationsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CollationsService]
    });
  });

  it('should be created', inject([CollationsService], (service: CollationsService) => {
    expect(service).toBeTruthy();
  }));
});
