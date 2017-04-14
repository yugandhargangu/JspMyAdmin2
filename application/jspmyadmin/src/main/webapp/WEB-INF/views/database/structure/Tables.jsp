<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="group">
    <div class="group-widget group-content">
        <div class="page-head">
            <h2><m:print key="lbl.tables"/></h2>
            <button type="button" class="btn btn-right" ng-click="createTableBtnClick()">+ <m:print
                    key="lbl.add_table"/></button>
        </div>
        <hr class="thin">
        <table class="tbl tbl-full">
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="allCheck" ng-change="checkAll()"></th>
                <th class="link" ng-click="loadTables('table_name')">
                    <m:print key="lbl.table_name"/>
                    <img ng-show="showIcon('table_name',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('table_name',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('table_type')">
                    <m:print key="lbl.type"/>
                    <img ng-show="showIcon('table_type',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('table_type',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('engine')">
                    <m:print key="lbl.engine"/>
                    <img ng-show="showIcon('engine',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('engine',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('table_rows')">
                    <m:print key="lbl.no_of_rows"/>
                    <img ng-show="showIcon('table_rows',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('table_rows',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('table_collation')">
                    <m:print key="lbl.collation"/>
                    <img ng-show="showIcon('table_collation',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('table_collation',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('data_size')">
                    <m:print key="lbl.data_size"/>
                    <img ng-show="showIcon('data_size',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('data_size',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('auto_increment')">
                    <m:print key="lbl.auto_increment"/>
                    <img ng-show="showIcon('auto_increment',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('auto_increment',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('create_time')">
                    <m:print key="lbl.create_date"/>
                    <img ng-show="showIcon('create_time',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('create_time',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th class="link" ng-click="loadTables('update_time')">
                    <m:print key="lbl.update_date"/>
                    <img ng-show="showIcon('update_time',' DESC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-desc-w.png">
                    <img ng-show="showIcon('update_time',' ASC')" alt="" class="icon"
                         src="${pageContext.request.contextPath}/components/icons/sort-asc-w.png">
                </th>
                <th>
                    <m:print key="lbl.comment"/>
                </th>
            </tr>
            </thead>
            <tfoot ng-show="tableCount > 0">
            <tr>
                <th></th>
                <th><m:print key="lbl.total"/>: {{page_data.footer_info.name}}</th>
                <th></th>
                <th></th>
                <th>{{page_data.footer_info.rows}}</th>
                <th></th>
                <th>{{page_data.footer_info.size}}</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </tfoot>
            <tbody ng-show="tableCount > 0">
            <tr ng-repeat="table_info in page_data.table_list" ng-class="{true:'even', false:'odd'}[$index % 2 === 0]">
                <td><input type="checkbox" ng-true-value="'{{table_info.name}}'" ng-false-value="''"
                           ng-model="temp_tables[$index]" ng-change="checkOne(table_info.name)"></td>
                <td style="cursor: pointer;">{{table_info.name}}</td>
                <td>{{table_info.type}}</td>
                <td>{{table_info.engine}}</td>
                <td>{{table_info.rows}}</td>
                <td>{{table_info.collation}}</td>
                <td>{{calculateBytes(table_info.size)}}</td>
                <td>{{table_info.auto_inr}}</td>
                <td>{{table_info.create_date}}</td>
                <td>{{table_info.update_date}}</td>
                <td>{{table_info.comment}}</td>
            </tr>
            </tbody>
            <tbody ng-show="tableCount === 0">
            <tr class="even">
                <td colspan="11"><m:print key="msg.no_table_found"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="group-widget group-footer" ng-show="tableCount > 0">
        <button type="button" class="btn" ng-click="showGroup(5)" ng-disabled="tables.length === 0"><m:print
                key="lbl.prefix"/></button>
        <button type="button" class="btn" ng-click="showGroup(6)" ng-disabled="tables.length === 0"><m:print
                key="lbl.suffix"/></button>
        <button type="button" class="btn" ng-click="showGroup(8)" ng-disabled="tables.length === 0"><m:print
                key="lbl.copy"/></button>
        <button type="button" class="btn" ng-click="showGroup(2)" ng-disabled="tables.length === 0"><m:print
                key="lbl.duplicate"/></button>
        <button type="button" class="btn" ng-click="showCreateClick()" ng-disabled="tables.length === 0"><m:print
                key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="showGroup(4)" ng-disabled="tables.length === 0"><m:print
                key="lbl.truncate"/></button>
        <button type="button" class="btn" ng-click="showGroup(3)" ng-disabled="tables.length === 0"><m:print
                key="lbl.drop"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 1">
    <div class="close" ng-click="closeGroup()">&#10005;</div>
    <div class="group-widget group-header"><m:print key="lbl.create_statement"/></div>
    <div class="group-widget group-content">
        <div style="width: 100%;">
            <textarea rows="15" cols="100" style="width: 100%;" id="show-create-area"></textarea>
        </div>
    </div>
    <span id="show-create-span"></span>
</div>
<div class="group" ng-show="groupId === 2">
    <div class="group-widget group-header"><m:print key="lbl.duplicate"/></div>
    <div class="group-widget group-content">
        <div class="form-input">
            <label><input type="checkbox" ng-model="input_data.enable_checks" ng-true-value="'Yes'" ng-false-value="''">
                <m:print key="lbl.with_foreign_key"/></label>
        </div>
        <div class="form-input">
            <label><input type="checkbox" ng-model="input_data.drop_checks" ng-true-value="'Yes'" ng-false-value="''">
                <m:print key="lbl.with_data"/></label>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="duplicateClick()" ng-disabled="tables.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 3">
    <div class="group-widget group-header"><m:print key="lbl.drop"/></div>
    <div class="group-widget group-content">
        <div class="form-input">
            <label><input type="checkbox" ng-model="input_data.enable_checks" ng-true-value="'Yes'" ng-false-value="''">
                <m:print key="lbl.enable_foreign_key_checks"/></label>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="dropClick()" ng-disabled="tables.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 4">
    <div class="group-widget group-header"><m:print key="lbl.truncate"/></div>
    <div class="group-widget group-content">
        <div class="form-input">
            <label><input type="checkbox" ng-model="input_data.enable_checks" ng-true-value="'Yes'" ng-false-value="''">
                <m:print key="lbl.enable_foreign_key_checks"/></label>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="truncateClick()" ng-disabled="tables.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 5">
    <div class="group-widget group-header"><m:print key="lbl.prefix"/></div>
    <div class="group-widget group-content">
        <div style="display: block;">
            <div style="display: inline-block; margin-right: 2em;"><m:print key="lbl.prefix_type"/> :</div>
            <div style="display: inline-block; margin-right: 1em;">
                <label><input type="radio" class="prefix-radio" ng-model="prefix_input.type" value="add"> <m:print
                        key="lbl.add_prefix"/></label>
            </div>
            <div style="display: inline-block; margin-right: 1em;">
                <label><input type="radio" class="prefix-radio" ng-model="prefix_input.type" value="replace"> <m:print
                        key="lbl.replace_prefix"/></label>
            </div>
            <div style="display: inline-block; margin-right: 1em;">
                <label><input type="radio" class="prefix-radio" ng-model="prefix_input.type" value="remove"> <m:print
                        key="lbl.remove_prefix"/></label>
            </div>
        </div>
        <div style="display: block;">
            <div class="form-input">
                <label><m:print key="lbl.prefix"/></label>
                <input type="text" ng-model="prefix_input.prefix" class="form-control">
            </div>
            <div class="form-input" ng-show="prefix_input.type === 'replace'">
                <label><m:print key="lbl.new_prefix"/></label>
                <input type="text" ng-model="prefix_input.new_prefix" class="form-control">
            </div>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="prefixClick()" ng-disabled="tables.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 6">
    <div class="group-widget group-header"><m:print key="lbl.suffix"/></div>
    <div class="group-widget group-content">
        <div style="display: block;">
            <div style="display: inline-block; margin-right: 2em;"><m:print key="lbl.suffix_type"/> :</div>
            <div style="display: inline-block; margin-right: 1em;">
                <label><input type="radio" class="suffix-radio" ng-model="prefix_input.type" value="add"> <m:print
                        key="lbl.add_suffix"/></label>
            </div>
            <div style="display: inline-block; margin-right: 1em;">
                <label><input type="radio" class="suffix-radio" ng-model="prefix_input.type" value="replace"> <m:print
                        key="lbl.replace_suffix"/></label>
            </div>
            <div style="display: inline-block; margin-right: 1em;">
                <label><input type="radio" class="suffix-radio" ng-model="prefix_input.type" value="remove"> <m:print
                        key="lbl.remove_suffix"/></label>
            </div>
        </div>
        <div style="display: block;">
            <div class="form-input">
                <label><m:print key="lbl.suffix"/></label>
                <input type="text" ng-model="prefix_input.prefix" class="form-control">
            </div>
            <div class="form-input" ng-show="prefix_input.type === 'replace'">
                <label><m:print key="lbl.new_suffix"/></label>
                <input type="text" ng-model="prefix_input.new_prefix" class="form-control">
            </div>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="suffixClick()" ng-disabled="tables.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 7">
    <div class="group-widget group-header"><m:print key="lbl.export"/></div>
    <div class="group-widget group-content">
        <div>
            <div style="display: inline-block;">
                <label><input type="radio" name="type"> <m:print key="lbl.structure_only"/></label>
            </div>
            <div style="display: inline-block;">
                <label><input type="radio" name="type"> <m:print key="lbl.structure_and_data"/></label>
            </div>
        </div>
        <div>
            <label><input type="checkbox" name="enable_checks" value="Yes"> <m:print
                    key="lbl.enable_foreign_key_checks"/></label>
        </div>
        <div>
            <label><input type="checkbox" name="drop_checks" value="Yes"> <m:print key="lbl.add_drops"/></label>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" id="btn-export-go" ng-disabled="tables.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 8">
    <div class="group-widget group-header"><m:print key="lbl.copy_table"/></div>
    <div class="group-widget group-content">
        <div class="form-input">
            <label><m:print key="lbl.select_database"/></label>
            <select ng-model="copy_input.database_name" ng-init="copy_input.database_name = ''">
                <option value=""><m:print key="lbl.select_database"/></option>
                <option ng-repeat="database in page_data.database_list" value="{{database}}">{{database}}</option>
            </select>
        </div>
        <div class="form-input">
            <div style="display: inline-block;">
                <label><input type="radio" ng-model="copy_input.type" value=""> <m:print
                        key="lbl.structure_only"/></label>
            </div>
            <div style="display: inline-block;">
                <label><input type="radio" ng-model="copy_input.type" value="data">
                    <m:print key="lbl.structure_and_data"/>
                </label>
            </div>
        </div>
        <div class="form-input">
            <label><input type="checkbox" ng-model="copy_input.drop_checks" ng-true-value="'Yes'" ng-false-value="''">
                <m:print key="lbl.drop_if_exists"/>
            </label>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="copyClick()"
                ng-disabled="tables.length === 0 || copy_input.database_name === ''"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="dialog" ng-show="showConfirm">
    <div class="dialog-widget dialog-normal">
        <div class="close" ng-click="confirmCloseClick()">&#10005;</div>
        <div class="dialog-header"><m:print key="lbl.alert"/></div>
        <div class="dialog-content">
            <p>{{confirm_content}}</p>
        </div>
        <div class="dialog-footer">
            <button type="button" class="btn" ng-click="confirmYesBtnClick()"><m:print key="lbl.yes"/></button>
            <button type="button" class="btn" ng-click="confirmNoBtnClick()"><m:print key="lbl.no"/></button>
        </div>
    </div>
</div>