import { Component, OnInit } from '@angular/core';
import { GethStatus } from './geth-status';

@Component({
  selector: 'app-geth-status',
  templateUrl: './geth-status.component.html',
  styleUrls: ['./geth-status.component.css']
})
export class GethStatusComponent implements OnInit {

  status: GethStatus = {
    clientVersion: 'v1'
  };

  constructor() { }

  ngOnInit() {
  }

}
