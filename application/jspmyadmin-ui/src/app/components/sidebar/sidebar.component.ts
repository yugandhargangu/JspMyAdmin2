import { Component } from '@angular/core';
import { DialogBoxService } from '../../utils/dialog-box.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  constructor(public dialogBox: DialogBoxService) { }

  confirmPositiveClick() {
    if (this.dialogBox.confirmObj.positiveClick != null) {
      this.dialogBox.confirmObj.positiveClick();
    }
    this.dialogBox.closeConfirm();
  }

  confirmNegativeClick() {
    if (this.dialogBox.confirmObj.negativeClick != null) {
      this.dialogBox.confirmObj.negativeClick();
    }
    this.dialogBox.closeConfirm();
  }
}
