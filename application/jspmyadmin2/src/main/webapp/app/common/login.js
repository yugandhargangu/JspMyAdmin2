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
    login: contextPath + '/login.text',
    home: contextPath + '/home.html'
};
var JspMyAdmin = angular.module('JspMyAdmin', ['ngResource']);
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
JspMyAdmin.factory('LoginResource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        get: {
            method: 'GET',
            url: Urls.login + '?token=:token',
            params: {
                token: '@token'
            },
            transformResponse: AjaxHelper.generateResponse
        },
        save: {
            method: 'POST',
            url: Urls.login,
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
        LanguageResource.save({}, _self.languageForm, function (response) {
            if (response.status == 0) {
                location.reload();
            }
        });
    };
}]);
JspMyAdmin.controller('LoginController', ['LoginResource', function (LoginResource) {
    var _self = this;
    _self.half_config = true;
    _self.showError = false;
    _self.loginForm = {};
    LoginResource.get({
        token: JspMyAdminContext.token
    }, function (response) {
        if (response.status == 0 && response.data) {
            _self.half_config = response.data.half_config;
            if (_self.half_config) {
                _self.loginForm.username = response.data.username;
            } else {
                _self.loginForm.hostname = response.data.hostname;
                _self.loginForm.portnumber = response.data.portnumber;
                _self.loginForm.username = response.data.username;
            }
        }
    });
    _self.submitLoginForm = function (valid) {
        _self.showError = true;
        if (valid) {
            _self.loginForm.token = JspMyAdminContext.token;
            LoginResource.save({}, _self.loginForm, function (response) {
                if (response.status == 0 && response.data.err && response.data.err != '') {
                    ErrorDialog.show(response.data.err);
                } else {
                    window.location = Urls.home;
                }
            });
        }
    };
}]);
$('body').keypress(function (e) {
    if (e.which == 13) {
        $('#go_btn').click();
    }
});