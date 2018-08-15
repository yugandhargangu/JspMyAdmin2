import { Injectable } from '@angular/core';

export enum DialogType {
  SUCCESS, ERROR
}

@Injectable({
  providedIn: 'root'
})
export class DialogBoxService {
  private _showLoading = true;
  private _showConfirm = false;
  private _alertMsg: string = null;
  private _alertClass = '';
  private _confirmObj: {
    title: string,
    message: string,
    positiveText: string,
    negativeText: string,
    positiveClick: any
    negativeClick?: any
  };
  constructor() {
  }

  get showLoading(): boolean {
    return this._showLoading;
  }

  set showLoading(value: boolean) {
    this._showLoading = value;
  }

  get showConfirm(): boolean {
    return this._showConfirm;
  }

  set showConfirm(value: boolean) {
    this._showConfirm = value;
  }

  get alertMsg(): string {
    return this._alertMsg;
  }

  get alertClass(): string {
    return this._alertClass;
  }

  get confirmObj(): {
    title: string,
    message: string,
    positiveText: string,
    negativeText: string,
    positiveClick: any
    negativeClick?: any
  } {
    return this._confirmObj;
  }

  closeAlert(): void {
    this._alertMsg = null;
  }

  closeConfirm(): void {
    this.showConfirm = false;
  }

  alert(type: DialogType, msg: string) {
    switch (type) {
      case DialogType.ERROR:
        this._alertClass = 'dialog-error';
        break;
      case DialogType.SUCCESS:
        this._alertClass = 'dialog-success';
        break;
      default:
        this._alertClass = '';
        break;
    }
    this._alertMsg = msg;
    this._showLoading = false;
  }

  confirm(handle: {
    title: string,
    message: string,
    positiveText: string,
    negativeText: string,
    positiveClick: any
    negativeClick?: any
  }) {
    this._confirmObj = handle;
    this.showConfirm = true;
  }
}
