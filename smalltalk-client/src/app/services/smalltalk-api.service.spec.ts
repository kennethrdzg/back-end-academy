import { TestBed } from '@angular/core/testing';

import { SmalltalkApiService } from './smalltalk-api.service';

describe('SmalltalkApiService', () => {
  let service: SmalltalkApiService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SmalltalkApiService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
