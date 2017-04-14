<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="page-head"><h3><m:print key="lbl.create_trigger"/></h3></div>
<div class="group">
    <div class="group-widget group-header"><m:print key="lbl.trigger_structure"/></div>
    <div class="group-widget group-content">
        <div>
            <div class="form-input">
                <label><m:print key="lbl.trigger_name"/> </label>
                <input type="text" ng-model="input_data.trigger_name" class="form-control">
            </div>
            <div class="form-input">
                <label><m:print key="lbl.definer"/></label>
                <select ng-model="input_data.definer" class="form-control" ng-init="input_data.definer = page_data.definer_list[0]">
                    <option value=""></option>
                    <option ng-repeat="definerVal in page_data.definer_list" value="{{definerVal}}">{{definerVal}}</option>
                </select>
            </div>
            <div class="form-input" style="display: inline-block;" ng-show="input_data.definer === 'OTHER'">
                <label><m:print key="lbl.definer_name"/></label>
                <input type="text" ng-model="input_data.definer_name" class="form-control">
            </div>
            <div class="form-input">
                <label><m:print key="lbl.trigger_time"/> </label>
                <select ng-model="input_data.trigger_time" class="form-control" ng-init="input_data.trigger_time = page_data.trigger_time_list[0]">
                    <option ng-repeat="triggerTimeVal in page_data.trigger_time_list" value="{{triggerTimeVal}}">{{triggerTimeVal}}</option>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.trigger_event"/> </label>
                <select ng-model="input_data.trigger_event" class="form-control" ng-init="input_data.trigger_event = page_data.trigger_event_list[0]">
                    <option ng-repeat="triggerEventVal in page_data.trigger_event_list" value="{{triggerEventVal}}">{{triggerEventVal}}</option>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.database_name"/> </label>
                <select ng-model="input_data.database_name" class="form-control" ng-init="input_data.database_name = page_data.database_name_list[0]" ng-change="fetchTables()">
                    <option ng-repeat="databaseVal in page_data.database_name_list" value="{{databaseVal}}">{{databaseVal}}</option>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.table_name"/> </label>
                <select ng-model="input_data.table_name" class="form-control" ng-init="input_data.table_name = page_data.table_name_list[0]">
                    <option ng-repeat="tableVal in page_data.table_name_list" value="{{tableVal}}">{{tableVal}}</option>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.trigger_order"/> </label>
                <select ng-model="input_data.trigger_order" class="form-control" ng-init="input_data.trigger_order = ''">
                    <option value=""><m:print key="lbl.select_order"/></option>
                    <option ng-repeat="triggerOrderVal in page_data.trigger_order_list" value="{{triggerOrderVal}}">{{triggerOrderVal}}</option>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.other_trigger_name"/></label>
                <select ng-model="input_data.other_trigger_name" class="form-control" ng-init="input_data.other_trigger_name = ''">
                    <option value=""><m:print key="lbl.select_trigger_name"/></option>
                    <option ng-repeat="triggerNameVal in page_data.other_trigger_name_list" value="{{triggerNameVal}}">{{triggerNameVal}}</option>
                </select>
            </div>
        </div>
        <div>
            <div class="form-input" style="display: block;">
                <label><m:print key="lbl.trigger_body"/></label>
            </div>
            <div>
                <textarea rows="15" cols="50" id="definition-area">UPDATE ;</textarea>
            </div>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="showCreateClick()"><m:print key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="runClick()"><m:print key="lbl.run"/></button>
    </div>
</div>
<div class="group" ng-show="showCreate">
    <div class="close" ng-click="closeShowCreate()">&#10005;</div>
    <div class="group-widget group-header"><m:print key="lbl.create_statement"/></div>
    <div class="group-widget group-content">
        <div style="width: 100%;">
            <textarea rows="15" cols="100" style="width: 100%;" id="show-create-area"></textarea>
        </div>
    </div>
    <span id="show-create-span"></span>
</div>