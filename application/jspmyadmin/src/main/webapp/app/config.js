'use strict';

var JspMyAdminContext = {
    token: '',
    database: '',
    table: '',
    view: '',
    redirectParams: {},
    setDatabase: function (database) {
        this.database = database;
        this.table = '';
        this.view = '';
    },
    setTable: function (table) {
        this.database = '';
        this.table = table;
        this.view = '';
    },
    setView: function (view) {
        this.database = '';
        this.table = '';
        this.view = view;
    },
    setToken: function (token) {
        this.token = token;
    },
    getToken: function () {
        return this.token;
    },
    checkDatabase: function ($stateParams) {
        if ($stateParams.request_db) {
            var database = EncryptionHelper.decode($stateParams.request_db);
            if (database == '') {
                return false;
            } else {
                this.database = database;
                return true;
            }
        } else {
            return false;
        }
    },
    checkTable: function ($stateParams) {
        if (!this.checkDatabase($stateParams)) {
            if ($stateParams.request_table) {
                var table = EncryptionHelper.decode($stateParams.request_table);
                if (table == '') {
                    return false;
                } else {
                    this.table = table;
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    },
    checkView: function ($stateParams) {
        if (!this.checkDatabase($stateParams)) {
            if ($stateParams.request_view) {
                var view = EncryptionHelper.decode($stateParams.request_view);
                if (view == '') {
                    return false;
                } else {
                    this.view = view;
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    },
    loadDefault: function ($state) {
        $state.go('home', {});
    }
};

var DialogContext = {
    showError: function (err) {
        $('#sidebar-error-msg').text(err);
        $('#sidebar-error-dialog').show();
    },
    showMessage: function (msg) {
        $('#sidebar-success-msg').text(msg);
        $('#sidebar-success-dialog').show();
    }
};

var ValidationUtil = {
    isEmpty: function (value) {
        return value.trim() == '';
    },
    isInteger: function (value) {
        return Math.floor(value) == value && $.isNumeric(value);
    },
    isValidSqlString: function (value, withQuotes) {

        if (withQuotes && (!value.startsWith("'") || !value.endsWith("'"))) {
            return false;
        }
        var count1 = 0;
        var count2 = 0;
        var lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = value.indexOf("'", lastIndex);
            if (lastIndex != -1) {
                count1++;
                lastIndex += 1;
            }
        }
        lastIndex = 0;
        while (lastIndex != -1) {
            lastIndex = value.indexOf("\'", lastIndex);

            if (lastIndex != -1) {
                count2++;
                lastIndex += 2;
            }
        }
        return count1 == count2;
    }
};

var AjaxHelper = {
    generateResponse: function (data, headers, status) {
        var actualData = {};
        if (data != null) {
            actualData = angular.fromJson(data);
        }
        if (actualData.token && actualData.token != '') {
            JspMyAdminContext.token = actualData.token;
        }
        if (status == 200) {
            var error = false;
            if (actualData.err !== undefined && actualData.err !== null) {
                DialogContext.showError(actualData.err);
                error = true;
            }
            if (actualData.msg !== undefined && actualData.msg !== null) {
                DialogContext.showMessage(actualData.msg);
            }
            return {status: 0, error: error, data: actualData};
        }
        return {status: 1, headers: headers};
    }
};

var CallbackHelper = {
    callbacks: [],
    add: function (callback) {
        this.callbacks.push(callback);
    },
    clean: function () {
        this.callbacks = [];
    }
};

var JspMyAdminApp = angular.module('JspMyAdminApp', ['ngResource', 'ui.router']);

JspMyAdminApp.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
    $urlRouterProvider.otherwise('/home');
    // Home state
    $stateProvider.state('home', {
        url: '/home',
        templateUrl: contextPath + '/home.html',
        controller: 'HomeController as ctrl'
    });

    // Server level states
    $stateProvider.state('server_databases', {
        url: '/server/databases',
        templateUrl: contextPath + '/server/databases.html',
        controller: 'ServerDatabasesController as ctrl'
    }).state('server_status', {
        url: '/server/status',
        templateUrl: contextPath + '/server/status.html',
        controller: 'ServerStatusController as ctrl'
    }).state('server_variables', {
        url: '/server/variables',
        templateUrl: contextPath + '/server/variables.html',
        controller: 'ServerVariablesController as ctrl'
    }).state('server_charsets', {
        url: '/server/charsets',
        templateUrl: contextPath + '/server/charsets.html',
        controller: 'ServerCharsetController as ctrl'
    }).state('server_engines', {
        url: '/server/engines',
        templateUrl: contextPath + '/server/engines.html',
        controller: 'ServerEngineController as ctrl'
    }).state('server_plugins', {
        url: '/server/plugins',
        templateUrl: contextPath + '/server/plugins.html',
        controller: 'ServerPluginsController as ctrl'
    });

    // database level states
    $stateProvider.state('database_tables', {
        url: '/database/tables/:request_db',
        templateUrl: contextPath + '/database/structure/tables.html',
        controller: 'DatabaseTablesController as ctrl'
    }).state('database_views', {
        url: '/database/views/:request_db',
        templateUrl: contextPath + '/database/structure/views.html',
        controller: 'DatabaseViewsController as ctrl'
    }).state('database_table_create', {
        url: '/database/table/create/:request_db',
        templateUrl: contextPath + '/database/table/create.html',
        controller: 'DatabaseTableCreateController as ctrl'
    }).state('database_view_create', {
        url: '/database/view/create/:request_db',
        templateUrl: contextPath + '/database/view/create.html',
        controller: 'DatabaseViewCreateController as ctrl'
    }).state('database_procedures', {
        url: '/database/procedures/:request_db',
        templateUrl: contextPath + '/database/structure/procedures.html',
        controller: 'DatabaseProceduresController as ctrl'
    }).state('database_functions', {
        url: '/database/functions/:request_db',
        templateUrl: contextPath + '/database/structure/functions.html',
        controller: 'DatabaseFunctionsController as ctrl'
    }).state('database_procedure_create', {
        url: '/database/procedure/create/:request_db',
        templateUrl: contextPath + '/database/procedure/create.html',
        controller: 'DatabaseProcedureCreateController as ctrl'
    }).state('database_function_create', {
        url: '/database/function/create/:request_db',
        templateUrl: contextPath + '/database/function/create.html',
        controller: 'DatabaseFunctionCreateController as ctrl'
    });
}]);

JspMyAdminApp.run(function ($rootScope, $timeout) {
    $rootScope.$on('$viewContentLoaded', function (event) {
        $timeout(function () {
            $('#sidebar-loading-dialog').hide();
            if (CallbackHelper.callbacks.length > 0) {
                for (var i = 0; i < CallbackHelper.callbacks.length; i++) {
                    CallbackHelper.callbacks[i]();
                    CallbackHelper.callbacks.splice(i, 1);
                }
            }
        });
    });
});

JspMyAdminApp.controller('SideBarController', ['$rootScope', function ($rootScope) {
}]);

JspMyAdminApp.controller('TopBarController', ['$rootScope', function ($rootScope) {
}]);

JspMyAdminApp.controller('HeaderController', ['$rootScope', '$scope', '$state', function ($rootScope, $scope, $state) {
    $rootScope.menuIndex = 1;
    $scope.goToState = function (state, id) {
        switch (id) {
            case 2:
                $state.go(state, {request_db: EncryptionHelper.encode(JspMyAdminContext.database)});
                break;
            case 3:
                break;
            case 4:
                break;
        }
    };
}]);

