'use strict';
JspMyAdminApp.factory('BA0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/structure/tables.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA1Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/database/structure/views.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA2Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/table/exists.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA3Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/view/create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA4Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/tables/drop.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA5Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/truncate.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA6Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/show_create.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA7Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/duplicate.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA8Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/prefix.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA9Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/suffix.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA10Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/copy.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA11Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/structure/views/drop.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('BA12Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/database/view/exists.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('DatabaseTablesController', ['$rootScope', '$scope', '$state', '$stateParams', 'BA0Resource', 'BA2Resource', 'BA4Resource', 'BA5Resource', 'BA6Resource', 'BA7Resource', 'BA8Resource', 'BA9Resource', 'BA10Resource', function ($rootScope, $scope, $state, $stateParams, BA0Resource, BA2Resource, BA4Resource, BA5Resource, BA6Resource, BA7Resource, BA8Resource, BA9Resource, BA10Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 1;
    $scope.page_data = {};
    $scope.tableCount = 0;
    $scope.sort_input = {
        type: ' ASC',
        name: 'table_name'
    };
    $scope.groupId = 0;
    $scope.tables = [];
    $scope.temp_tables = [];
    $scope.allCheck = false;
    $scope.callback = null;
    $scope.codeMirrorObj = null;
    $scope.input_data = {};
    $scope.prefix_input = {};
    $scope.copy_input = {};
    $scope.loadTables = function (name) {
        if (name != '') {
            if ($scope.sort_input.name === name) {
                if ($scope.sort_input.type === ' DESC') {
                    $scope.sort_input.type = ' ASC';
                } else {
                    $scope.sort_input.type = ' DESC';
                }
            } else {
                $scope.sort_input.name = name;
                $scope.sort_input.type = ' ASC'
            }
        }
        BA0Resource.query({
            request_db: JspMyAdminContext.database,
            name: $scope.sort_input.name,
            type: $scope.sort_input.type
        }, function (response) {
            if (response.status === 0) {
                $scope.page_data = response.data;
                $scope.tableCount = $scope.page_data.table_list.length;
                $scope.groupId = 0;
                $scope.tables = [];
                $scope.temp_tables = [];
            }
        });
    };
    $scope.showIcon = function (name, type) {
        return $scope.sort_input.name === name && $scope.sort_input.type === type;
    };
    $scope.calculateBytes = function (original) {
        return ByteUtils.fromBytes(original);
    };
    $scope.createTableBtnClick = function () {
        $state.go('database_table_create', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
    };
    $scope.checkAll = function () {
        $scope.tables = [];
        $scope.temp_tables = [];
        if ($scope.allCheck) {
            if ($scope.page_data.table_list.length > 0) {
                angular.forEach($scope.page_data.table_list, function (item) {
                    $scope.temp_tables.push(item.name);
                    $scope.tables.push(item.name);
                });
            }
        }
    };
    $scope.checkOne = function (table) {
        var index = $scope.tables.indexOf(table);
        if (index > -1) {
            $scope.tables.splice(index, 1);
        } else {
            $scope.tables.push(table);
        }
        $scope.allCheck = $scope.tables.length > 0 && $scope.tables.length === $scope.page_data.table_list.length;
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
    $scope.showGroup = function (groupId) {
        $scope.input_data.enable_checks = '';
        $scope.input_data.drop_checks = '';
        $scope.prefix_input.type = 'add';
        $scope.prefix_input.prefix = '';
        $scope.prefix_input.new_prefix = '';
        $scope.copy_input.database_name = '';
        $scope.copy_input.type = 'data';
        $scope.copy_input.drop_checks = '';
        $scope.groupId = groupId;
    };
    $scope.closeGroup = function () {
        $scope.groupId = 0;
    };
    $scope.prefixClick = function () {
        if ($scope.prefix_input.prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[2]);
            return;
        }
        if ($scope.prefix_input.type === 'replace' && $scope.prefix_input.new_prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[3]);
            return;
        }
        BA8Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            type: $scope.prefix_input.type,
            prefix: $scope.prefix_input.prefix,
            new_prefix: $scope.prefix_input.new_prefix,
            tables: $scope.tables
        }, function (response) {
            if (response.status === 0) {
                $scope.loadTables('');
            }
        });
    };
    $scope.suffixClick = function () {
        if ($scope.prefix_input.prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[4]);
            return;
        }
        if ($scope.prefix_input.type === 'replace' && $scope.prefix_input.new_prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[5]);
            return;
        }
        BA9Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            type: $scope.prefix_input.type,
            prefix: $scope.prefix_input.prefix,
            new_prefix: $scope.prefix_input.new_prefix,
            tables: $scope.tables
        }, function (response) {
            if (response.status === 0) {
                $scope.loadTables('');
            }
        });
    };
    $scope.copyClick = function () {
        BA10Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            database_name: $scope.copy_input.database_name,
            type: $scope.copy_input.type,
            drop_checks: $scope.copy_input.drop_checks,
            tables: $scope.tables
        }, function (response) {
            if (response.status === 0) {
                $scope.loadTables('');
            }
        });
    };
    $scope.duplicateClick = function () {
        if ($scope.tables.length > 0) {
            BA7Resource.save({}, {
                token: JspMyAdminContext.token,
                request_db: JspMyAdminContext.database,
                enable_checks: $scope.input_data.enable_checks,
                drop_checks: $scope.input_data.drop_checks,
                tables: $scope.tables
            }, function (response) {
                if (response.status === 0) {
                    $scope.loadTables('');
                }
            });
        }
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
        BA6Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            tables: $scope.tables
        }, function (response) {
            if (response.status === 0 && !response.error) {
                var actJsonData = angular.fromJson(response.data.query);
                var result = '';
                for (var key in actJsonData) {
                    result += '#------------- Create Table: ';
                    result += key;
                    result += ' ------------- \n';
                    result += actJsonData[key];
                    result += '\n\n\n';
                }
                $scope.codeMirrorObj.setValue(result);
                $scope.codeMirrorObj.refresh();
                $scope.codeMirrorObj.focus();
            }
        });
        $scope.groupId = 1;
    };
    $scope.truncateClick = function () {
        $scope.callback = function () {
            if ($scope.tables.length > 0) {
                BA5Resource.save({}, {
                    token: JspMyAdminContext.token,
                    request_db: JspMyAdminContext.database,
                    enable_checks: $scope.input_data.enable_checks,
                    tables: $scope.tables
                }, function (response) {
                    if (response.status === 0) {
                        $scope.loadTables('');
                    }
                });
            }
        };
        $scope.confirm_content = $scope.page_data.msgs[1];
        $scope.showConfirm = true;
    };
    $scope.dropClick = function () {
        $scope.callback = function () {
            if ($scope.tables.length > 0) {
                BA4Resource.save({}, {
                    token: JspMyAdminContext.token,
                    request_db: JspMyAdminContext.database,
                    enable_checks: $scope.input_data.enable_checks,
                    tables: $scope.tables
                }, function (response) {
                    if (response.status === 0) {
                        $scope.loadTables('');
                    }
                });
            }
        };
        $scope.confirm_content = $scope.page_data.msgs[0];
        $scope.showConfirm = true;
    };
    $scope.loadTables('');
}]);
JspMyAdminApp.controller('DatabaseViewsController', ['$rootScope', '$scope', '$state', '$stateParams', 'BA1Resource', 'BA6Resource', 'BA8Resource', 'BA9Resource', 'BA11Resource', 'BA12Resource', function ($rootScope, $scope, $state, $stateParams, BA1Resource, BA6Resource, BA8Resource, BA9Resource, BA11Resource, BA12Resource) {
    if (!JspMyAdminContext.checkDatabase($stateParams)) {
        JspMyAdminContext.loadDefault($state);
        return;
    }
    $rootScope.menuIndex = 2;
    $rootScope.menuActiveIndex = 2;
    $scope.page_data = {};
    $scope.viewCount = 0;
    $scope.groupId = 0;
    $scope.views = [];
    $scope.temp_views = [];
    $scope.allCheck = false;
    $scope.callback = null;
    $scope.codeMirrorObj = null;
    $scope.input_data = {};
    $scope.prefix_input = {};
    $scope.copy_input = {};
    $scope.loadViews = function () {
        BA1Resource.query({request_db: JspMyAdminContext.database}, function (response) {
            if (response.status === 0) {
                $scope.page_data = response.data;
                $scope.viewCount = $scope.page_data.table_list.length;
                $scope.groupId = 0;
                $scope.views = [];
                $scope.temp_views = [];
            }
        });
    };
    $scope.createViewBtnClick = function () {
        $state.go('database_view_create', {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
    };
    $scope.checkAll = function () {
        $scope.views = [];
        $scope.temp_views = [];
        if ($scope.allCheck) {
            if ($scope.page_data.table_list.length > 0) {
                angular.forEach($scope.page_data.table_list, function (item) {
                    $scope.temp_views.push(item.name);
                    $scope.views.push(item.name);
                });
            }
        }
    };
    $scope.checkOne = function (table) {
        var index = $scope.views.indexOf(table);
        if (index > -1) {
            $scope.views.splice(index, 1);
        } else {
            $scope.views.push(table);
        }
        $scope.allCheck = $scope.views.length > 0 && $scope.views.length === $scope.page_data.table_list.length;
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
    $scope.showGroup = function (groupId) {
        $scope.input_data.enable_checks = '';
        $scope.input_data.drop_checks = '';
        $scope.prefix_input.type = 'add';
        $scope.prefix_input.prefix = '';
        $scope.prefix_input.new_prefix = '';
        $scope.copy_input.database_name = '';
        $scope.copy_input.type = 'data';
        $scope.copy_input.drop_checks = '';
        $scope.groupId = groupId;
    };
    $scope.closeGroup = function () {
        $scope.groupId = 0;
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
        BA6Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            tables: $scope.views
        }, function (response) {
            if (response.status === 0 && !response.error) {
                var actJsonData = angular.fromJson(response.data.query);
                var result = '';
                for (var key in actJsonData) {
                    result += '#------------- Create View: ';
                    result += key;
                    result += ' ------------- \n';
                    result += actJsonData[key];
                    result += '\n\n\n';
                }
                $scope.codeMirrorObj.setValue(result);
                $scope.codeMirrorObj.refresh();
                $scope.codeMirrorObj.focus();
            }
        });
        $scope.groupId = 1;
    };
    $scope.prefixClick = function () {
        if ($scope.prefix_input.prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[1]);
            return;
        }
        if ($scope.prefix_input.type === 'replace' && $scope.prefix_input.new_prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[2]);
            return;
        }
        BA8Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            type: $scope.prefix_input.type,
            prefix: $scope.prefix_input.prefix,
            new_prefix: $scope.prefix_input.new_prefix,
            tables: $scope.views
        }, function (response) {
            if (response.status === 0) {
                $scope.loadViews('');
            }
        });
    };
    $scope.suffixClick = function () {
        if ($scope.prefix_input.prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[3]);
            return;
        }
        if ($scope.prefix_input.type === 'replace' && $scope.prefix_input.new_prefix.trim() === '') {
            DialogContext.showError($scope.page_data.msgs[4]);
            return;
        }
        BA9Resource.save({}, {
            token: JspMyAdminContext.token,
            request_db: JspMyAdminContext.database,
            type: $scope.prefix_input.type,
            prefix: $scope.prefix_input.prefix,
            new_prefix: $scope.prefix_input.new_prefix,
            tables: $scope.views
        }, function (response) {
            if (response.status === 0) {
                $scope.loadViews('');
            }
        });
    };
    $scope.dropClick = function () {
        $scope.callback = function () {
            if ($scope.views.length > 0) {
                BA11Resource.save({}, {
                    token: JspMyAdminContext.token,
                    request_db: JspMyAdminContext.database,
                    tables: $scope.views
                }, function (response) {
                    if (response.status === 0) {
                        $scope.loadViews('');
                    }
                });
            }
        };
        $scope.confirm_content = $scope.page_data.msgs[0];
        $scope.showConfirm = true;
    };
    $scope.loadViews('');
}]);