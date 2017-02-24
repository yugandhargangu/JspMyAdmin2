<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div class="page-head">
    <h3><m:print key="lbl.engine_list" />	</h3>
</div>
<div class="group-widget group-content">
	<table class="tbl tbl-full">
		<thead>
			<tr>
				<th  ng-repeat="columnName in page_data.column_info">{{columnName}}</th>
			</tr>
		</thead>
		<tfoot>
            <tr>
				<th ng-repeat="columnName in page_data.column_info">{{columnName}}</th>
			</tr>
		</tfoot>
		<tbody>
			<tr ng-repeat="engineData in page_data.data_list" ng-class="{true:'even',false:'odd'}[$index % 2 === 0]">
			    <td >{{engineData[0]}}</td>
			    <td >{{engineData[2]}}</td>
			    <td >{{engineData[3]}}</td>
			    <td >{{engineData[4]}}</td>
			    <td >{{engineData[5]}}</td>
			    <td >{{engineData[6]}}</td>
			</tr>
		    <tr class="even" ng-show="page_data.data_list.length === 0">
				<td colspan="6"><m:print key="msg.no_engine_found" /></td>
			</tr>
		</tbody>
	</table>
</div>