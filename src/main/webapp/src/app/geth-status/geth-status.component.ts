import { Component, OnInit } from '@angular/core';

import { GethStatus } from './geth-status';
import { GethStatusService } from '../geth-status.service';
import { GeoInfo } from './geo-info';

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

  getPrettyGeoLocation(geoInfo: GeoInfo): string {
    if (geoInfo == null) {
      return 'Unknown';
    }
    return `${geoInfo.countryName}/${geoInfo.regionName}`;
  }

  getFlagUrl(countryCode: string): string {
    return `/assets/svg/${countryCode.toLowerCase()}.svg`;
  }
}
