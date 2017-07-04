import { Injectable } from '@angular/core';

import * as moment from 'moment';
import * as webstomp from 'webstomp-client';

import { GethStatus } from './geth-status/geth-status';
import { environment } from '../environments/environment';

@Injectable()
export class GethStatusService {

  private contextRoot: string = ''; // TODO fetch somehow?
  private gethStatus: GethStatus = new GethStatus();

  constructor() {
    this.stompConnect();
  }

  getGethStatus(): Promise<GethStatus> {
    return Promise.resolve(this.gethStatus);
  }

  private stompConnect(): void {
    const host = environment.windowLocationHost
      ? environment.windowLocationHost
      : window.location.host;
    const wsUrl = `ws://${host}${this.contextRoot}/ws`;
    console.log('wsUrl: ', wsUrl);

    const client = webstomp.client(wsUrl);

    const connectionHeaders: webstomp.ConnectionHeaders = {
      login: undefined,
      passcode: undefined
    };

    client.connect(connectionHeaders, () => {
      const win: any = window;
      if (win && win.loading_screen) {
        win.loading_screen.finish();
      }
      client.subscribe('/topic/status', this.onStatusMessageReceived.bind(this));
      client.subscribe('/user/queue/init', this.onStatusMessageReceived.bind(this))
      client.send('/app/init')
    }, this.onStompError.bind(this));
  }

  private onStatusMessageReceived(message): void {
    const data = JSON.parse(message.body)
    console.log("received: ", message, data)

    this.gethStatus.clientVersion = data.clientVersion;
    this.gethStatus.protocolVersion = data.protocolVersion;
    this.gethStatus.listening = data.listening;
    this.gethStatus.peerCount = data.peerCount;
    this.gethStatus.syncing = data.syncing;
    this.gethStatus.blockNumber = data.blockNumber;
    this.gethStatus.gasPrice = data.gasPrice.toLocaleString();
    this.gethStatus.diskStats = data.diskStats;
    this.gethStatus.peers = data.peers;
    this.gethStatus.lastUpdated = moment();
  }

  private onStompError(error) : void {
    console.log('STOMP: ' + error);
    setTimeout(this.stompConnect.bind(this), 10000);
    console.log('STOMP: Reconnecting in 10 seconds...');
  }
}
