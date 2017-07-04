import { GethStatus } from './geth-status/geth-status'
import * as moment from 'moment';

export const GETH_STATUS: GethStatus = {
  publicIp: 'Unknown',
  clientVersion: 'Geth/v1.6.6-stable-10a45cb5/linux-amd64/go1.8.3',
  protocolVersion: 10001,
  listening: true,
  peerCount: 3,
  syncing: 'false',
  blockNumber: 3964606,
  gasPrice: 20000000000,
  peers: [],
  lastUpdated: moment(),
  diskStats: {
    usedGB: '123',
    totalGB: '321'
  }
}
