import { Component, OnInit } from '@angular/core';

import { GethStatus } from './geth-status';
import { GethStatusService } from '../geth-status.service';

@Component({
  selector: 'app-geth-status',
  templateUrl: './geth-status.component.html',
  styleUrls: ['./geth-status.component.css'],
  providers: [GethStatusService]
})
export class GethStatusComponent implements OnInit {

  gethStatus: GethStatus;

  constructor(private gethStatusService: GethStatusService) { }

  ngOnInit() {
    this.loadGethStatus();
  }

  private loadGethStatus() {
    this.gethStatusService.getGethStatus().then(gethStatus => this.gethStatus = gethStatus);
  }
}
