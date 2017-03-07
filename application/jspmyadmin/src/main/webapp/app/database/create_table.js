'use strict';

JspMyAdminApp.factory('BB0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/table/create.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        },
        save: {
            method: 'POST',
            url: contextPath + '/database/table/create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('DatabaseTableCreateController', ['$rootScope', '$scope', '$state', '$stateParams', 'BB0Resource', function ($rootScope, $scope, $state, $stateParams, BB0Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 1;
    $scope.page_data = {};
    $scope.max_add_rows = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10];
    $scope.input_data = {
        request_db: JspMyAdminContext.database,
        columns: [],
        datatypes: [],
        lengths: [],
        defaults: [],
        collations: [],
        pks: [],
        nns: [],
        uqs: [],
        bins: [],
        uns: [],
        zfs: [],
        ais: [],
        comments: []
    };
    $scope.disables = {
        bin: [],
        charset: [],
        length: [],
        ai: [],
        uns: [],
        zf: []
    };
    $scope.temp_input = {};
    if (JspMyAdminContext.redirectParams.table_name) {
        $scope.input_data.table_name = JspMyAdminContext.redirectParams.table_name;
        $scope.redirectParams = {};
    }
    $scope.row_count = [0];
    $scope.add_count = 1;
    $scope.row_increment = 1;
    $scope.showCreate = false;
    $scope.codeMirrorObj = null;
    BB0Resource.query({token: JspMyAdminContext.token, request_db: JspMyAdminContext.database}, function (response) {
        if (response.status === 0) {
            $scope.page_data = response.data;
            $scope.input_data.engine = $scope.page_data.engine_list[0];
        }
    });
    $scope.addNewColumn = function () {
        var val = parseInt($scope.add_count);
        if (isNaN(val)) {
            $scope.add_count = 1;
        }
        for (var i = 0; i < $scope.add_count; i++) {
            $scope.row_count.push($scope.row_increment++);
        }
        $scope.add_count = '1';
    };
    $scope.removeRow = function (item) {
        for (var i = 0; i < $scope.row_count.length; i++) {
            if (item == $scope.row_count[i]) {
                $scope.row_count.splice(i, 1);
                break;
            }
        }
        if ($scope.row_count.length === 0) {
            $scope.row_increment = 0;
        }
    };
    $scope.dataTypeChange = function (index) {
        var datatype = $scope.findByDTOption(index);
        // handle disables
        $scope.disables.bin[index] = datatype.datatype_options.bin;
        $scope.disables.charset[index] = datatype.datatype_options.charset;
        $scope.disables.length[index] = (datatype.datatype_options.length === -1);
        $scope.disables.ai[index] = datatype.datatype_options.ai;
        $scope.disables.uns[index] = datatype.datatype_options.us;
        $scope.disables.zf[index] = datatype.datatype_options.zf;
        // handle values
        if (!$scope.disables.bin[index]) {
            $scope.input_data.bins[index] = '0';
        }
        if (!$scope.disables.charset[index]) {
            $scope.input_data.collations[index] = '';
        }
        if (!$scope.disables.length[index]) {
            $scope.input_data.lengths[index] = '';
        }
        if (!$scope.disables.ai[index]) {
            $scope.input_data.ais[index] = '0';
        }
        if (!$scope.disables.uns[index]) {
            $scope.input_data.uns[index] = '0';
        }
        if (!$scope.disables.zf[index]) {
            $scope.input_data.zfs[index] = '0';
        }
    };
    $scope.initColumn = function (item) {
        var index = -1;
        for (var i = 0; i < $scope.row_count.length; i++) {
            if (item == $scope.row_count[i]) {
                index = i;
                break;
            }
        }
        // init values
        $scope.input_data.columns[index] = '';
        $scope.input_data.datatypes[index] = '1';
        $scope.input_data.lengths[index] = '';
        $scope.input_data.defaults[index] = '';
        $scope.input_data.collations[index] = '';
        $scope.input_data.pks[index] = '0';
        $scope.input_data.nns[index] = '0';
        $scope.input_data.uqs[index] = '0';
        $scope.input_data.bins[index] = '0';
        $scope.input_data.uns[index] = '0';
        $scope.input_data.zfs[index] = '0';
        $scope.input_data.ais[index] = '0';
        $scope.input_data.comments[index] = '';

        // init checks
        $scope.disables.bin[index] = false;
        $scope.disables.charset[index] = false;
        $scope.disables.length[index] = false;
        $scope.disables.ai[index] = true;
        $scope.disables.uns[index] = true;
        $scope.disables.zf[index] = true;
    };
    $scope.pkChange = function (index) {
        var count = 0;
        for (var i = 0; i < $scope.input_data.pks.length; i++) {
            if ($scope.input_data.pks[i] === '1') {
                count++;
            }
        }
        if (count > 1) {
            DialogContext.showError($scope.page_data.msgs[0]);
            $scope.input_data.pks[index] = '0';
        } else if (count === 1) {
            $scope.input_data.nns[index] = '1';
            $scope.input_data.uqs[index] = '1';
        }
    };
    $scope.aiChange = function (index) {
        var count = 0;
        for (var i = 0; i < $scope.input_data.ais.length; i++) {
            if ($scope.input_data.ais[i] === '1') {
                count++;
            }
        }
        if (count > 1) {
            DialogContext.showError($scope.page_data.msgs[1]);
            $scope.input_data.ais[index] = '0';
        }
    };
    $scope.findByDTOption = function (index) {
        for (var i = 0; i < $scope.page_data.datatype_list.length; i++) {
            for (var j = 0; j < $scope.page_data.datatype_list[i].value.length; j++) {
                var datatype = $scope.page_data.datatype_list[i].value[j];
                if (datatype.id == $scope.input_data.datatypes[index]) {
                    return datatype;
                }
            }
        }
        return null;
    };
    $scope.closeShowCreate = function () {
        $scope.showCreate = false;
    };
    $scope.showCreateClick = function () {
        if ($scope.isValidToProceed()) {
            if ($scope.codeMirrorObj == null) {
                var id = document.getElementById('show-create-area');
                $scope.codeMirrorObj = CodeMirror.fromTextArea(id, {
                    mode: 'text/x-mysql',
                    indentWithTabs: true,
                    smartIndent: true,
                    lineNumbers: true,
                    lineWrapping: true,
                    matchBrackets: true,
                    autofocus: true,
                    extraKeys: {
                        "Ctrl-Space": "autocomplete"
                    },
                    readOnly: true
                });
            }
            $scope.input_data.token = JspMyAdminContext.token;
            $scope.input_data.request_db = JspMyAdminContext.database;
            BB0Resource.save({}, $scope.input_data, function (response) {
                if (response.status === 0 && !response.error) {
                    $scope.showCreate = true;
                    $scope.codeMirrorObj.setValue(response.data.query + ';');
                    $scope.codeMirrorObj.refresh();
                    $scope.codeMirrorObj.focus();
                }
            });
        }
    };
    $scope.runClick = function () {
        if ($scope.isValidToProceed()) {
            $scope.input_data.action = 'Yes';
            $scope.input_data.token = JspMyAdminContext.token;
            $scope.input_data.request_db = JspMyAdminContext.database;
            BB0Resource.save({}, $scope.input_data, function (response) {
                if (response.status === 0 && !response.error) {
                    $state.go('database_tables', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                }
            });
        }
    };
    $scope.isValidToProceed = function () {
        var actualCount = 0;
        for (var i = 0; i < $scope.row_count.length; i++) {
            var column = $scope.input_data.columns[i].trim();
            if (column != '') {
                actualCount++;
                if (!$scope.isDuplicateColumn($scope.input_data.columns[i])) {
                    var datatype = $scope.findByDTOption(i);
                    var isInvalid = false;
                    if (datatype.datatype_options.has_list) {
                        if (ValidationUtil.isEmpty($scope.input_data.lengths[i])) {
                            DialogContext.showError($scope.page_data.msgs[3] + column);
                            isInvalid = true;
                        } else {
                            var tempArr = $scope.input_data.lengths[i].split(',');
                            for (var j = 0; j < tempArr.length; j++) {
                                if (!ValidationUtil.isValidSqlString(tempArr[j], true)) {
                                    isInvalid = true;
                                    break;
                                }
                            }
                        }
                    } else {
                        switch (datatype.datatype_options.length) {
                            case 0:
                                if (ValidationUtil.isEmpty($scope.input_data.lengths[i])) {
                                    isInvalid = true;
                                } else if (!ValidationUtil.isInteger($scope.input_data.lengths[i])) {
                                    isInvalid = true;
                                    break;
                                }
                                break;
                            case 1:
                                if (ValidationUtil.isEmpty($scope.input_data.lengths[i])) {
                                    isInvalid = true;
                                } else {
                                    tempArr = $scope.input_data.lengths.split(",");
                                    if (tempArr == null || tempArr.length != 2) {
                                        isInvalid = true;
                                    } else if (!ValidationUtil.isInteger(tempArr[0]) || !ValidationUtil.isInteger(tempArr[1])) {
                                        isInvalid = true;
                                    }
                                }
                                break;
                            case 2:
                                if (!ValidationUtil.isEmpty($scope.input_data.lengths[i]) && !ValidationUtil.isInteger($scope.input_data.lengths[i])) {
                                    isInvalid = true;
                                }
                                break;
                            case 3:
                                if (!ValidationUtil.isEmpty($scope.input_data.lengths[i])) {
                                    tempArr = $scope.input_data.lengths[i].split(",");
                                    if (tempArr == null || tempArr.length != 2) {
                                        isInvalid = true;
                                    } else if (!ValidationUtil.isInteger(tempArr[0]) || !ValidationUtil.isInteger(tempArr[1])) {
                                        isInvalid = true;
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                        if (isInvalid) {
                            DialogContext.showError($scope.page_data.msgs[3] + column);
                            return false;
                        }
                    }
                    if (!ValidationUtil.isEmpty($scope.input_data.defaults[i]) && !ValidationUtil.isValidSqlString($scope.input_data.defaults[i], false)) {
                        DialogContext.showError($scope.page_data.msgs[4] + column);
                        return false;
                    }

                    if (!ValidationUtil.isEmpty($scope.input_data.defaults[i]) && !ValidationUtil.isValidSqlString($scope.input_data.defaults[i], false)) {
                        DialogContext.showError($scope.page_data.msgs[5] + column);
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        if (actualCount == 0) {
            DialogContext.showError($scope.page_data.msgs[6]);
            return false;
        }
        return true;
    };
    $scope.isDuplicateColumn = function (column) {
        var count = 0;
        for (var i = 0; i < $scope.row_count.length; i++) {
            if (column == $scope.input_data.columns[i].trim()) {
                count++;
                if (count > 1) {
                    DialogContext.showError($scope.page_data.msgs[2]);
                    return true;
                }
            }
        }
        return false;
    };
}]);