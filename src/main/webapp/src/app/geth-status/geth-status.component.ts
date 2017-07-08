import { Component, OnInit } from '@angular/core'

import { GethStatus } from './geth-status'
import { GethStatusService } from '../geth-status.service'
import { GeoInfo } from './geo-info'
import * as moment from 'moment'

@Component({
  selector: 'app-geth-status',
  templateUrl: './geth-status.component.html',
  styleUrls: ['./geth-status.component.css'],
  providers: [GethStatusService]
})
export class GethStatusComponent implements OnInit {

  gethStatus: GethStatus

  /**
   * Pluralize words (strings) by literally adding an s whenever amount != 1
   * @param word Word (or string) to pluralize
   * @param amount Amount to consider when pluralizing
   * @returns {string}
   */
  private static naivePluralize(word: string, amount: number): string {
    return amount === 1 ? word : word + 's'
  }

  constructor(private gethStatusService: GethStatusService) { }

  ngOnInit() {
    this.loadGethStatus()
  }

  private loadGethStatus() {
    this.gethStatusService.getGethStatus().then(gethStatus => this.gethStatus = gethStatus)
  }

  getPrettyGeoLocation(geoInfo: GeoInfo): string {
    if (geoInfo == null) {
      return 'Unknown'
    }
    return `${geoInfo.countryName}/${geoInfo.regionName}`
  }

  getFlagUrl(countryCode: string): string {
    return `/assets/svg/${countryCode.toLowerCase()}.svg`
  }

  getUptime(uptime: moment.Moment): string {
    if (uptime == null) {
      return 'Unknown'
    }
    const now = moment()
    const duration = moment.duration(now.diff(uptime))
    let s = ''
    let previousAdded = false
    if (duration.years() !== 0) {
      s += duration.years() + GethStatusComponent.naivePluralize(' year', duration.years()) + ', '
      previousAdded = true
    }
    if (previousAdded || duration.months() !== 0) {
      s += duration.months() + GethStatusComponent.naivePluralize(' month', duration.months()) + ', '
      previousAdded = true
    }
    if (previousAdded || duration.days() !== 0) {
      s += duration.days() + GethStatusComponent.naivePluralize(' day', duration.days()) + ', '
      previousAdded = true
    }
    if (previousAdded || duration.hours() !== 0) {
      s += duration.hours() + GethStatusComponent.naivePluralize( ' hour', duration.hours()) + ', '
      previousAdded = true
    }
    if (previousAdded || duration.minutes() !== 0) {
      s += duration.minutes() + GethStatusComponent.naivePluralize(' minute', duration.minutes()) + ', '
      previousAdded = true
    }
    if (previousAdded || duration.seconds() !== 0) {
      s += duration.seconds() + GethStatusComponent.naivePluralize(' second', duration.seconds())
    }
    return s
  }
}
