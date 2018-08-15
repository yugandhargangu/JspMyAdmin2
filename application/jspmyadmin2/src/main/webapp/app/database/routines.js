'use strict';

JspMyAdminApp.factory('BD0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/routines.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BD1Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/routine/exists.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BD2Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/routine/show_create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BD3Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/routine/drop.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('DatabaseProceduresController', ['$rootScope', '$scope', '$state', '$stateParams', 'BD0Resource', 'BD1Resource', 'BD2Resource', 'BD3Resource', function ($rootScope, $scope, $state, $stateParams, BD0Resource, BD1Resource, BD2Resource, BD3Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 3;
    $scope.page_data = {};
    $scope.showCreate = false;
    $scope.showConfirm = false;
    $scope.routines = [];
    $scope.temp_routines = [];
    $scope.allCheck = false;
    $scope.callback = null;
    $scope.codeMirrorObj = null;
    $scope.type = 'PROCEDURE';
    BD0Resource.query({request_db: JspMyAdminContext.database, type: $scope.type}, function (response) {
        if (response.status === 0 && !response.error) {
            $scope.page_data = response.data;
        }
    });
    $scope.checkAll = function () {
        $scope.routines = [];
        $scope.temp_routines = [];
        if ($scope.allCheck) {
            if ($scope.page_data.routine_info_list.length > 0) {
                angular.forEach($scope.page_data.routine_info_list, function (item) {
                    $scope.temp_routines.push(item.name);
                    $scope.routines.push(item.name);
                });
            }
        }
    };
    $scope.checkOne = function (table) {
        var index = $scope.routines.indexOf(table);
        if (index > -1) {
            $scope.routines.splice(index, 1);
        } else {
            $scope.routines.push(table);
        }
        $scope.allCheck = $scope.routines.length > 0 && $scope.routines.length === $scope.page_data.routine_info_list.length;
    };
    $scope.confirmYesBtnClick = function () {
        if ($scope.callback != null) {
            $scope.callback();
            $scope.callback = null;
        }
        $scope.showConfirm = false;
    };
    $scope.confirmNoBtnClick = function () {
        $scope.callback = null;
        $scope.showConfirm = false;
    };
    $scope.confirmCloseClick = function () {
        $scope.callback = null;
        $scope.showConfirm = false;
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
        BD2Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            type: $scope.type,
            routines: $scope.routines
        }, function (response) {
            if (response.status === 0 && !response.error) {
                var actJsonData = angular.fromJson(response.data.query);
                var result = '';
                for (var key in actJsonData) {
                    result += '#------------- Create Procedure: ';
                    result += key;
                    result += ' ------------- \n';
                    result += actJsonData[key];
                    result += '\n\n\n';
                }
                $scope.codeMirrorObj.setValue(result);
                $scope.codeMirrorObj.refresh();
                $scope.codeMirrorObj.focus();
                $scope.showCreate = true;
            }
        });
    };
    $scope.showCreateClose = function () {
        $scope.showCreate = false;
    };
    $scope.dropClick = function () {
        $scope.callback = function () {
            if ($scope.routines.length > 0) {
                BD3Resource.save({}, {
                    token: JspMyAdminContext.token,
                    request_db: JspMyAdminContext.database,
                    type: $scope.type,
                    routines: $scope.routines
                }, function (response) {
                    if (response.status === 0) {
                        $state.go('database_procedures', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                    }
                });
            }
        };
        $scope.confirm_content = $scope.page_data.msgs[0];
        $scope.showConfirm = true;
    };
    $scope.runClick = function () {
        $state.go('database_procedure_create', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
    };
}]);
JspMyAdminApp.controller('DatabaseFunctionsController', ['$rootScope', '$scope', '$state', '$stateParams', 'BD0Resource', 'BD1Resource', 'BD2Resource', 'BD3Resource', function ($rootScope, $scope, $state, $stateParams, BD0Resource, BD1Resource, BD2Resource, BD3Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 4;
    $scope.page_data = {};
    $scope.create_function = {};
    $scope.showCreate = false;
    $scope.showConfirm = false;
    $scope.routines = [];
    $scope.temp_routines = [];
    $scope.allCheck = false;
    $scope.callback = null;
    $scope.codeMirrorObj = null;
    $scope.type = 'FUNCTION';
    BD0Resource.query({request_db: JspMyAdminContext.database, type: $scope.type}, function (response) {
        if (response.status === 0 && !response.error) {
            $scope.page_data = response.data;
        }
    });
    $scope.checkAll = function () {
        $scope.routines = [];
        $scope.temp_routines = [];
        if ($scope.allCheck) {
            if ($scope.page_data.routine_info_list.length > 0) {
                angular.forEach($scope.page_data.routine_info_list, function (item) {
                    $scope.temp_routines.push(item.name);
                    $scope.routines.push(item.name);
                });
            }
        }
    };
    $scope.checkOne = function (table) {
        var index = $scope.routines.indexOf(table);
        if (index > -1) {
            $scope.routines.splice(index, 1);
        } else {
            $scope.routines.push(table);
        }
        $scope.allCheck = $scope.routines.length > 0 && $scope.routines.length === $scope.page_data.routine_info_list.length;
    };
    $scope.confirmYesBtnClick = function () {
        if ($scope.callback != null) {
            $scope.callback();
            $scope.callback = null;
        }
        $scope.showConfirm = false;
    };
    $scope.confirmNoBtnClick = function () {
        $scope.callback = null;
        $scope.showConfirm = false;
    };
    $scope.confirmCloseClick = function () {
        $scope.callback = null;
        $scope.showConfirm = false;
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
        BD2Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            type: $scope.type,
            routines: $scope.routines
        }, function (response) {
            if (response.status === 0 && !response.error) {
                var actJsonData = angular.fromJson(response.data.query);
                var result = '';
                for (var key in actJsonData) {
                    result += '#------------- Create Function: ';
                    result += key;
                    result += ' ------------- \n';
                    result += actJsonData[key];
                    result += '\n\n\n';
                }
                $scope.codeMirrorObj.setValue(result);
                $scope.codeMirrorObj.refresh();
                $scope.codeMirrorObj.focus();
                $scope.showCreate = true;
            }
        });
    };
    $scope.showCreateClose = function () {
        $scope.showCreate = false;
    };
    $scope.dropClick = function () {
        $scope.callback = function () {
            if ($scope.routines.length > 0) {
                BD3Resource.save({}, {
                    token: JspMyAdminContext.token,
                    request_db: JspMyAdminContext.database,
                    type: $scope.type,
                    routines: $scope.routines
                }, function (response) {
                    if (response.status === 0) {
                        $state.go('database_functions', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                    }
                });
            }
        };
        $scope.confirm_content = $scope.page_data.msgs[0];
        $scope.showConfirm = true;
    };
    $scope.runClick = function () {
        $state.go('database_function_create', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
    };
}]);