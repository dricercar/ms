import { TestBed } from '@angular/core/testing';

import { ScratchService } from './scratch.service';

describe('ScratchService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScratchService = TestBed.get(ScratchService);
    expect(service).toBeTruthy();
  });
});
