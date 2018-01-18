(function(angular) {
	/* EmpGoals Controller*/
	var EmpGoalsController = function($scope, $controller, DTColumnBuilder){
		var _this = this;
		$scope.projectGoals = [];
		$scope.addProject = function(){
			$scope.projectGoals.push({
				Name : $scope.name,
				Desc : $scope.desc,
				selfEvaln : $scope.selfEvaln,
				pmEvaln : $scope.pmEvaln
			})
		};
		
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('year').withTitle('Year'),
            DTColumnBuilder.newColumn('projectRating').withTitle('Project Rating'),
            DTColumnBuilder.newColumn('utilRating').withTitle('Utiliziation Rating')
        ];
		
		var vm = {
			'title' : 'Goals',
		    'formId' : 'goalsForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl[''],
		    'updateUrl' : perfUrl[''],
		    'deleteUrl' : perfUrl[''],
		    'loadListUrl': ''
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	EmpGoalsController.$inject = ['$scope','$controller', 'DTColumnBuilder'];
	mainApp.controller('empGoalsController', EmpGoalsController);
})(angular);