(function(angular) {
	/* Roles controller */
	var RolesController = function($scope, $controller, DTColumnBuilder){
		var _this = this;
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('roleName').withTitle('Roles')
        ];
		
		var vm = {
			'title' : 'Role',
		    'formId' : 'rolesForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addRoles'],
		    'updateUrl' : perfUrl['updateRoles'],
		    'deleteUrl' : perfUrl['deleteRoles'],
		    'loadListUrl': perfUrl['loadRoles']
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	RolesController.$inject = ['$scope','$controller', 'DTColumnBuilder'];
	mainApp.controller('rolesController', RolesController);
})(angular);