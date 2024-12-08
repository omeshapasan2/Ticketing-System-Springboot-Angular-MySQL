import { TestBed } from '@angular/core/testing';

import { TicketingStatusService } from './ticketing-status.service';

describe('TicketingStatusService', () => {
  let service: TicketingStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TicketingStatusService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
