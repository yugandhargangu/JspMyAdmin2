import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ServerStatus, Variable } from '../models';

@Injectable({
  providedIn: 'root'
})
export class ServerService {

  constructor(private http: HttpClient) { }

  getStatus() {
    return this.http.get<ServerStatus>(`${environment.apiUrl}/server/status.jma`);
  }

  getCharsets() {
    return this.http.get<any>(`${environment.apiUrl}/server/charsets.jma`);
  }

  getVariables() {
    return this.http.get<string[][]>(`${environment.apiUrl}/server/variables.jma`);
  }

  saveVariable(variable: Variable) {
    return this.http.post<string>(`${environment.apiUrl}/server/variable.jma`, variable);
  }
}
