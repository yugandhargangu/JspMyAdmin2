import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { HomeInfo } from '../models';

@Injectable({
  providedIn: 'root'
})
export class InfoService {

  constructor(private http: HttpClient) { }

  getServerInfo() {
    return this.http.get<HomeInfo>(`${environment.apiUrl}/home.jma`);
  }
}
