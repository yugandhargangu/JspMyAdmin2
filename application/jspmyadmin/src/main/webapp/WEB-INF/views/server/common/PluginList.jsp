<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div class="page-head">
    <h3><m:print key="lbl.plugin_list" />	</h3>
</div>
<div class="group-widget group-content">
	<table class="tbl" id="plugin-list">
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
			<tr ng-repeat="pluginData in page_data.data_list" ng-class="{true:'even',false:'odd'}[$index % 2 === 0]">
				<td>{{pluginData[0]}}</td>
				<td>{{pluginData[1]}}</td>
				<td>{{pluginData[2]}}</td>
				<td>{{pluginData[3]}}</td>
				<td>{{pluginData[4]}}</td>
				<td>{{pluginData[5]}}</td>
				<td>{{pluginData[6]}}</td>
				<td>{{pluginData[7]}}</td>
				<td>{{pluginData[8]}}</td>
				<td>{{pluginData[9]}}</td>
				<td>{{pluginData[10]}}</td>
			</tr>
			<tr class="even" ng-show="page_data.data_list.length === 0">
				<td colspan="11"><m:print key="msg.no_plugin_found" /></td>
			</tr>
		</tbody>
	</table>
</div>