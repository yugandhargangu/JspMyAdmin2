<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="m" uri="http://jspmyadmin.com/taglib/jsp/messages" %>
<m:open/>
<div class="page-head">
    <h3><m:print key="lbl.create_table"/></h3>
</div>
<div class="group">
    <div class="group-widget group-header"><m:print key="lbl.table_structure"/></div>
    <div class="group-widget group-content">
        <div style="display: block;">
            <div class="form-input">
                <label><m:print key="lbl.table_name"/></label>
                <input type="text" ng-model="input_data.table_name" class="form-control">
            </div>
            <div class="form-input">
                <label><m:print key="lbl.collation"/></label>
                <select ng-model="input_data.collation" class="form-control" ng-init="input_data.collation = ''">
                    <option value=""><m:print key="lbl.default"/></option>
                    <optgroup ng-repeat="(charset, collationList) in ::page_data.collation_map" label="{{charset}}">
                        <option ng-repeat="collation in ::collationList" value="{{collation}}">{{collation}}</option>
                    </optgroup>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.storage_engine"/></label>
                <select class="form-control" ng-init="input_data.engine = page_data.engine_list[0]"
                        ng-model="input_data.engine">
                    <option ng-repeat="engine in ::page_data.engine_list" value="{{engine}}">{{engine}}</option>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.comment"/></label>
                <input type="text" class="form-control" style="width: 350px;" ng-model="input_data.comment">
            </div>
        </div>

        <table class="tbl tbl-full">
            <thead>
            <tr>
                <th></th>
                <th><m:print key="lbl.column_name"/></th>
                <th><m:print key="lbl.datatype"/></th>
                <th><m:print key="lbl.length_value"/></th>
                <th><m:print key="lbl.default"/></th>
                <th><m:print key="lbl.collation"/></th>
                <th>PK</th>
                <th>NN</th>
                <th>UQ</th>
                <th>BIN</th>
                <th>UN</th>
                <th>ZF</th>
                <th>AI</th>
                <th><m:print key="lbl.comment"/></th>
            </tr>
            </thead>
            <tfoot>
            <tr>
                <th colspan="14">
                    <span class="gap-item">PK-Primary Key</span>
                    <span class="gap-item">NN-Not Null</span>
                    <span class="gap-item">UQ-Unique</span>
                    <span class="gap-item">BIN-Binary</span>
                    <span class="gap-item">UN-Unsigned</span>
                    <span class="gap-item">ZF-Zero Fill</span>
                    <span class="gap-item">AI-Auto Increment</span></th>
            </tr>
            </tfoot>
            <tbody>
            <tr ng-repeat="i in row_count" ng-init="$rowIndex = i">
                <td ng-init="initColumn($rowIndex)">
                    <img alt="" class="icon" src="${pageContext.request.contextPath}/components/icons/minus-r.png"
                         ng-click="removeRow($rowIndex)">
                </td>
                <td><input type="text" class="form-control" style="width: 150px;"
                           ng-model="input_data.columns[$rowIndex]">
                </td>
                <td>
                    <select class="form-control" ng-model="input_data.datatypes[$rowIndex]"
                            ng-change="dataTypeChange($rowIndex)">
                        <optgroup ng-repeat="datatype_info in ::page_data.datatype_list" label="{{datatype_info.key}}">
                            <option ng-repeat="datatype in ::datatype_info.value" value="{{datatype.id}}">
                                {{datatype.datatype}}
                            </option>
                        </optgroup>
                    </select>
                </td>
                <td><input type="text" class="form-control" style="width: 70px;"
                           ng-model="input_data.lengths[$rowIndex]" ng-hide="disables.length[$rowIndex]">
                </td>
                <td><input type="text" class="form-control" ng-model="input_data.defaults[$rowIndex]"></td>
                <td>
                    <select class="form-control" ng-model="input_data.collations[$rowIndex]"
                            ng-show="disables.charset[$rowIndex]">
                        <option value=""><m:print key="lbl.default"/></option>
                        <optgroup ng-repeat="(charset, collationList) in ::page_data.collation_map" label="{{charset}}">
                            <option ng-repeat="collation in ::collationList" value="{{collation}}">{{collation}}
                            </option>
                        </optgroup>
                    </select>
                </td>
                <td><input type="checkbox" ng-model="input_data.pks[$rowIndex]" ng-true-value="'1'" ng-false-value="'0'"
                           ng-change="pkChange($rowIndex)"></td>
                <td><input type="checkbox" ng-model="input_data.nns[$rowIndex]" ng-true-value="'1'"
                           ng-false-value="'0'">
                </td>
                <td><input type="checkbox" ng-model="input_data.uqs[$rowIndex]" ng-true-value="'1'"
                           ng-false-value="'0'">
                </td>
                <td><input type="checkbox" ng-model="input_data.bins[$rowIndex]" ng-true-value="'1'"
                           ng-false-value="'0'" ng-disabled="!disables.bin[$rowIndex]">
                </td>
                <td><input type="checkbox" ng-model="input_data.uns[$rowIndex]" ng-true-value="'1'" ng-false-value="'0'"
                           ng-disabled="!disables.uns[$rowIndex]">
                </td>
                <td><input type="checkbox" ng-model="input_data.zfs[$rowIndex]" ng-true-value="'1'" ng-false-value="'0'"
                           ng-disabled="!disables.zf[$rowIndex]">
                </td>
                <td><input type="checkbox" ng-model="input_data.ais[$rowIndex]" ng-true-value="'1'" ng-false-value="'0'"
                           ng-change="aiChange($rowIndex)" ng-disabled="!disables.ai[$rowIndex]">
                </td>
                <td><input type="text" class="form-control" ng-model="input_data.comments[$rowIndex]"></td>
            </tr>
            </tbody>
            <tbody ng-show="row_count.length === 0">
            <td colspan="14"><m:print key="msg.no_columns_found"/></td>
            </tbody>
        </table>
        <div ng-show="page_data.partition_support">
            <div class="form-input">
                <label><m:print key="lbl.partition"/></label>
                <select ng-model="input_data.partition" class="form-control">
                    <option value=""><m:print key="lbl.select_partition"/></option>
                    <option ng-repeat="partitionValue in ::page_data.partition_list" value="{{partitionValue}}">
                        {{partitionValue}}
                    </option>
                </select>
            </div>
            <div class="form-input">
                <label><m:print key="lbl.partition_value"/></label>
                <input type="text" class="form-control" ng-model="input_data.partition_val">
            </div>
            <div class="form-input">
                <label><m:print key="lbl.partitions"/></label>
                <input type="number" class="form-control" ng-model="input_data.partitions">
            </div>
        </div>
    </div>
    <div class="group-widget group-footer">
        <select style="width: 50px;" ng-model="add_count" ng-init="add_count = '1'">
            <option ng-repeat="i in ::max_add_rows" value="{{i}}">{{i}}</option>
        </select>
        <button type="button" class="btn" ng-click="addNewColumn()" ng-disabled="row_count.length === 999">
            <m:print key="lbl.add_column"/>
        </button>
        <button type="button" class="btn" ng-click="showCreateClick()" ng-disabled="row_count.length === 0">
            <m:print key="lbl.show_create"/>
        </button>
        <button type="button" class="btn" ng-click="runClick()" ng-disabled="row_count.length === 0">
            <m:print key="lbl.run"/>
        </button>
    </div>
</div>
<div class="group">
    <div class="group-widget group-normal">
        <p style="color: red;">
            <m:print key="note.table_create"/>
        </p>
    </div>
</div>
<div class="group" ng-show="showCreate">
    <div class="close" ng-click="closeShowCreate()">&#10005;</div>
    <div class="group-widget group-header">
        <m:print key="lbl.create_statement"/>
    </div>
    <div class="group-widget group-content">
        <div style="width: 100%;">
            <textarea rows="15" cols="100" style="width: 100%;" id="show-create-area"></textarea>
        </div>
    </div>
    <span id="show-create-span"></span>
</div>