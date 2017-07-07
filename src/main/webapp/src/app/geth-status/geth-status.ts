import { Moment } from 'moment';

import {GethPeer} from './geth-peer';
import {DiskStats} from './disk-stats';
import {GeoInfo} from './geo-info';

export class GethStatus {
  clientVersion: string;
  publicIp: string;
  geoInfo: GeoInfo;
  protocolVersion: number;
  listening: boolean;
  peerCount: number;
  syncing: string;
  blockNumber: number;
  gasPrice: number;
  peers: GethPeer[];
  diskStats: DiskStats;
  lastUpdated: Moment;
}
