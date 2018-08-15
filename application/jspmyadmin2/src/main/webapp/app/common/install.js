'use strict';
var JspMyAdminContext = {
    token: '',
    database: '',
    table: '',
    view: '',
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
                $('#sidebar-error-msg').text(actualData.err);
                $('#sidebar-error-dialog').show();
                error = true;
            }
            if (actualData.msg !== undefined && actualData.msg !== null) {
                $('#sidebar-success-msg').text(actualData.msg);
                $('#sidebar-success-dialog').show();
            }
            return {status: 0, error: error, data: actualData};
        }
        return {status: 1, headers: headers};
    }
};
var Urls = {
    language: contextPath + '/language.text',
    install: contextPath + '/install.text',
    index: contextPath + '/index.html'
};
var JspMyAdmin = angular.module('JspMyAdmin', ['ngResource']);
JspMyAdmin.directive('pwMatch', function () {
    return {
        restrict: 'A',
        require: '?ngModel',
        link: function (scope, elem, attrs, ngModel) {
            if (!ngModel) {
                return;
            }
            scope.$watch(attrs.ngModel, function () {
                validate();
            });
            attrs.$observe('pwMatch', function (val) {
                validate();
            });
            var validate = function () {
                var val1 = ngModel.$viewValue;
                var val2 = attrs.pwMatch;
                ngModel.$setValidity('pwmatch', val1 === val2);
            };
        }
    }
});
JspMyAdmin.factory('LanguageResource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        get: {
            method: 'GET',
            url: Urls.language,
            transformResponse: AjaxHelper.generateResponse
        },
        save: {
            method: 'POST',
            url: Urls.language,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdmin.factory('InstallResource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        save: {
            method: 'POST',
            url: Urls.install,
            headers: {
                'Content-Type': 'application/json'
            },
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdmin.controller('LanguageController', ['LanguageResource', function (LanguageResource) {
    var _self = this;
    _self.language_map = {};
    _self.languageForm = {};
    LanguageResource.get(function (response) {
        if (response.status == 0) {
            _self.language_map = response.data.language_map;
            _self.languageForm.language = response.data.language;
        }
    });
    _self.changeLanguage = function () {
        _self.languageForm.token = JspMyAdminContext.token;
        LanguageResource.save({}, _self.languageForm, function (response) {
            if (response.status == 0) {
                location.reload();
            }
        });
    };
}]);
JspMyAdmin.controller('InstallController', ['InstallResource', function (InstallResource) {
    var _self = this;
    _self.showError = false;
    _self.installForm = {};
    _self.validation = {
        host: true,
        port: true,
        user: true
    };
    _self.configChange = function () {
        switch (_self.installForm.config_type) {
            case 'config':
                _self.validation = {
                    host: true,
                    port: true,
                    user: true
                };
                break;
            case 'half_config':
                _self.validation = {
                    host: true,
                    port: true,
                    user: false
                };
                break;
            default:
                _self.validation = {
                    host: false,
                    port: false,
                    user: false
                };
                break;
        }
    };
    _self.submitInstallForm = function (valid) {
        _self.showError = true;
        if (valid) {
            _self.installForm.token = JspMyAdminContext.token;
            InstallResource.save({}, _self.installForm, function (response) {
                if (response.status == 0 && response.data && !response.data.err) {
                    window.location = Urls.index;
                } else if (response.status == 0 && response.data && !response.data.err) {

                }
            });
        }
    };
}]);
$(function () {
    $(document).keypress(function (e) {
        if (e.which == 13) {
            $('#go_btn').click();
        }
    });
});