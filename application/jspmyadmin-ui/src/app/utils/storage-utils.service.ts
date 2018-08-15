import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageUtilsService {

  private _auth = true;

  constructor() { }

  get auth(): boolean {
    return this._auth;
  }
}
