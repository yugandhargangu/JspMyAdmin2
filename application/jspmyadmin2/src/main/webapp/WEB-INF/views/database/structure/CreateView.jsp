<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="page-head">
    <h3><m:print key="lbl.create_view"/></h3>
</div>
<div class="group">
    <div class="group-widget group-header"><m:print key="lbl.view_structure"/></div>
    <div class="group-widget group-content">
        <div style="display: inline-block; margin-right: 3em;">
            <div class="form-input" style="display: block;">
                <div style="display: inline-block;">
                    <label><m:print key="lbl.create_type"/>: </label>
                </div>
                <div style="display: inline-block;">
                    <label><input type="radio" ng-model="input_data.create_type" value="CREATE"
                                  style="font-weight: normal;"> CREATE</label>
                </div>
                <div style="display: inline-block;">
                    <label><input type="radio" ng-model="input_data.create_type" value="CREATE OR REPLACE"
                                  style="font-weight: normal;"> CREATE OR REPLACE</label>
                </div>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.algorithm"/></label>
                <select ng-model="input_data.algorithm" class="form-control">
                    <option value=""></option>
                    <option ng-repeat="algorithm in ::page_data.algorithm_list" value="{{algorithm}}">{{algorithm}}
                    </option>
                </select>
            </div>
            <div class="form-input" style="display: block;">
                <label><m:print key="lbl.definer"/></label>
                <select ng-model="input_data.definer" class="form-control">
                    <option value=""></option>
                    <option ng-repeat="definer in ::page_data.definer_list" value="{{definer}}">{{definer}}</option>
                </select>
            </div>
            <div class="form-input" style="display: block;" ng-show="input_data.definer === 'OTHER'">
                <label><m:print key="lbl.definer_name"/></label>
                <input type="text" ng-model="input_data.definer_name" class="form-control">
            </div>
            <div class="form-input" style="display: block;">
                <label><m:print key="lbl.sql_security"/></label>
                <select ng-model="input_data.sql_security" class="form-control">
                    <option value=""></option>
                    <option ng-repeat="security in ::page_data.security_list" value="{{security}}">{{security}}</option>
                </select>
            </div>
            <div class="form-input" style="display: block;">
                <label><m:print key="lbl.view_name"/></label>
                <input type="text" ng-model="input_data.view_name" class="form-control">
            </div>
            <div class="form-input" style="display: block;">
                <label><m:print key="lbl.check_option"/></label>
                <select ng-model="input_data.sql_security" class="form-control">
                    <option value=""></option>
                    <option ng-repeat="checkVal in ::page_data.check_list" value="{{checkVal}}">{{checkVal}}</option>
                </select>
            </div>
        </div>
        <div style="display: inline-block; vertical-align: top; margin-right: 2em;">
            <div class="form-input" style="display: block;">
                <div style="display: block;">
                    <label style="display: inline-block;"><m:print key="lbl.columns"/>
                        <button type="button" class="btn" ng-click="addColumn()"><m:print key="lbl.add"/></button>
                    </label>
                </div>
                <div style="padding-top: 5px;" ng-repeat="i in column_count" ng-init="initColumn(i)">
                    <img alt="" class="icon" ng-click="removeColumn(i)"
                         src="${pageContext.request.contextPath}/components/icons/minus-r.png">
                    <input type="text" ng-model="input_data.column_list[i]" class="form-control">
                </div>
            </div>
        </div>
        <div style="display: inline-block; vertical-align: top; width: 40%;">
            <div class="form-input" style="display: block;">
                <label><m:print key="lbl.view_definition"/></label>
                <textarea rows="15" cols="100" style="width: 100%;" id="definition-area">SELECT　</textarea>
            </div>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="showCreateClick()"><m:print key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="goClick()"><m:print key="lbl.run"/></button>
    </div>
</div>
<div class="group">
    <div class="group-widget group-normal">
        <p style="color: red;">
            <m:print key="note.view_create"/>
        </p>
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