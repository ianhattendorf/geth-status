import { Injectable } from '@angular/core';

import * as moment from 'moment';
import * as webstomp from 'webstomp-client';

import { GethStatus } from './geth-status/geth-status';
import { environment } from '../environments/environment';

@Injectable()
export class GethStatusService {

  private contextRoot = ''; // TODO fetch somehow?
  private gethStatus: GethStatus = new GethStatus();
  private firstMessageReceived = false;

  constructor() {
    this.stompConnect();
  }

  getGethStatus(): Promise<GethStatus> {
    return Promise.resolve(this.gethStatus);
  }

  private stompConnect(): void {
    if (!this.firstMessageReceived) {
      this.updateLoadingHtml('Connecting to server...');
    }

    const host = environment.windowLocationHost
      ? environment.windowLocationHost
      : window.location.host;
    const protocol = window.location.protocol === 'https:' ? 'wss:' : 'ws:';
    const wsUrl = `${protocol}//${host}${this.contextRoot}/ws`;
    console.log('wsUrl: ', wsUrl);

    const client = webstomp.client(wsUrl);

    const connectionHeaders: webstomp.ConnectionHeaders = {
      login: undefined,
      passcode: undefined
    };

    client.connect(connectionHeaders, () => {
      if (!this.firstMessageReceived) {
        this.updateLoadingHtml('Waiting for server response...');
      }

      client.subscribe('/topic/status', this.onStatusMessageReceived.bind(this));
      client.subscribe('/user/queue/init', this.onStatusMessageReceived.bind(this));
      client.send('/app/init');
    }, this.onStompError.bind(this));
  }

  private onStatusMessageReceived(message): void {
    const data = JSON.parse(message.body)
    console.log('received: ', message, data)
    if (!this.firstMessageReceived) {
      this.firstMessageReceived = true;
      this.finishLoading();
    }

    this.gethStatus.clientVersion = data.clientVersion;
    this.gethStatus.publicIp = data.publicIp;
    this.gethStatus.geoInfo = data.geoInfo;
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

  private onStompError(error): void {
    console.log('STOMP: ' + error);
    setTimeout(this.stompConnect.bind(this), 10000);
    console.log('STOMP: Reconnecting in 10 seconds...');
  }

  /**
   * Update loading html with new text. Extremely simple regex replace, doesn't parse html or do any verification.
   * @param newText New text to display on loading screen.
   */
  private updateLoadingHtml(newText: string): void {
    console.log('Loading status: ', newText);
    const win: any = window;
    if (win && win.loading_screen) {
      const oldHtml: string = win.loading_screen.options.loadingHtml;
      const newHtml: string = oldHtml.replace(/(<p class='loading-message'>).*(<\/p>)/, `$1${newText}$2`);
      win.loading_screen.updateLoadingHtml(newHtml, true);
    }
  }

  private finishLoading(): void {
    console.log('Finished loading.');
    const win: any = window;
    if (win && win.loading_screen) {
      win.loading_screen.finish();
    }
  }
}
