import { Component, OnInit } from '@angular/core';
import { DialogBoxService } from './utils/dialog-box.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'jspmyadmin-ui';

  constructor(public dialogBox: DialogBoxService) {

  }

  ngOnInit() {
    this.dialogBox.showLoading = false;
  }
}
