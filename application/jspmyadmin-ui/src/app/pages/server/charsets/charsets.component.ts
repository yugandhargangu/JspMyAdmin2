import { Component, OnInit } from '@angular/core';
import { DialogBoxService, ResponseObserver } from '../../../utils';
import { ServerService } from '../../../api';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-charsets',
  templateUrl: './charsets.component.html',
  styleUrls: ['./charsets.component.css']
})
export class CharsetsComponent implements OnInit {

  columns: string[];
  charsets: any;
  isPageReady = false;
  constructor(private dialogBox: DialogBoxService, private serverService: ServerService) { }

  ngOnInit() {
    this.dialogBox.showLoading = true;
    this.serverService.getCharsets().pipe(first()).subscribe(ResponseObserver.handle(response => {
      this.columns = response.columns;
      this.charsets = response.charsets;
      this.isPageReady = true;
      this.dialogBox.showLoading = false;
    }));
  }

  getKeys() {
    return Object.keys(this.charsets);
  }
}
