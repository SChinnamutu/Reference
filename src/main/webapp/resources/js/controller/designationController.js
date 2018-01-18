(function(angular) {
	/* Designation Controller*/
	var DesignationController = function($scope, $controller, DTColumnBuilder){
		var _this = this;
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('designation').withTitle('Job Title'),
            DTColumnBuilder.newColumn('sbu').withTitle('SBU')
        ];
		
		var vm = {
			'title' : 'Job Title',
		    'formId' : 'designationForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addDesignation'],
		    'updateUrl' : perfUrl['updateDesignation'],
		    'deleteUrl' : perfUrl['deleteDesignation'],
		    'loadListUrl': perfUrl['loadDesignations']
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	DesignationController.$inject = ['$scope','$controller', 'DTColumnBuilder'];
	mainApp.controller('designationController', DesignationController);
})(angular);