<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div class="page-head">
	<h3><m:print key="lbl.variable_list" /></h3>
</div>
<div class="group-widget group-content">
	<div>
		<label><m:print key="lbl.search" /> : </label>
		<input type="text"	class="form-control" ng-model="search" ng-change="searchInData()">
	</div>
	<table class="tbl tbl-full">
		<thead>
			<tr>
				<th ng-repeat="columnName in page_data.column_info">{{columnName}}</th>
				<th>Variable Scope</th>
				<th><m:print key="lbl.action" /></th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th ng-repeat="columnName in page_data.column_info">{{columnName}}</th>
				<th>Variable Scope</th>
	    		<th><m:print key="lbl.action" /></th>
			</tr>
		</tfoot>
		<tbody>
			<tr ng-repeat="variableData in variables" ng-class="{true:'even', false:'odd'}[$index % 2 === 0]">
				<td>{{variableData[0]}}</td>
				<td>{{variableData[1]}}</td>
				<td>{{variableData[2]}}</td>
				<td>
				    <button type="button" class="btn" ng-click="showInfoDialog($index)"><m:print key="lbl.edit" /></button>
				</td>
			</tr>
			<tr class="even" ng-show="variables.count === 0">
				<td colspan="3"><m:print key="msg.no_variable_found" /></td>
			</tr>
		</tbody>
	</table>
</div>
<div class="dialog" ng-show="showInfo">
	<div class="dialog-widget dialog-normal">
		<div class="close" ng-click="closeInfoDialog()">&#10005;</div>
		<div class="dialog-header"><m:print key="lbl.update_variable" /></div>
		<div class="dialog-content">
			<div class="form-input">
			    <label>Variable Name</label>
			    <input type="text" class="form-control" ng-model="input_data.name" readonly="readonly">
			</div>
			<div class="form-input">
			    <label>Variable Value</label>
			    <input type="text" class="form-control" ng-model="input_data.value">
			</div>
			<br>
            <div class="form-input">
			    <label>Variable Scope</label>
			    <div ng-repeat="scope in scopes">
			        <input type="radio" ng-model="input_data.scope" value="{{scope}}"> {{scope}}
			    </div>
			</div>
		</div>
		<div class="dialog-footer">
        	<button type="button" class="btn" ng-click="saveVariable()"><m:print key="lbl.save"/></button>
        </div>
	</div>
</div>

