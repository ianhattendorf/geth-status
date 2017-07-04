import {GethPeer} from "./geth-peer";
import {DiskStats} from "./disk-stats";

export class GethStatus {
  clientVersion: string;
  protocolVersion: number;
  listening: boolean;
  peerCount: number;
  syncing: string;
  blockNumber: number;
  gasPrice: number;
  peers: GethPeer[];
  diskStats: DiskStats;
  lastUpdated: string;
}
