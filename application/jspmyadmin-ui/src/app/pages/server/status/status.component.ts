import { Component, OnInit, OnDestroy } from '@angular/core';
import { ServerService } from '../../../api';
import { DialogBoxService, ResponseObserver } from '../../../utils';
import { ServerStatus } from '../../../models';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit, OnDestroy {

  private bytes_dates = ['Bytes_received', 'Bytes_sent', 'Uptime', 'Uptime_since_flush_status'];
  private msgs = {
    days: 'Days',
    hours: 'Hours',
    mins: 'Mins',
    secs: 'Secs'
  };
  private months = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];
  statistics = { received: 0, sent: 0, start_date: null, run_time: null };
  isPageReady = false;
  status: ServerStatus;
  variables: string[][];
  search = '';
  private statusInterval = null;

  constructor(private serverService: ServerService, private dialogBox: DialogBoxService) { }

  ngOnInit() {
    this.dialogBox.showLoading = true;
    this.serverService.getStatus().pipe(first()).subscribe(ResponseObserver.handle(status => {
      this.status = status;
      for (let i = 0; i < this.status.data_list.length; i++) {
        const index = this.bytes_dates.indexOf(this.status.data_list[i][0]);
        if (index > -1) {
          switch (index) {
            case 0:
              this.statistics.received = parseInt(this.status.data_list[i][1], 10);
              break;
            case 1:
              this.statistics.sent = parseInt(this.status.data_list[i][1], 10);
              break;
            case 2:
              this.statistics.start_date = this.status.data_list[i][1];
              break;
            case 3:
              this.statistics.run_time = this.status.data_list[i][1];
              break;
          }
        }
      }
      this.filterVariables();
      this.isPageReady = true;
      this.dialogBox.showLoading = false;
      this.statusInterval = setInterval(() => {
        const start_date = this.statistics.start_date;
        this.statistics.start_date = null;
        this.statistics.start_date = start_date;
        this.statistics.run_time++;
      }, 1000);
    }));
  }

  ngOnDestroy() {
    if (this.statusInterval != null) {
      clearInterval(this.statusInterval);
    }
  }

  filterVariables() {
    this.variables = [];
    for (let i = 0; i < this.status.data_list.length; i++) {
      if (this.search === '' || this.status.data_list[i][0].indexOf(this.search) > -1) {
        this.variables.push(this.status.data_list[i]);
      }
    }
  }

  getFormattedValue(code, original) {
    const index = this.bytes_dates.indexOf(code);
    if (index > -1) {
      switch (index) {
        case 0:
          return this.calculateBytes(original);
        case 1:
          return this.calculateBytes(original);
        case 2:
          return this.calculateDate(original);
        case 3:
          return this.calculateDays(original);
      }
    }
    return original;
  }

  calculateBytes(bytes: number) {
    if (bytes > 1024) {
      bytes = bytes / 1024;
      if (bytes > 1024) {
        bytes = bytes / 1024;
        if (bytes > 1024) {
          bytes = bytes / 1024;
          if (bytes > 1024) {
            bytes = bytes / 1024;
            return bytes.toFixed(2) + ' TB';
          } else {
            return bytes.toFixed(2) + ' GB';
          }
        } else {
          return bytes.toFixed(2) + ' MB';
        }
      } else {
        return bytes.toFixed(2) + ' KB';
      }
    } else {
      return bytes + ' B';
    }
  }

  calculateDate(original) {
    if (!original || original == null || original === '') {
      return '';
    }
    const d = new Date(Date.now() - original);
    let date = this.months[d.getMonth()] + ' ' + d.getDate() + ', ' + d.getFullYear() + ' ';
    if (d.getHours() < 10) {
      date += '0' + d.getHours();
    } else {
      date += d.getHours();
    }
    if (d.getMinutes() < 10) {
      date += ':0' + d.getMinutes();
    } else {
      date += ':' + d.getMinutes();
    }
    if (d.getSeconds() < 10) {
      date += ':0' + d.getSeconds();
    } else {
      date += ':' + d.getSeconds();
    }
    return date;
  }

  calculateDays(original) {
    if (!original || original == null) {
      return '';
    }
    if (original > 60) {
      const sec = original % 60;
      original = (original - sec) / 60;
      if (original > 60) {
        const min = original % 60;
        original = (original - min) / 60;
        if (original > 60) {
          const hour = original % 24;
          original = (original - hour) / 24;
          return original + ' ' + this.msgs.days + ' ' + hour + ' ' + this.msgs.hours + ' '
            + min + ' ' + this.msgs.mins + ' ' + sec + ' ' + this.msgs.secs;
        } else {
          return original + ' ' + this.msgs.hours + ' ' + min + ' ' + this.msgs.mins + ' '
            + sec + ' ' + this.msgs.secs;
        }
      } else {
        return original + ' ' + this.msgs.mins + ' ' + sec + ' ' + this.msgs.secs;
      }
    } else {
      return original + ' ' + this.msgs.secs;
    }
  }
}
