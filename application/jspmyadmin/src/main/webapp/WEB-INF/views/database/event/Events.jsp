<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="group">
    <div class="group-widget group-content">
        <div class="page-head">
            <h2><m:print key="lbl.events"/></h2>
            <button type="button" class="btn btn-right" ng-click="addEventClick()">+ <m:print key="lbl.add_event"/></button>
        </div>
        <hr class="thin">
        <table class="tbl tbl-full">
            <thead>
            <tr>
                <th><input type="checkbox" ng-model="allCheck" ng-change="checkAll()"></th>
                <th><m:print key="lbl.event_name"/></th>
                <th><m:print key="lbl.definer"/></th>
                <th><m:print key="lbl.type"/></th>
                <th><m:print key="lbl.status"/></th>
                <th><m:print key="lbl.create_date"/></th>
                <th><m:print key="lbl.last_altered_date"/></th>
                <th><m:print key="lbl.comment"/></th>
            </tr>
            </thead>
            <tfoot>
            <tr ng-show="page_data.event_list.length > 0">
                <th></th>
                <th><m:print key="lbl.total"/>: {{page_data.event_list.length}}</th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
            </tr>
            <tr class="even" ng-show="page_data.event_list.length === 0">
                <td colspan="8"><m:print key="msg.no_event_found"/></td>
            </tr>
            </tfoot>
            <tbody ng-show="page_data.event_list.length > 0">
            <tr ng-repeat="event_info in page_data.event_list" ng-click="{true:'even',false:'odd'}[$index % 2 === 0]">
                <td><input type="checkbox" ng-true-value="'{{event_info.name}}'" ng-false-value="''"
                           ng-model="temp_events[$index]" ng-change="checkOne(event_info.name)"></td>
                <td>{{event_info.name}}</td>
                <td>{{event_info.definer}}</td>
                <td>{{event_info.type}}</td>
                <td>{{event_info.status}}</td>
                <td>{{event_info.create_date}}</td>
                <td>{{event_info.alter_date}}</td>
                <td>{{event_info.comments}}</td>
            </tr>
            </tbody>
        </table>
    </div>
    <div class="group-widget group-footer" ng-show="page_data.event_list.length > 0">
        <button type="button" class="btn" ng-click="enableClick()" ng-disabled="events.length == 0"><m:print key="lbl.enable"/></button>
        <button type="button" class="btn" ng-click="disableClick()" ng-disabled="events.length == 0"><m:print key="lbl.disable"/></button>
        <button type="button" class="btn" id="btn-edit" ng-disabled="events.length == 0"><m:print key="lbl.alter"/></button>
        <button type="button" class="btn" ng-click="renameShowClick()" ng-disabled="events.length == 0"><m:print key="lbl.rename"/></button>
        <button type="button" class="btn" ng-click="showCreateClick()" ng-disabled="events.length == 0"><m:print key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="dropClick()" ng-disabled="events.length == 0"><m:print key="lbl.drop"/></button>
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
<div class="group" ng-show="showRename">
    <div class="close" ng-click="renameShowClose()">&#10005;</div>
    <div class="group-widget group-header"><m:print key="lbl.rename_event"/></div>
    <div class="group-widget group-content">
        <div class="form-input" style="width: 300px;">
            <label><m:print key="lbl.new_event_name"/></label>
            <input type="text" ng-model="new_event" class="form-control">
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="renameClick()" ng-disabled="new_event.trim() == ''"><m:print key="lbl.run"/></button>
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