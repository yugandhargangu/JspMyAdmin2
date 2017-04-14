'use strict';

JspMyAdminApp.factory('BG0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/structure/events.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BG1Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/event/exists.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BG2Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/event/show_create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BG3Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/event/drop.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BG4Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/event/enable.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BG5Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/event/disable.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BG6Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/event/rename.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BG7Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/event/create.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        },
        save: {
            method: 'POST',
            url: contextPath + '/database/event/create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('DatabaseEventsController', ['$rootScope', '$scope', '$state', '$stateParams', 'BG0Resource', 'BG1Resource', 'BG2Resource', 'BG3Resource', 'BG4Resource', 'BG5Resource', 'BG6Resource', function ($rootScope, $scope, $state, $stateParams, BG0Resource, BG1Resource, BG2Resource, BG3Resource, BG4Resource, BG5Resource, BG6Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 5;
    $scope.page_data = {
        event_list: {},
        msgs: []
    };
    $scope.new_event = '';
    $scope.create_event = {};
    $scope.showCreate = false;
    $scope.showRename = false;
    $scope.showConfirm = false;
    $scope.events = [];
    $scope.temp_events = [];
    $scope.allCheck = false;
    $scope.callback = null;
    $scope.codeMirrorObj = null;
    BG0Resource.query({request_db: JspMyAdminContext.database}, function (response) {
        if (response.status === 0 && !response.error) {
            $scope.page_data = response.data;
        }
    });
    $scope.checkAll = function () {
        $scope.events = [];
        $scope.temp_events = [];
        if ($scope.allCheck) {
            if ($scope.page_data.event_list.length > 0) {
                angular.forEach($scope.page_data.event_list, function (item) {
                    $scope.temp_events.push(item.name);
                    $scope.events.push(item.name);
                });
            }
        }
    };
    $scope.checkOne = function (event) {
        var index = $scope.events.indexOf(event);
        if (index > -1) {
            $scope.events.splice(index, 1);
        } else {
            $scope.events.push(event);
        }
        $scope.allCheck = $scope.events.length > 0 && $scope.events.length === $scope.page_data.event_list.length;
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
        BG2Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            events: $scope.events
        }, function (response) {
            if (response.status === 0 && !response.error) {
                var actJsonData = angular.fromJson(response.data.query);
                var result = '';
                for (var key in actJsonData) {
                    result += '#------------- Create Trigger: ';
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
            if ($scope.events.length > 0) {
                BG3Resource.save({}, {
                    token: JspMyAdminContext.token,
                    request_db: JspMyAdminContext.database,
                    events: $scope.events
                }, function (response) {
                    if (response.status === 0) {
                        $state.go('database_events', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                    }
                });
            }
        };
        $scope.confirm_content = $scope.page_data.msgs[0];
        $scope.showConfirm = true;
    };
    $scope.renameShowClick = function () {
        $scope.showRename = true;
    };
    $scope.renameShowClose = function () {
        $scope.new_event = '';
        $scope.showRename = false;
    };
    $scope.renameClick = function () {
        if ($scope.events.length > 0) {
            BG6Resource.save({}, {
                token: JspMyAdminContext.token,
                request_db: JspMyAdminContext.database,
                events: $scope.events,
                new_event: $scope.new_event
            }, function (response) {
                if (response.status === 0) {
                    $state.go('database_events', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                }
            });
        }
    };
    $scope.enableClick = function () {
        if ($scope.events.length > 0) {
            BG4Resource.save({}, {
                token: JspMyAdminContext.token,
                request_db: JspMyAdminContext.database,
                events: $scope.events
            }, function (response) {
                if (response.status === 0) {
                    $state.go('database_events', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                }
            });
        }
    };
    $scope.disableClick = function () {
        if ($scope.events.length > 0) {
            BG5Resource.save({}, {
                token: JspMyAdminContext.token,
                request_db: JspMyAdminContext.database,
                events: $scope.events
            }, function (response) {
                if (response.status === 0) {
                    $state.go('database_events', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                }
            });
        }
    };
    $scope.addEventClick = function () {
        $state.go('database_event_create', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
    }
}]);
JspMyAdminApp.controller('DatabaseEventCreateController', ['$rootScope', '$scope', '$state', '$stateParams', 'BG7Resource', function ($rootScope, $scope, $state, $stateParams, BG7Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 5;
    $scope.page_data = {};
    $scope.input_data = {
        event_name: '',
        body: '',
        start_date_interval_quantity: [],
        start_date_interval: [],
        end_date_interval_quantity: [],
        end_date_interval: []
    };
    $scope.intervals = {
        start: [],
        end: [],
        start_count: 1,
        end_count: 1
    };
    $scope.showCreate = false;
    $scope.codeMirrorObj = null;
    $scope.codeMirrorDefinition = null;
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
        $scope.input_data.body = $scope.codeMirrorDefinition.getValue();
        BG7Resource.save({}, $scope.input_data, function (response) {
            if (response.status === 0 && !response.error) {
                $scope.showCreate = true;
                $scope.codeMirrorObj.setValue(response.data.query);
                $scope.codeMirrorObj.refresh();
                $scope.codeMirrorObj.focus();
            }
        });

    };
    $scope.runClick = function () {
        $scope.input_data.action = 'Yes';
        $scope.input_data.token = JspMyAdminContext.token;
        $scope.input_data.request_db = JspMyAdminContext.database;
        $scope.input_data.body = $scope.codeMirrorDefinition.getValue();
        BG7Resource.save({}, $scope.input_data, function (response) {
            if (response.status === 0 && !response.error) {
                $state.go('database_events', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
            }
        });
    };
    $scope.addStartInterval = function () {
        $scope.intervals.start.push($scope.intervals.start_count++);
    };
    $scope.removeStartInterval = function (item) {
        for (var i = 0; i < $scope.intervals.start.length; i++) {
            if (item == $scope.intervals.start[i]) {
                $scope.intervals.start.splice(i, 1);
                break;
            }
        }
    };
    $scope.addEndInterval = function () {
        $scope.intervals.end.push($scope.intervals.start_count++);
    };
    $scope.removeEndInterval = function (item) {
        for (var i = 0; i < $scope.intervals.end.length; i++) {
            if (item == $scope.intervals.end[i]) {
                $scope.intervals.end.splice(i, 1);
                break;
            }
        }
    };
    BG7Resource.query({request_db: JspMyAdminContext.database}, function (response) {
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