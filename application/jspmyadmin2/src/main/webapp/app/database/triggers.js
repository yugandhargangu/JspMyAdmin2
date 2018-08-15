'use strict';

JspMyAdminApp.factory('BF0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/structure/triggers.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BF1Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/trigger/exists.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BF2Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/trigger/show_create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BF3Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/trigger/drop.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BF4Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/trigger/table_list.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BF5Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/trigger/create.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        },
        save: {
            method: 'POST',
            url: contextPath + '/database/trigger/create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('DatabaseTriggersController', ['$rootScope', '$scope', '$state', '$stateParams', 'BF0Resource', 'BF1Resource', 'BF2Resource', 'BF3Resource', function ($rootScope, $scope, $state, $stateParams, BF0Resource, BF1Resource, BF2Resource, BF3Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 6;
    $scope.page_data = {
        trigger_list: {},
        msgs: []
    };
    $scope.create_trigger = {};
    $scope.showCreate = false;
    $scope.showConfirm = false;
    $scope.triggers = [];
    $scope.temp_triggers = [];
    $scope.allCheck = false;
    $scope.callback = null;
    $scope.codeMirrorObj = null;
    BF0Resource.query({request_db: JspMyAdminContext.database}, function (response) {
        if (response.status === 0 && !response.error) {
            $scope.page_data = response.data;
        }
    });
    $scope.checkAll = function () {
        $scope.triggers = [];
        $scope.temp_triggers = [];
        if ($scope.allCheck) {
            if ($scope.page_data.trigger_list.length > 0) {
                angular.forEach($scope.page_data.trigger_list, function (item) {
                    $scope.temp_triggers.push(item.trigger_name);
                    $scope.triggers.push(item.trigger_name);
                });
            }
        }
    };
    $scope.checkOne = function (trigger) {
        var index = $scope.triggers.indexOf(trigger);
        if (index > -1) {
            $scope.triggers.splice(index, 1);
        } else {
            $scope.triggers.push(trigger);
        }
        $scope.allCheck = $scope.triggers.length > 0 && $scope.triggers.length === $scope.page_data.trigger_list.length;
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
        BF2Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            triggers: $scope.triggers
        }, function (response) {
            if (response.status === 0 && !response.error) {
                var actJsonData = angular.fromJson(response.data.query);
                var result = '';
                for (var key in actJsonData) {
                    result += '#------------- Create Trigger: ';
                    result += key;
                    result += ' ------------- \n';
                    result += actJsonData[key];
                    result += ';\n\n\n';
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
            if ($scope.triggers.length > 0) {
                BF3Resource.save({}, {
                    token: JspMyAdminContext.token,
                    request_db: JspMyAdminContext.database,
                    triggers: $scope.triggers
                }, function (response) {
                    if (response.status === 0) {
                        $state.go('database_triggers', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                    }
                });
            }
        };
        $scope.confirm_content = $scope.page_data.msgs[0];
        $scope.showConfirm = true;
    };
    $scope.runClick = function () {
        $state.go('database_trigger_create', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
    };
}]);
JspMyAdminApp.controller('DatabaseTriggerCreateController', ['$rootScope', '$scope', '$state', '$stateParams', 'BF4Resource', 'BF5Resource', function ($rootScope, $scope, $state, $stateParams, BF4Resource, BF5Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 6;
    $scope.page_data = {};
    $scope.input_data = {
        trigger_name: '',
        trigger_body: ''
    };
    $scope.showCreate = false;
    $scope.codeMirrorObj = null;
    $scope.codeMirrorDefinition = null;
    $scope.fetchTables = function () {
        var form_data = {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            database_name: $scope.input_data.database_name
        };
        BF4Resource.save({}, form_data, function (response) {
            if (response.status === 0 && !response.error) {
                $scope.page_data.table_name_list = response.data.tables;
            }
        });
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

        $scope.input_data.action = '';
        $scope.input_data.token = JspMyAdminContext.token;
        $scope.input_data.request_db = JspMyAdminContext.database;
        $scope.input_data.trigger_body = $scope.codeMirrorDefinition.getValue();
        BF5Resource.save({}, $scope.input_data, function (response) {
            if (response.status === 0 && !response.error) {
                $scope.showCreate = true;
                $scope.codeMirrorObj.setValue(response.data.query + ';');
                $scope.codeMirrorObj.refresh();
                $scope.codeMirrorObj.focus();
            }
        });

    };
    $scope.runClick = function () {
        $scope.input_data.action = 'Yes';
        $scope.input_data.token = JspMyAdminContext.token;
        $scope.input_data.request_db = JspMyAdminContext.database;
        $scope.input_data.trigger_body = $scope.codeMirrorDefinition.getValue();
        BF5Resource.save({}, $scope.input_data, function (response) {
            if (response.status === 0 && !response.error) {
                $state.go('database_triggers', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
            }
        });
    };
    BF5Resource.query({request_db: JspMyAdminContext.database}, function (response) {
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