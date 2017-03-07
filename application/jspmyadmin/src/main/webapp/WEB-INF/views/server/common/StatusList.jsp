<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div class="page-head">
	<h3><m:print key="lbl.status_variables_list" /></h3>
</div>
<div class="group-widget group-content">
	<div style="padding: 0.2em;">
		<label><m:print key="lbl.server_start_date" /> : <b>{{calculateDate(start_date)}}</b></label>
		<label style="float: right;"><m:print key="lbl.server_running_time" /> : <b>{{calculateDays(run_time)}}</b></label>
	</div>
	<hr class="thin">
	<div style="padding: 0.2em;">
		<label><m:print key="lbl.received" /> : <b>{{calculateBytes(received)}}</b></label>
		|
		<label><m:print key="lbl.sent" /> : <b>{{calculateBytes(sent)}}</b></label>
		<label style="float: right;"><m:print key="lbl.total" /> : <b>{{calculateBytes(sent + received)}}</b> </label>
	</div>
	<hr class="thin">
	<div>
		<label><m:print key="lbl.search" /> : </label>
		<input type="text" class="form-control" ng-model="search" ng-change="searchInData()">
	</div>
	<table class="tbl">
		<thead>
			<tr>
	    		<th ng-repeat="columnName in page_data.column_info">{{columnName}}</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
				<th ng-repeat="columnName in page_data.column_info">{{columnName}}</th>
			</tr>
		</tfoot>
		<tbody>
			<tr ng-repeat="variableData in variables" ng-class="{true:'even',false:'odd'}[$index % 2 === 0]">
				<td>{{variableData[0]}}</td>
				<td>{{getFormattedValue(variableData[0], variableData[1])}}</td>
			</tr>
			<tr class="even" ng-show="variables.length === 0">
				<td colspan="3"><m:print key="msg.no_status_variable_found" /></td>
			</tr>
		</tbody>
	</table>
</div>