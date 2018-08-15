'use strict';
JspMyAdminApp.factory('AA0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        get: {
            method: 'GET',
            url: contextPath + '/home.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('AA1Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/home/save_collation.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('AA2Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/home/save_locale.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('AA3Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: contextPath + '/home/save_fontsize.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('HomeController', ['$rootScope', '$scope', 'AA0Resource', 'AA1Resource', 'AA2Resource', 'AA3Resource', function ($rootScope, $scope, AA0Resource, AA1Resource, AA2Resource, AA3Resource) {
    $rootScope.menuIndex = 1;
    $rootScope.menuActiveIndex = 0;
    $scope.home_data = {};
    $scope.input_data = {};
    AA0Resource.get(function (response) {
        if (response.status === 0) {
            $scope.home_data = response.data;
            $scope.input_data.collation = $scope.home_data.collation;
            $scope.input_data.language = $scope.home_data.language;
            $scope.input_data.fontsize = $scope.home_data.fontsize.toString();
        }
    });
    $scope.saveCollation = function () {
        AA1Resource.save({token: JspMyAdminContext.token, collation: $scope.input_data.collation}, function (response) {
            if (response.status === 0) {
                location.reload();
            }
        });
    };
    $scope.saveLocale = function () {
        AA2Resource.save({token: JspMyAdminContext.token, language: $scope.input_data.language}, function (response) {
            location.reload();
        });
    };
    $scope.saveFontSize = function () {
        AA3Resource.save({token: JspMyAdminContext.token, fontsize: $scope.input_data.fontsize}, function (response) {
            location.reload();
        });
    };
}]);