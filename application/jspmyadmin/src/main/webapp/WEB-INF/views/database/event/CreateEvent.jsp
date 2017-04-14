<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="page-head">
    <h3><m:print key="lbl.create_event"/></h3>
</div>
<div class="group">
    <div class="group-widget group-header"><m:print key="lbl.event_structure"/></div>
    <div class="group-widget group-content">
        <div>
            <div class="form-input">
                <label><m:print key="lbl.event_name"/></label>
                <input type="text" ng-model="input_data.event_name" class="form-control">
            </div>
            <div class="form-input">
                <label>
                    <input type="checkbox" ng-model="input_data.not_exists" ng-true-value="'Yes'" ng-false-value="''">
                    IF NOT EXISTS</label>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.definer"/></label>
                <select ng-model="input_data.definer" class="form-control" ng-init="input_data.definer = ''">
                <option value=""></option>
                <option ng-repeat="definerVal in ::page_data.definer_list" value="{{definerVal}}">{{definerVal}}</option>
            </select>
            </div>
            <div class="form-input" style="display: inline-block;" ng-show="input_data.definer === 'OTHER'" ng-init="input_data.definer_name = ''">
                <label><m:print key="lbl.definer_name"/></label>
                <input type="text" ng-model="input_data.definer_name" class="form-control">
            </div>
            <div class="form-input">
                <label><m:print key="lbl.comment"/></label>
                <input type="text" ng-model="input_data.comment" class="form-control">
            </div>
        </div>
        <div>
            <div class="form-input" ng-init="input_data.schedule_type = 'AT'">
                <label style="display: inline-block;"><m:print key="lbl.schedule_type"/>: </label>
                <label style="display: inline-block; font-weight: normal;">
                    <input type="radio" ng-model="input_data.schedule_type" ng-value="'AT'">
                    AT
                </label>
                <label style="display: inline-block; font-weight: normal;">
                    <input type="radio" ng-model="input_data.schedule_type" ng-value="'EVERY'">
                    EVERY
                </label>
            </div>
            <div style="border: 1px solid #ffffff;" ng-show="input_data.schedule_type == 'EVERY'">
                <div>
                    <div class="form-input"><label>EVERY</label></div>
                    <div class="form-input"><label>INTERVAL</label></div>
                    <div class="form-input">
                        <label><m:print key="lbl.interval"/></label>
                        <select class="form-control" ng-model="input_data.interval">
                            <option ng-repeat="intervalVal in page_data.interval_list" value="{{intervalVal}}">{{intervalVal}}</option>
                    </select>
                    </div>
                    <div class="form-input">
                        <label><m:print key="lbl.quantity"/></label>
                        <input type="text" ng-model="input_data.interval_quantity" class="form-control">
                    </div>
                </div>
            </div>
            <div>&nbsp;</div>
            <div style="border: 1px solid #ffffff;">
                <div style="display: inline-block; width: 45%; vertical-align: top;">
                    <div class="form-input">
                        <label ng-show="input_data.schedule_type == 'AT'">AT</label>
                        <label ng-show="input_data.schedule_type == 'EVERY'">STARTS</label>
                        <select class="form-control" ng-model="input_data.start_date_type">
                            <option value=""></option>
                            <option value="CURRENT_TIMESTAMP">CURRENT_TIMESTAMP</option>
                            <option value="OTHER">OTHER</option>
                        </select>
                    </div>
                    <div class="form-input" ng-show="input_data.start_date_type == 'OTHER'">
                        <label>TIME STAMP</label>
                        <input type="text" class="form-control" ng-model="input_data.start_date">
                    </div>
                    <div class="form-input">
                        <button type="button" class="btn" ng-click="addStartInterval()"><m:print key="lbl.add_interval"/></button>
                    </div>
                </div>
                <div style="display: inline-block;">
                    <div ng-repeat="i in intervals.start">
                        <div class="form-input"><label>+ INTERVAL</label></div>
                        <div class="form-input">
                            <label><m:print key="lbl.quantity"/></label>
                            <input type="text" ng-model="input_data.start_date_interval_quantity[i]" class="form-control">
                        </div>
                        <div class="form-input">
                            <label><m:print key="lbl.interval"/></label>
                            <select ng-model="input_data.start_date_interval" class="form-control">
                                <option ng-repeat="intervalVal in ::page_data.interval_list[i]" value="{{intervalVal}}">{{intervalVal}}</option>
                            </select>
                        </div>
                        <div style="display: inline-block;">
                            <img alt="" class="icon" ng-click="removeStartInterval(i);" src="${pageContext.request.contextPath}/components/icons/minus-r.png">
                        </div>
                    </div>
                </div>
            </div>
            <div>&nbsp;</div>
            <div style="border: 1px solid #ffffff;" ng-show="input_data.schedule_type == 'EVERY'">
                <div style="display: inline-block; width: 45%; vertical-align: top;">
                    <div class="form-input">
                        <label>ENDS</label>
                        <select class="form-control" ng-model="input_data.end_date_type">
                            <option value=""></option>
                            <option value="CURRENT_TIMESTAMP">CURRENT_TIMESTAMP</option>
                            <option value="OTHER">OTHER</option>
                        </select>
                    </div>
                    <div class="form-input" ng-show="input_data.end_date_type == 'OTHER'">
                        <label>TIME STAMP</label>
                        <input type="text" class="form-control" ng-model="input_data.end_date">
                    </div>
                    <div class="form-input">
                        <button type="button" class="btn" ng-click="addEndInterval()"><m:print key="lbl.add_interval"/></button>
                    </div>
                </div>
                <div style="display: inline-block;">
                    <div ng-repeat="i in intervals.end">
                        <div class="form-input"><label>+ INTERVAL</label></div>
                        <div class="form-input">
                            <label><m:print key="lbl.quantity"/></label>
                            <input type="text" ng-model="input_data.end_date_interval_quantity[i]" class="form-control">
                        </div>
                        <div class="form-input">
                            <label><m:print key="lbl.interval"/></label>
                            <select ng-model="input_data.end_date_interval[i]" class="form-control">
                                <option ng-repeat="intervalVal in ::page_data.interval_list" value="{{intervalVal}}">{{intervalVal}}</option>
                            </select>
                        </div>
                        <div style="display: inline-block;">
                            <img alt="" class="icon" ng-click="removeEndInterval(i);" src="${pageContext.request.contextPath}/components/icons/minus-r.png">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div style="display: inline-block; width: 45%;" ng-init="input_data.preserve = ''">
            <div class="form-input"><label>ON COMPLETION: </label></div>
            <div class="form-input">
                <label style="font-weight: normal;"><input type="radio" ng-model="input_data.preserve" ng-value="''"><m:print key="lbl.none"/></label>
            </div>
            <div class="form-input">
                <label style="font-weight: normal;"><input type="radio" ng-model="input_data.preserve" ng-value="'PRESERVE'"> PRESERVE</label>
            </div>
            <div class="form-input">
                <label style="font-weight: normal;"><input type="radio" ng-model="input_data.preserve" ng-value="'NOT PRESERVE'"> NOT PRESERVE</label>
            </div>
        </div>
        <div style="display: inline-block;" ng-init="input_data.status = ''">
            <div class="form-input"><label><m:print key="lbl.status"/>: </label></div>
            <div class="form-input">
                <label style="font-weight: normal;"><input type="radio" ng-model="input_data.status" ng-value="''"><m:print key="lbl.none"/></label>
            </div>
            <div class="form-input">
                <label style="font-weight: normal;"><input type="radio" ng-model="input_data.status" ng-value="'ENABLE'"> ENABLE</label>
            </div>
            <div class="form-input">
                <label style="font-weight: normal;"><input type="radio" ng-model="input_data.status" ng-value="'DISABLE'"> DISABLE</label>
            </div>
            <div class="form-input">
                <label style="font-weight: normal;"><input type="radio" ng-model="input_data.status" ng-value="'DISABLE ON SLAVE'"> DISABLE ON SLAVE</label>
            </div>
        </div>
        <div>
            <div class="form-input" style="display: block;">
                <label><m:print key="lbl.event_body"/></label>
            </div>
            <div>
                <textarea rows="15" cols="50" id="definition-area">SELECT ;</textarea>
            </div>
        </div>
    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="showCreateClick()"><m:print key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="runClick()"><m:print key="lbl.run"/></button>
    </div>
</div>
<div class="group">
    <div class="group-widget group-normal">
        <p style="color: red;"><m:print key="note.view_create"/></p>
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