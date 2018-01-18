(function(angular) {
	/* Components controller */
	var ComponentsController = function($scope, $controller, DTColumnBuilder){
		var _this = this;
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('componentName').withTitle('Components')
        ];
		
		var vm = {
			'title' : 'Component',
		    'formId' : 'componentsForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addComponent'],
		    'updateUrl' : perfUrl['updateComponent'],
		    'deleteUrl' : perfUrl['deleteComponent'],
		    'loadListUrl': perfUrl['loadComponents']
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	ComponentsController.$inject = ['$scope','$controller', 'DTColumnBuilder'];
	mainApp.controller('componentsController', ComponentsController);
})(angular);