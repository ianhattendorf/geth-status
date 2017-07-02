import {GethPeer} from "./geth-peer";

export class GethStatus {
  clientVersion: string;
  protocolVersion: number;
  listening: boolean;
  peerCount: number;
  syncing: string;
  blockNumber: number;
  gasPrice: number;
  peers: GethPeer[];
  lastUpdated: string;
}
