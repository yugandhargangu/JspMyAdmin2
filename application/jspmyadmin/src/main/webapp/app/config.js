'use strict';

var JspMyAdminApp = angular.module('JspMyAdminApp',['ngResource', 'ui.router']);

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
}]);

JspMyAdminApp.run(function ($rootScope, $timeout) {
    $rootScope.$on('$viewContentLoaded', function (event) {
        $timeout(function () {
            $('#sidebar-loading-dialog').hide();
        });
    });
});

JspMyAdminApp.controller('SideBarController',['$rootScope', function($rootScope) {
}]);

JspMyAdminApp.controller('TopBarController',['$rootScope', function($rootScope) {
}]);

JspMyAdminApp.controller('HeaderController',['$rootScope', function($rootScope) {
    $rootScope.menuIndex = 1;
}]);

