<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<%@ taglib prefix="jma" uri="http://jspmyadmin.com/taglib/jsp/jma"%>
<m:open />
<div class="page-head">
    <h3><m:print key="lbl.charset_list" /></h3>
</div>
<div class="group-widget group-content">
	<table class="tbl tbl-full">
	    <thead>
			<tr>
			    <th class="link" ng-repeat="columnName in page_data.columns" ng-click="loadCharsets(columnName)">
			        {{columnName}}
				</th>
			</tr>
		</thead>
		<tfoot>
			<tr>
			    <th class="link" ng-repeat="columnName in page_data.columns" ng-click="loadCharsets(columnName)">
			        {{columnName}}
				</th>
			</tr>
		</tfoot>
		<tbody ng-repeat="(charset,collations) in page_data.charsets">
            <tr>
                <th colspan="7">{{charset}}</th>
            </tr>
			<tr ng-repeat="collationInfo in collations" ng-class="{true:'even', false:'odd'}[$index % 2 === 0]">
				<td>{{collationInfo[0]}}</td>
				<td>{{collationInfo[1]}}</td>
				<td>{{collationInfo[2]}}</td>
				<td>{{collationInfo[3]}}</td>
				<td>{{collationInfo[4]}}</td>
				<td>{{collationInfo[5]}}</td>
				<td>{{collationInfo[6]}}</td>
			</tr>
		</tbody>
		<tbody ng-show="page_data.charsets.length === 0">
		    <tr class="even">
				<td colspan="7"><m:print key="msg.no_collation_found" /></td>
			</tr>
		</tbody>
	</table>
</div>