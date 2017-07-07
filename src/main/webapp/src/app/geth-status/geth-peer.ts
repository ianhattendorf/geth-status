import {GethPeerNetwork} from './geth-peer-network'

export class GethPeer {
  id: string
  name: string
  caps: string[]
  network: GethPeerNetwork
}
