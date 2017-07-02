import { Injectable } from '@angular/core';

import { GethStatus } from './geth-status/geth-status';
import { GETH_STATUS } from './mock-geth-status';

@Injectable()
export class GethStatusService {

  constructor() { }

  getGethStatus(): Promise<GethStatus> {
    return Promise.resolve(GETH_STATUS);
  }
}
