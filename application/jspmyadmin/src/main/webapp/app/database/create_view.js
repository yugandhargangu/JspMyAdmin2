'use strict';
JspMyAdminApp.factory('BC0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/view/create.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        },
        save: {
            method: 'POST',
            url: contextPath + '/database/view/create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('DatabaseViewCreateController', ['$rootScope', '$scope', '$state', '$stateParams', 'BC0Resource', function ($rootScope, $scope, $state, $stateParams, BC0Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 2;
    $scope.page_data = {};
    $scope.input_data = {
        create_type: 'CREATE',
        definition: '',
        column_list: []
    };
    if (JspMyAdminContext.redirectParams.view_name) {
        $scope.input_data.view_name = JspMyAdminContext.redirectParams.view_name;
        $scope.redirectParams = {};
    }
    $scope.column_count = [];
    $scope.column_increment = 0;
    $scope.showCreate = false;
    $scope.codeMirrorObj = null;
    $scope.codeMirrorDefinition = null;
    $scope.addColumn = function () {
        $scope.column_count.push($scope.column_increment++);
    };
    $scope.initColumn = function (item) {
        var index = -1;
        for (var i = 0; i < $scope.column_count.length; i++) {
            if (item === $scope.column_count[i]) {
                index = i;
                break;
            }
        }
        $scope.input_data.column_list[index] = '';
    };
    $scope.removeColumn = function (item) {
        for (var i = 0; i < $scope.column_count.length; i++) {
            if (item === $scope.column_count[i]) {
                $scope.column_count.splice(i, 1);
                break;
            }
        }
        if ($scope.column_count.length === 0) {
            $scope.column_increment = 0;
        }
    };
    $scope.closeShowCreate = function () {
        $scope.showCreate = false;
    };
    $scope.showCreateClick = function () {
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
        $scope.input_data.definition = $scope.codeMirrorDefinition.getValue();
        BC0Resource.save({}, $scope.input_data, function (response) {
            if (response.status === 0 && !response.error) {
                $scope.showCreate = true;
                $scope.codeMirrorObj.setValue(response.data.query + ';');
                $scope.codeMirrorObj.refresh();
                $scope.codeMirrorObj.focus();
            }
        });
    };
    $scope.goClick = function () {
        $scope.input_data.action = 'Yes';
        $scope.input_data.token = JspMyAdminContext.token;
        $scope.input_data.request_db = JspMyAdminContext.database;
        BC0Resource.save({}, $scope.input_data, function (response) {
            if (response.status === 0 && !response.error) {
                $state.go('database_views', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
            }
        });
    };
    BC0Resource.query({request_db: JspMyAdminContext.database}, function (response) {
        if (response.status === 0 && !response.error) {
            $scope.page_data = response.data;
        }
    });
    CallbackHelper.add(function () {
        var definition_area = document.getElementById('definition-area');
        $scope.codeMirrorDefinition = CodeMirror.fromTextArea(definition_area, {
            mode: 'text/x-mysql',
            indentWithTabs: true,
            smartIndent: true,
            lineNumbers: true,
            lineWrapping: true,
            matchBrackets: true,
            autofocus: true,
            extraKeys: {
                "Ctrl-Space": "autocomplete"
            }
        });
    });
}]);