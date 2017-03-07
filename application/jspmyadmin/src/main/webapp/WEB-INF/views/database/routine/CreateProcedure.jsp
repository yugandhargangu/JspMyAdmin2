<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="page-head">
    <h3><m:print key="lbl.create_procedure"/></h3>
</div>
<div class="group">
    <div class="group-widget group-header"><m:print key="lbl.procedure_structure"/></div>
    <div class="group-widget group-content">
        <div style="display: inline-block; margin-right: 3em; width: 42%;">
            <div style="display: block;">
                <div class="form-input" style="width: 300px;">
                    <label>Procedure Name</label>
                    <input type="text" ng-model="input_data.name" class="form-control">
                </div>
                <div class="form-input">
                    <label><m:print key="lbl.comment"/></label>
                    <input type="text" ng-model="input_data.comment" class="form-control">
                </div>
            </div>
            <div style="display: block;">
                <div class="form-input"
                     style="display: inline-block; width: 300px;">
                    <label><m:print key="lbl.definer"/></label>
                    <select ng-model="input_data.definer" class="form-control" ng-init="input_data.definer = ''">
                        <option value=""></option>
                        <option ng-repeat="definerVal in ::page_data.definer_list" value="{{definerVal}}">
                            {{definerVal}}
                        </option>
                    </select>
                </div>
                <div class="form-input" style="display: inline-block;" ng-show="input_data.definer === 'OTHER'">
                    <label><m:print key="lbl.definer_name"/></label>
                    <input type="text" ng-model="input_data.definer_name" class="form-control">
                </div>
            </div>
            <div>
                <div class="form-input" style="width: 300px;">
                    <label><m:print key="lbl.sql_type"/></label>
                    <select ng-model="input_data.sql_type" class="form-control" ng-init="input_data.sql_type = ''">
                        <option value=""></option>
                        <option ng-repeat="securityVal in ::page_data.sql_type_list" value="{{securityVal}}">
                            {{securityVal}}
                        </option>
                    </select>
                </div>
                <div class="form-input">
                    <label><m:print key="lbl.security_type"/></label>
                    <select ng-model="input_data.sql_security" class="form-control"
                            ng-init="input_data.sql_security == ''">
                        <option value=""></option>
                        <option ng-repeat="checkVal in ::page_data.security_type_list" value="{{checkVal}}">
                            {{checkVal}}
                        </option>
                    </select>
                </div>
            </div>
            <div>
                <div class="form-input" style="width: 300px;">
                    <label><input type="checkbox" ng-model="input_data.lang_sql" ng-true-value="'LANGUAGE SQL'"
                                  ng-false-value="''"> LANGUAGE SQL</label>
                </div>
                <div class="form-input">
                    <label><m:print key="lbl.deterministic"/></label>
                    <select ng-model="input_data.deterministic" class="form-control"
                            ng-init="input_data.deterministic = ''">
                        <option value=""></option>
                        <option value="DETERMINISTIC">DETERMINISTIC</option>
                        <option value="NOT DETERMINISTIC">NOT DETERMINISTIC</option>
                    </select>
                </div>
            </div>
            <div class="form-input" style="display: block; width: 100%;">
                <label><m:print key="lbl.procedure_body"/></label>
                <textarea rows="15" cols="100" style="width: 100%;" id="definition-area">SELECT ;</textarea>
            </div>
        </div>
        <div style="display: inline-block; vertical-align: top;">
            <div class="form-input" id="columns-div" style="display: block;">
                <div style="display: block;">
                    <label style="display: inline-block;"><m:print key="lbl.parameters"/></label>
                </div>
                <table style="width: 100%;" class="tbl tbl-full">
                    <thead>
                    <tr>
                        <th><m:print key="lbl.type"/></th>
                        <th><m:print key="lbl.name"/></th>
                        <th><m:print key="lbl.datatype"/></th>
                        <th><m:print key="lbl.length"/></th>
                        <th>
                            <button type="button" class="btn" ng-click="addRow()"><m:print key="lbl.add"/></button>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr ng-repeat="i in row_count" ng-init="$rowIndex = $index">
                        <td class="no-display">
                            <select ng-model="input_data.param_types[$rowIndex]"
                                    ng-init="input_data.param_types[$rowIndex] = 'IN'">
                                <option value="IN">IN</option>
                                <option value="OUT">OUT</option>
                                <option value="INOUT">INOUT</option>
                            </select>
                        </td>
                        <td><input type="text" ng-model="input_data.params[$rowIndex]"
                                   ng-init="input_data.params[$rowIndex] = ''"></td>
                        <td>
                            <select ng-model="input_data.param_data_types[$rowIndex]"
                                    ng-init="input_data.param_data_types[$rowIndex] = '1'">
                                <optgroup ng-repeat="datatype_info in ::page_data.data_types"
                                          label="{{datatype_info.key}}">
                                    <option ng-repeat="datatype in ::datatype_info.value" value="{{datatype.id}}">
                                        {{datatype.datatype}}
                                    </option>
                                </optgroup>
                            </select>
                        </td>
                        <td><input type="text" ng-model="input_data.lengths[$rowIndex]"
                                   ng-init="input_data.lengths[$rowIndex] = ''"></td>
                        <td style="text-align: center;">
                            <img alt="" class="icon" ng-click="removeRow(i)"
                                 src="${pageContext.request.contextPath}/components/icons/minus-r.png">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>
    <div class="group-widget group-footer">
        <button type="button" class="btn" ng-click="showCreate()"><m:print key="lbl.show_create"/></button>
        <button type="button" class="btn" ng-click="runClick()"><m:print key="lbl.run"/></button>
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