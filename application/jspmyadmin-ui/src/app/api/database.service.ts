import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Databases, Database } from '../models/databases.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DatabaseService {

  constructor(private http: HttpClient) { }

  getDatabases() {
    return this.http.get<Databases>(`${environment.apiUrl}/server/databases.jma`);
  }

  createDatabase(db: Database) {
    return this.http.post<any>(`${environment.apiUrl}/server/database/create.jma`, db);
  }

  dropDatabases(databases: string[]) {
    return this.http.post<any>(`${environment.apiUrl}/server/database/drop.jma`, {databases: databases});
  }
}
