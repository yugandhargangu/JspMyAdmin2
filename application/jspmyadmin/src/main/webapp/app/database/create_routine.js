'use strict';

JspMyAdminApp.factory('BE0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/routine/create.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BE1Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/procedure/create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BE2Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/function/create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('DatabaseProcedureCreateController', ['$rootScope', '$scope', '$state', '$stateParams', 'BE0Resource', 'BE1Resource', function ($rootScope, $scope, $state, $stateParams, BE0Resource, BE1Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 3;
    $scope.page_data = {};
    $scope.input_data = {
        body: '',
        param_types: [],
        params: [],
        param_data_types: [],
        lengths: []
    };
    $scope.row_count = [];
    $scope.row_increment = 0;
    $scope.showCreate = false;
    $scope.codeMirrorObj = null;
    $scope.codeMirrorDefinition = null;
    $scope.addRow = function () {
        $scope.row_count.push($scope.row_increment++);
    };
    $scope.getRowIndex = function (item) {
        var index = -1;
        for (var i = 0; i < $scope.row_count.length; i++) {
            if (item == $scope.row_count[i]) {
                index = i;
                break;
            }
        }
        return index;
    };
    $scope.removeRow = function (item) {
        alert(item);
        alert($scope.row_count);
        for (var i = 0; i < $scope.row_count.length; i++) {
            if (item == $scope.row_count[i]) {
                $scope.row_count.splice(i, 1);
                break;
            }
        }
    };
    BE0Resource.query({request_db: JspMyAdminContext.database}, function (response) {
        if (response.status === 0) {
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
JspMyAdminApp.controller('DatabaseFunctionCreateController', ['$rootScope', '$scope', '$state', '$stateParams', 'BE0Resource', 'BE2Resource', function ($rootScope, $scope, $state, $stateParams, BE0Resource, BE2Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 4;
}]);