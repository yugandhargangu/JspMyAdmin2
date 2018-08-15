import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import { HomeInfo } from '../../../models';
import { InfoService, CollationsService } from '../../../api';
import { ResponseObserver } from '../../../utils';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {
  isPageReady = false;
  homeInfo: HomeInfo;
  collations: any = {};
  pageInputs: any = {
    collation: ''
  };
  constructor(private infoService: InfoService, private collationsService: CollationsService) { }

  ngOnInit() {
    this.collationsService.getAll().pipe(first()).subscribe(ResponseObserver.handle(collations => {
      this.collations = collations;
    }));
    this.infoService.getServerInfo().pipe(first()).subscribe(ResponseObserver.handle(homeInfo => {
      this.homeInfo = homeInfo;
      this.isPageReady = true;
    }));
  }

  getCollationsKeys() {
    return Object.keys(this.collations);
  }
}
