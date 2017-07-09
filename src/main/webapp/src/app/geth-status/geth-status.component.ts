import { Component, OnInit, OnDestroy } from '@angular/core'
import * as moment from 'moment'
import { Subscription } from 'rxjs/Subscription'
import { TimerObservable } from 'rxjs/observable/TimerObservable'

import { GethStatus } from './geth-status'
import { GethStatusService } from '../geth-status.service'
import { GeoInfo } from './geo-info'

@Component({
  selector: 'app-geth-status',
  templateUrl: './geth-status.component.html',
  styleUrls: ['./geth-status.component.css'],
  providers: [GethStatusService]
})
export class GethStatusComponent implements OnInit, OnDestroy {

  gethStatus: GethStatus
  gethUptimeText: string
  uptimeSubscription: Subscription

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
    this.gethStatus = this.gethStatusService.getGethStatus()
    this.uptimeSubscription = TimerObservable.create(2000, 1000)
      .subscribe(() => this.gethUptimeText = this.getUptime(this.gethStatus.uptime))
  }

  ngOnDestroy() {
    this.uptimeSubscription.unsubscribe()
  }

  getPrettyGeoLocation(geoInfo: GeoInfo): string {
    if (geoInfo == null) {
      return 'Unknown'
    }
    if (geoInfo.regionName == null || geoInfo.regionName === '') {
      return geoInfo.countryName || 'Unknown'
    }
    if (geoInfo.countryName == null || geoInfo.countryName === '') {
      return geoInfo.regionName
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
