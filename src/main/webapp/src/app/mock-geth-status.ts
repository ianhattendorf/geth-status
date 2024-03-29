import { GethStatus } from './geth-status/geth-status'
import * as moment from 'moment'

export const GETH_STATUS: GethStatus = {
  clientVersion: 'Geth/v1.6.6-stable-10a45cb5/linux-amd64/go1.8.3',
  publicIp: 'Unknown',
  geoInfo: {
    countryCode: 'US',
    countryName: 'United States',
    regionName: 'Arizona'
  },
  uptime: moment().subtract(1, 'hours'),
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
  },
  memoryStats: {
    usedGB: '2.4',
    totalGB: '4.0'
  }
}
