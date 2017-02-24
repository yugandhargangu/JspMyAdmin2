'use strict';
JspMyAdminApp.factory('AB0Resource', [ '$resource', function($resource) {
	return $resource('/', {}, {
		query : {
			method : 'GET',
			url : contextPath + '/server/databases.text',
			isArray : false,
			transformResponse : AjaxHelper.generateResponse
		},
		save : {
        	method : 'POST',
        	url : contextPath + '/server/database/create.text',
        	transformResponse : AjaxHelper.generateResponse
        }
	});
} ]);
JspMyAdminApp.factory('AB1Resource', [ '$resource', function($resource) {
	return $resource('/', {}, {
		save : {
			method : 'POST',
			url : contextPath + '/server/database/drop.text',
			transformResponse : AjaxHelper.generateResponse
		}
	});
} ]);
JspMyAdminApp.controller('ServerDatabasesController', ['$rootScope', '$scope', 'AB0Resource', 'AB1Resource',
        function($rootScope, $scope, AB0Resource, AB1Resource) {
    $rootScope.menuIndex = 1;
    $rootScope.menuActiveIndex = 1;
	$scope.page_data = {};
	$scope.sort_input = {
	    type: ' ASC',
	    name: 'db_name'
	};
	$scope.input_data = {};
	$scope.temp_databases = [];
	$scope.databases = [];
	$scope.allCheck = false;
	$scope.loadDatabases = function(name) {
        if(name != ''){
            if($scope.sort_input.name === name) {
                if($scope.sort_input.type === ' DESC') {
                    $scope.sort_input.type = ' ASC';
                } else {
                    $scope.sort_input.type = ' DESC';
                }
            } else {
                $scope.sort_input.name = name;
                $scope.sort_input.type = ' ASC'
            }
        }
	    AB0Resource.query($scope.sort_input, function(response){
	        if(response.status === 0){
	            $scope.temp_databases = [];
                $scope.databases = [];
	            $scope.page_data = response.data;
	        }
	    });
	};
	$scope.showIcon = function (name, type){
	    return $scope.sort_input.name === name && $scope.sort_input.type === type;
	};
	$scope.calculateBytes = function(original){
	    if(original > 1024){
            original = original / 1024;
            if(original > 1024) {
                original = original / 1024;
                if(original > 1024){
                    return original.toFixed(2) + ' GB';
                } else {
                    return original.toFixed(2) + ' MB';
                }
            } else {
                return original.toFixed(2) + ' KB';
            }
	    } else {
	        return original + ' B';
	    }
	};
	$scope.checkAll = function() {
	    $scope.databases = [];
	    $scope.temp_databases = [];
        if($scope.allCheck) {
            if($scope.page_data.count > 0) {
                angular.forEach($scope.page_data.database_list, function(item){
                    $scope.temp_databases.push(item.database);
                    $scope.databases.push(item.database);
                });
            }
        }
	};
	$scope.checkOne = function(database) {
	    var index = $scope.databases.indexOf(database);
	    if(index > -1){
	        $scope.databases.splice(index, 1);
	    } else {
	        $scope.databases.push(database);
	    }
	    $scope.allCheck = false;
        if($scope.databases.length > 0 && $scope.databases.length === $scope.page_data.database_list.length){
            $scope.allCheck = true;
        }
    };
	$scope.dropDatabases = function() {
        AB1Resource.save({}, {token:token, databases:$scope.databases}, function(response) {
            if(response.status === 0 && !response.error) {
                $scope.loadDatabases('');
            }
        });
	};
	$scope.saveDatabase = function() {
	    $scope.input_data.token = token;
	    AB0Resource.save({},$scope.input_data, function(response) {
            if(response.status === 0 && !response.error) {
                $scope.input_data = {};
                $scope.loadDatabases('');
            }
	    });
	};
	$scope.loadDatabases('');
} ]);