<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="page-head">
    <h3><m:print key="lbl.views"/></h3>
</div>
<div class="group">
    <div class="group-widget group-header"><m:print key="lbl.create_view"/></div>
    <div class="group-widget group-content">
        <div class="form-input">
            <label><m:print key="lbl.view_name"/></label>
            <input type="text" class="form-control" ng-model="create_view.view_name">
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="createViewBtnClick()"><m:print key="lbl.run"/></button>
    </div>
</div>
<div class="group">
    <div class="group-widget group-header"><m:print key="lbl.view_list"/></div>
    <div class="group-widget group-content">
        <table class="tbl">
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="allCheck" ng-change="checkAll()"></th>
                <th><m:print key="lbl.view_name"/></th>
                <th><m:print key="lbl.type"/></th>
                <th><m:print key="lbl.comment"/></th>
            </tr>
            </thead>
            <tfoot ng-show="viewCount > 0">
            <tr>
                <th></th>
                <th><m:print key="lbl.total"/>: {{page_data.footer_info.name}}</th>
                <th></th>
                <th></th>
            </tr>
            </tfoot>
            <tbody ng-show="viewCount > 0">
            <tr ng-repeat="view_info in page_data.table_list">
                <td><input type="checkbox" ng-true-value="'{{view_info.name}}'" ng-false-value="''"
                           ng-model="temp_views[$index]" ng-change="checkOne(view_info.name)"></td>
                <td>{{view_info.name}}</td>
                <td>{{view_info.type}}</td>
                <td>{{view_info.comment}}</td>
            </tr>
            </tbody>
            <tbody ng-show="viewCount === 0">
            <tr class="even">
                <td colspan="4"><m:print key="msg.no_view_found"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="showGroup(2)" ng-disabled="views.length === 0"><m:print
                key="lbl.prefix"/></button>
        <button type="button" class="btn" ng-click="showGroup(3)" ng-disabled="views.length === 0"><m:print
                key="lbl.suffix"/></button>
        <button type="button" class="btn" ng-click="showCreateClick()" ng-disabled="views.length === 0"><m:print
                key="lbl.show_create"/></button>
        <button type="button" class="btn" id="btn-edit" ng-disabled="views.length === 0"><m:print
                key="lbl.alter"/></button>
        <button type="button" class="btn" ng-click="dropClick()" ng-disabled="views.length === 0"><m:print
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
        <button type="button" class="btn" ng-click="prefixClick()" ng-disabled="views.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 3">
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
        <button type="button" class="btn" ng-click="suffixClick()" ng-disabled="views.length === 0"><m:print
                key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="groupId === 4">
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
        <button type="button" class="btn" id="btn-export-go" value="${pageContext.request.contextPath}/"><m:print
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
<div style="display: none;">
    <form action="${pageContext.request.contextPath}/database_ext_sql.html" method="post" id="sql-form">
        <input type="hidden" name="request_db" value="${requestScope.command.request_db}">
        <input type="hidden" name="token" class="server-token" value="${requestScope.command.token}">
        <input type="hidden" name="edit_type" value="2"> <input type="hidden" name="edit_name">
    </form>
</div>