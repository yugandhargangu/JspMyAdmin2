<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages"%>
<m:open />
<div style="padding: 0.2em 2em;">
    <div class="page-head">
		<h3><m:print key="lbl.database_list" /></h3>
	</div>
	<div class="group">
		<div class="group-widget group-header"><m:print key="lbl.create_database" /></div>
		<div class="group-widget gorup-content">
			<div class="form-input">
				<label><m:print key="lbl.database_name" /></label>
				<input type="text" class="form-control" maxlength="50" ng-model="input_data.database">
			</div>
			<div class="form-input">
				<label><m:print key="lbl.collation" /></label>
				<select class="form-control" ng-model="input_data.collation">
					<option value=""><m:print key="lbl.default_collation" /></option>
					<optgroup ng-repeat="(charsetKey, collationList) in page_data.collation_map" label="{{charsetKey}}">
						<option ng-repeat="collationName in collationList" value="{{collationName}}">{{collationName}}</option>
                    </optgroup>
				</select>
			</div>
		</div>
		<div class="group-widget group-footer">
			<button type="button" class="btn" ng-click="saveDatabase()"><m:print key="lbl.run" /></button>
		</div>
	</div>
	<div class="group">
		<div class="group-widget group-header"><m:print key="lbl.database_list" /></div>
		<div class="group-widget group-content">
			<table class="tbl tbl-full">
				<thead>
					<tr>
			    		<th><input type="checkbox" id="check-all" ng-model="allCheck" ng-change="checkAll()"></th>
						<th class="link" ng-click="loadDatabases('db_name')">
						    <m:print key="lbl.database_name" />
							<img ng-show="showIcon('db_name',' DESC')" alt="" class="icon"src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
    					    <img ng-show="showIcon('db_name',' ASC')" alt=""	class="icon" src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
						</th>
						<th class="link" ng-click="loadDatabases('db_collation')">
						    <m:print key="lbl.collation" />
						    <img ng-show="showIcon('db_collation',' DESC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
							<img ng-show="showIcon('db_collation',' ASC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
						</th>
						<th class="link" ng-click="loadDatabases('db_table_count')">
						    <m:print key="lbl.no_of_tables" />
						    <img ng-show="showIcon('db_table_count',' DESC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
							<img ng-show="showIcon('db_table_count',' ASC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
						</th>
						<th class="link" ng-click="loadDatabases('db_rows_count')">
						    <m:print key="lbl.no_of_rows" />
						    <img ng-show="showIcon('db_rows_count',' DESC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
							<img ng-show="showIcon('db_rows_count',' ASC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
						</th>
						<th class="link" ng-click="loadDatabases('db_data')">
						    <m:print key="lbl.data_size" />
						    <img ng-show="showIcon('db_data',' DESC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
							<img ng-show="showIcon('db_data',' ASC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
						</th>
						<th class="link" ng-click="loadDatabases('db_index')">
						    <m:print key="lbl.index_size" />
						    <img ng-show="showIcon('db_index',' DESC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
							<img ng-show="showIcon('db_index',' ASC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
						</th>
						<th class="link" ng-click="loadDatabases('db_total')">
						    <m:print key="lbl.total_size" />
						    <img ng-show="showIcon('db_total',' DESC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
							<img ng-show="showIcon('db_total',' ASC')" alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
						</th>
					</tr>
				</thead>
				<tfoot ng-show="page_data.count > 0">
					<tr>
						<th></th>
						<th><m:print key="lbl.total" />:{{page_data.footer_info.database}}</th>
						<th></th>
						<th>{{page_data.footer_info.tables}}</th>
						<th>{{page_data.footer_info.rows}}</th>
						<th>{{calculateBytes(page_data.footer_info.data)}}</th>
						<th>{{calculateBytes(page_data.footer_info.indexes)}}</th>
						<th>{{calculateBytes(page_data.footer_info.total)}}</th>
					</tr>
				</tfoot>
				<tbody ng-show="page_data.count > 0">
					<tr ng-repeat="(i, database_info) in page_data.database_list track by $index" ng-class="{true:'even',false:'odd'}[i % 2 === 0]">
						<td><input type="checkbox" ng-true-value="'{{database_info.database}}'" ng-false-value="''" ng-model="temp_databases[i]" ng-change="checkOne(database_info.database)"></td>
						<td>{{database_info.database}}</td>
						<td>{{database_info.collation}}</td>
						<td>{{database_info.tables}}</td>
						<td>{{database_info.rows}}</td>
						<td>{{calculateBytes(database_info.data)}}</td>
						<td>{{calculateBytes(database_info.indexes)}}</td>
						<td>{{calculateBytes(database_info.total)}}</td>
					</tr>
				</tbody>
				<tbody ng-show="page_data.count === 0">
					<tr class="even">
						<td colspan="8"><m:print key="msg.no_database_found" /></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="group-widget group-footer">
			<button type="button" class="btn" ng-click="dropDatabases()">
			    <img alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/delete-database.png">
				<m:print key="lbl.drop" />
			</button>
		</div>
	</div>
</div>