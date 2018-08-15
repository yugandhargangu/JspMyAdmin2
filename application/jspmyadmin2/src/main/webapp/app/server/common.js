'use strict';
JspMyAdminApp.factory('AC0Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/server/status.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('AC1Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/server/charsets.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('AC2Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/server/engines.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('AC3Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/server/plugins.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.factory('AC4Resource', ['$resource', function ($resource) {
    return $resource('/', {}, {
        query: {
            method: 'GET',
            url: contextPath + '/server/variables.text',
            isArray: false,
            transformResponse: AjaxHelper.generateResponse
        },
        save: {
            method: 'POST',
            url: contextPath + '/server/variable.text',
            transformResponse: AjaxHelper.generateResponse
        }
    });
}]);
JspMyAdminApp.controller('ServerStatusController', ['$rootScope', '$scope', 'AC0Resource', function ($rootScope, $scope, AC0Resource) {
    $rootScope.menuIndex = 1;
    $rootScope.menuActiveIndex = 3;
    $scope.page_data = {};
    $scope.bytes_dates = ['Bytes_received', 'Bytes_sent', 'Uptime', 'Uptime_since_flush_status'];
    $scope.formatCount = 0;
    AC0Resource.query({}, function (response) {
        if (response.status === 0) {
            $scope.page_data = response.data;
            $scope.initStatus();
            $scope.formatCount = 0;
        }
    });
    $scope.initStatus = function () {
        $scope.variables = $scope.page_data.data_list;
        $scope.search = '';
    };
    $scope.getFormattedValue = function (code, original) {
        var index = $scope.bytes_dates.indexOf(code);
        if (index > -1) {
            switch (index) {
                case 0:
                    $scope.received = original;
                    return $scope.calculateBytes(original);
                case 1:
                    $scope.sent = original;
                    return $scope.calculateBytes(original);
                case 2:
                    $scope.start_date = original;
                    return $scope.calculateDate(original);
                case 3:
                    $scope.run_time = original;
                    return $scope.calculateDays(original);
            }
        }
        return original;
    };
    $scope.calculateBytes = function (original) {
        return ByteUtils.fromBytes(original);
    };
    $scope.calculateDate = function (original) {
        var d = new Date(Date.now() - original);
        return d.getFullYear() + '-' + (d.getMonth() + 1) + '-' + d.getDate() + ' ' + d.getHours() + ':' + d.getMinutes() + ':' + d.getSeconds();
    };
    $scope.calculateDays = function (original) {
        if (original > 60) {
            var sec = original % 60;
            original = (original - sec) / 60;
            if (original > 60) {
                var min = original % 60;
                original = (original - min) / 60;
                if (original > 60) {
                    var hour = original % 24;
                    original = (original - hour) / 24;
                    return original + ' ' + $scope.page_data.msgs[0] + ' ' + hour + ' ' + $scope.page_data.msgs[1] + ' '
                        + min + ' ' + $scope.page_data.msgs[2] + ' ' + sec + ' ' + $scope.page_data.msgs[3];
                } else {
                    return original + ' ' + $scope.page_data.msgs[1] + ' ' + min + ' ' + $scope.page_data.msgs[2] + ' ' + sec + ' ' + $scope.page_data.msgs[3];
                }
            } else {
                return original + ' ' + $scope.page_data.msgs[2] + ' ' + sec + ' ' + $scope.page_data.msgs[3];
            }
        } else {
            return original + ' ' + $scope.page_data.msgs[3];
        }
    };
    $scope.searchInData = function () {
        if ($scope.search === '') {
            $scope.variables = $scope.page_data.data_list;
        } else {
            $scope.variables = [];
            for (var i = 0; i < $scope.page_data.data_list.length; i++) {
                var item = $scope.page_data.data_list[i];
                if (item[0].indexOf($scope.search) > -1 || item[1].indexOf($scope.search) > -1) {
                    $scope.variables.push(item);
                }
            }
        }
    };
}]);
JspMyAdminApp.controller('ServerCharsetController', ['$rootScope', '$scope', 'AC1Resource', function ($rootScope, $scope, AC1Resource) {
    $rootScope.menuIndex = 1;
    $rootScope.menuActiveIndex = 8;
    $scope.page_data = {};
    AC1Resource.query({}, function (response) {
        if (response.status === 0) {
            $scope.page_data = response.data;
        }
    });
}]);
JspMyAdminApp.controller('ServerEngineController', ['$rootScope', '$scope', 'AC2Resource', function ($rootScope, $scope, AC2Resource) {
    $rootScope.menuIndex = 1;
    $rootScope.menuActiveIndex = 9;
    $scope.page_data = {};
    AC2Resource.query({}, function (response) {
        if (response.status === 0) {
            $scope.page_data = response.data;
        }
    });
}]);
JspMyAdminApp.controller('ServerPluginsController', ['$rootScope', '$scope', 'AC3Resource', function ($rootScope, $scope, AC3Resource) {
    $rootScope.menuIndex = 1;
    $rootScope.menuActiveIndex = 10;
    $scope.page_data = {};
    AC3Resource.query({}, function (response) {
        if (response.status === 0) {
            $scope.page_data = response.data;
        }
    });
}]);
JspMyAdminApp.controller('ServerVariablesController', ['$rootScope', '$scope', 'AC4Resource', function ($rootScope, $scope, AC4Resource) {
    $rootScope.menuIndex = 1;
    $rootScope.menuActiveIndex = 7;
    $scope.page_data = {};
    $scope.variables = [];
    $scope.input_data = {};
    $scope.loadVariables = function () {
        AC4Resource.query({}, function (response) {
            if (response.status === 0) {
                $scope.page_data = response.data;
                $scope.initVariables();
            }
        });
    };
    $scope.initVariables = function () {
        $scope.variables = $scope.page_data.data_list;
        $scope.search = '';
    };
    $scope.searchInData = function () {
        if ($scope.search === '') {
            $scope.variables = $scope.page_data.data_list;
        } else {
            $scope.variables = [];
            for (var i = 0; i < $scope.page_data.data_list.length; i++) {
                var item = $scope.page_data.data_list[i];
                if (item[0].indexOf($scope.search) > -1 || item[1].indexOf($scope.search) > -1 || item[2].indexOf($scope.search) > -1) {
                    $scope.variables.push(item);
                }
            }
        }
    };
    $scope.showInfoDialog = function (index) {
        var info = $scope.variables[index];
        $scope.scopes = info[2].split(',');
        if ($scope.scopes.length == 2) {
            $scope.scopes[2] = info[2];
        }
        $scope.input_data = {
            name: info[0],
            value: info[1],
            scope: $scope.scopes[0]
        };
        $scope.showInfo = true;
    };
    $scope.closeInfoDialog = function () {
        $scope.showInfo = false;
    };
    $scope.saveVariable = function () {
        $scope.input_data.scope = $scope.input_data.scope.split(',');
        $scope.showInfo = false;
        $scope.input_data.token = JspMyAdminContext.token;
        AC4Resource.save({}, $scope.input_data, function (response) {
            if (response.status === 0) {
                $scope.loadVariables();
                $scope.input_data = {};
            }
        });
    };
    $scope.loadVariables();
}]);