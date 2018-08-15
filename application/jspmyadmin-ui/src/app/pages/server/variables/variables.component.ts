import { Component, OnInit } from '@angular/core';
import { DialogBoxService, ResponseObserver, DialogType } from '../../../utils';
import { ServerService } from '../../../api';
import { Variable } from '../../../models';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-variables',
  templateUrl: './variables.component.html',
  styleUrls: ['./variables.component.css']
})
export class VariablesComponent implements OnInit {

  all_variables: string[][];
  isPageReady = false;
  variables: string[][];
  variable: Variable;
  scopes: string[];
  search = '';
  selectedIndex = -1;

  constructor(private dialogBox: DialogBoxService, private serverService: ServerService) { }

  ngOnInit() {
    this.dialogBox.showLoading = true;
    this.serverService.getVariables().pipe(first()).subscribe(ResponseObserver.handle(variables => {
      this.all_variables = variables;
      this.filterVariables();
      this.isPageReady = true;
      this.dialogBox.showLoading = false;
    }));
  }

  filterVariables() {
    this.variables = [];
    for (let i = 0; i < this.all_variables.length; i++) {
      if (this.search === '' || this.all_variables[i][0].indexOf(this.search) > -1) {
        this.variables.push(this.all_variables[i]);
      }
    }
  }

  showInfoDialog(index: number) {
    this.variable = new Variable();
    this.variable.name = this.variables[index][0];
    this.scopes = this.variables[index][3].split(',');
    this.variable.scope = [this.scopes[0]];
    if (this.scopes.length === 2) {
      this.variable.value = this.variables[index][1];
    } else if (this.scopes[0] === 'SESSION') {
      this.variable.value = this.variables[index][1];
    } else if (this.scopes[0] === 'GLOBAL') {
      this.variable.value = this.variables[index][2];
    }
    this.selectedIndex = index;
  }

  closeInfoDialog() {
    this.selectedIndex = -1;
  }

  saveVariable() {
    this.dialogBox.showLoading = true;
    this.serverService.saveVariable(this.variable).pipe(first()).subscribe(ResponseObserver.handle(msg => {
      this.closeInfoDialog();
      this.serverService.getVariables().pipe(first()).subscribe(ResponseObserver.handle(variables => {
        this.all_variables = variables;
        this.dialogBox.showLoading = false;
        this.dialogBox.alert(DialogType.SUCCESS, msg);
      }));
    }));
  }
}
