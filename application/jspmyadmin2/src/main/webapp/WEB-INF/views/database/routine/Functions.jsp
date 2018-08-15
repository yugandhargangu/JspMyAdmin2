<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="group">
    <div class="group-widget group-content">
        <div class="page-head">
            <h2><m:print key="lbl.functions"/></h2>
            <button type="button" class="btn btn-right" ng-click="runClick()">+ <m:print key="lbl.add_function"/></button>
        </div>
        <hr class="thin">
        <table class="tbl tbl-full">
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="allCheck" ng-change="checkAll()"></th>
                <th><m:print key="lbl.name"/></th>
                <th><m:print key="lbl.routine_body"/></th>
                <th><m:print key="lbl.returns"/></th>
                <th><m:print key="lbl.is_deterministic"/></th>
                <th><m:print key="lbl.sql_data_access"/></th>
                <th><m:print key="lbl.server_type"/></th>
                <th><m:print key="lbl.definer"/></th>
                <th><m:print key="lbl.comment"/></th>
            </tr>
            </thead>
            <tfoot ng-show="page_data.routine_info_list.length > 0">
            <tr>
                <th></th>
                <th><m:print key="lbl.total"/>: {{page_data.routine_info_list.length}}</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </tfoot>
            <tbody ng-show="page_data.routine_info_list.length > 0">
            <tr ng-repeat="routine_info in page_data.routine_info_list"
                ng-class="{true:'even',false:'odd'}[$index % 2 === 0]">
                <td><input type="checkbox" ng-true-value="'{{routine_info.name}}'" ng-false-value="''"
                           ng-model="temp_routines[$index]" ng-change="checkOne(routine_info.name)"></td>
                <td>{{routine_info.name}}</td>
                <td>{{routine_info.routine_body}}</td>
                <td>{{routine_info.returns}}</td>
                <td>{{routine_info.deterministic}}</td>
                <td>{{routine_info.data_access}}</td>
                <td>{{routine_info.security_type}}</td>
                <td>{{routine_info.definer}}</td>
                <td>{{routine_info.comments}}</td>
            </tr>
            </tbody>
            <tbody ng-show="page_data.routine_info_list.length === 0">
            <tr class="even">
                <td colspan="9"><m:print key="msg.no_function_found"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-disabled="routines.length === 0"><m:print key="lbl.execute"/></button>
        <button type="button" class="btn" ng-disabled="routines.length !== 1"><m:print key="lbl.edit"/></button>
        <button type="button" class="btn" ng-click="showCreateClick()" ng-disabled="routines.length === 0"><m:print
                key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="dropClick()" ng-disabled="routines.length === 0"><m:print
                key="lbl.drop"/></button>
    </div>
</div>
<div class="group" ng-show="showCreate">
    <div class="close">&#10005;</div>
    <div class="group-widget group-header"><m:print key="lbl.create_statement"/></div>
    <div class="group-widget group-content">
        <div style="width: 100%;">
            <textarea rows="15" cols="100" style="width: 100%;" id="show-create-area"></textarea>
        </div>
    </div>
    <span id="show-create-span"></span>
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