import { TestBed, inject } from '@angular/core/testing';

import { StorageUtilsService } from './storage-utils.service';

describe('StorageUtilsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [StorageUtilsService]
    });
  });

  it('should be created', inject([StorageUtilsService], (service: StorageUtilsService) => {
    expect(service).toBeTruthy();
  }));
});
