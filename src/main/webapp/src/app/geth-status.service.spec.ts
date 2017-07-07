import { TestBed, inject } from '@angular/core/testing'

import { GethStatusService } from './geth-status.service'

describe('GethStatusService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [GethStatusService]
    })
  })

  it('should be created', inject([GethStatusService], (service: GethStatusService) => {
    expect(service).toBeTruthy()
  }))
})
