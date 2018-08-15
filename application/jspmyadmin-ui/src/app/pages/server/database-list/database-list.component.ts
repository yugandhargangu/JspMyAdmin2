import { Component, OnInit } from '@angular/core';
import { first } from 'rxjs/operators';
import { CollationsService, DatabaseService } from '../../../api';
import { DialogBoxService, ResponseObserver, DialogType } from '../../../utils';
import { Databases, Database } from '../../../models';

@Component({
  selector: 'app-database-list',
  templateUrl: './database-list.component.html',
  styleUrls: ['./database-list.component.css']
})
export class DatabaseListComponent implements OnInit {

  isPageReady = false;
  collations: any = {};
  databases: Databases;
  pageInputs = {
    database: '',
    collation: ''
  };
  selectedDbs = [];
  sortInfo = {
    column: 'database',
    type: 0
  };

  constructor(private dbService: DatabaseService, private collationsService: CollationsService,
    private dialogBox: DialogBoxService) { }

  ngOnInit() {
    this.collationsService.getAll().pipe(first()).subscribe(ResponseObserver.handle(collations => {
      this.collations = collations;
    }));
    this.dbService.getDatabases().pipe(first()).subscribe(ResponseObserver.handle(databases => {
      this.databases = databases;
      this.isPageReady = true;
    }));
  }

  createDatabase() {
    if (this.pageInputs.database.trim() === '') {
      this.dialogBox.alert(DialogType.ERROR, 'Database name is empty');
      return;
    }
    const db = new Database();
    db.database = this.pageInputs.database.trim();
    db.collation = this.pageInputs.collation;
    this.dialogBox.showLoading = true;
    this.dbService.createDatabase(db).pipe(first()).subscribe(ResponseObserver.handle(res => {
      this.dbService.getDatabases().pipe(first()).subscribe(ResponseObserver.handle(databases => {
        this.databases = databases;
        this.isPageReady = true;
        this.dialogBox.showLoading = false;
      this.dialogBox.alert(DialogType.SUCCESS, res);
      }));
    }));
  }

  dropDatabases() {
    if (this.selectedDbs.length === 0) {
      this.dialogBox.alert(DialogType.ERROR, 'Please select atleast one database.');
      return;
    }
    this.dialogBox.confirm({
      title: 'Dropping databases',
      message: 'Are you sure? You can not recover dropped databses.',
      positiveText: 'YES',
      negativeText: 'NO',
      positiveClick: () => {
        this.dialogBox.showLoading = true;
        this.dbService.dropDatabases(this.selectedDbs).pipe(first()).subscribe(ResponseObserver.handle(res => {
          this.dbService.getDatabases().pipe(first()).subscribe(ResponseObserver.handle(databases => {
            this.databases = databases;
            this.isPageReady = true;
            this.dialogBox.showLoading = false;
            this.dialogBox.alert(DialogType.SUCCESS, res);
            this.selectedDbs = [];
          }));
        }));
      }
    });
  }

  getCollationsKeys() {
    return Object.keys(this.collations);
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

  sortDatabases(column, type) {
    switch (type) {
      case 0:
        this.databases.database_list.sort((a, b) => {
          if (a[column] < b[column]) {
            return -1;
          } else if (a[column] > b[column]) {
            return 1;
          } else {
            return 0;
          }
        });
        break;
      case 1:
        this.databases.database_list.sort((a, b) => {
          if (a[column] > b[column]) {
            return -1;
          } else if (a[column] < b[column]) {
            return 1;
          } else {
            return 0;
          }
        });
        break;
    }
    this.sortInfo.column = column;
    this.sortInfo.type = type;
  }

  toggleAll() {
    const selectedDbs = [];
    if (this.databases.database_list.length !== this.selectedDbs.length) {
      for (let i = 0; i < this.databases.database_list.length; i++) {
        selectedDbs.push(this.databases.database_list[i].database);
      }
    }
    this.selectedDbs = selectedDbs;
  }

  toggleDb(database) {
    const index = this.selectedDbs.indexOf(database);
    if (index > -1) {
      this.selectedDbs.splice(index, 1);
    } else {
      this.selectedDbs.push(database);
    }
  }
}
