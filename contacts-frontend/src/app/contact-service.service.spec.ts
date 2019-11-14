import { TestBed } from '@angular/core/testing';

import { ContactServiceService } from './contact-service.service';

describe('ContactServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ContactServiceService = TestBed.get(ContactServiceService);
    expect(service).toBeTruthy();
  });
});
