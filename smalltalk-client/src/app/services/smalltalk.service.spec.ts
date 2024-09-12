import { TestBed } from '@angular/core/testing';

import { SmalltalkService } from './smalltalk.service';

describe('SmalltalkService', () => {
  let service: SmalltalkService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SmalltalkService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
