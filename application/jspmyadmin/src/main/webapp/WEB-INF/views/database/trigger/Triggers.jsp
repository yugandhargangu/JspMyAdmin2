<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="group">
    <div class="group-widget group-content">
        <div class="page-head">
            <h2><m:print key="lbl.triggers"/></h2>
            <button type="button" class="btn btn-right" ng-click="runClick()">+ <m:print key="lbl.add_trigger"/></button>
        </div>
        <hr class="thin">
        <table class="tbl tbl-full">
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="allCheck" ng-change="checkAll()"></th>
                <th><m:print key="lbl.trigger_name"/></th>
                <th><m:print key="lbl.event_type"/></th>
                <th><m:print key="lbl.table_name"/></th>
                <th><m:print key="lbl.event_time"/></th>
                <th><m:print key="lbl.definer"/></th>
            </tr>
            </thead>
            <tfoot>
            <tr ng-show="page_data.trigger_list.length > 0">
                <th></th>
                <th><m:print key="lbl.total"/>: {{page_data.trigger_list.length}}</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            </tfoot>
            <tbody ng-show="page_data.trigger_list.length > 0">
            <tr ng-repeat="trigger_info in page_data.trigger_list"
                ng-click="{true:'even',false:'odd'}[$index % 2 === 0]">
                <td><input type="checkbox" ng-true-value="'{{trigger_info.trigger_name}}'" ng-false-value="''"
                           ng-model="temp_triggers[$index]" ng-change="checkOne(trigger_info.trigger_name)"></td>
                <td>{{trigger_info.trigger_name}}</td>
                <td>{{trigger_info.event_type}}</td>
                <td>{{trigger_info.table_name}}</td>
                <td>{{trigger_info.event_time}}</td>
                <td>{{trigger_info.definer}}</td>
            </tr>
            </tbody>
            <tbody ng-show="page_data.trigger_list.length === 0">
            <tr class="even">
                <td colspan="6"><m:print key="msg.no_trigger_found"/></td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="showCreateClick()" ng-disabled="triggers.length == 0"><m:print key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="dropClick()" ng-disabled="triggers.length == 0"><m:print key="lbl.drop"/></button>
    </div>
</div>
<div class="group" ng-show="showCreate">
    <div class="close" ng-click="showCreateClose()">&#10005;</div>
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