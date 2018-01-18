(function(angular) {
	/* Project Controller*/
	var ProjectController = function($scope, $controller, DTColumnBuilder){
		var _this = this;
		$scope.stDate = {
	        opened: false
	    };
	    $scope.endDate = {
	        opened: false
	    };
	    $scope.prStartDate = function() {
	        $scope.stDate.opened = true;
	    };
	    $scope.prEndDate = function() {
	        $scope.endDate.opened = true;
	    };
	    
	    $scope.validate = function(){
    		perfUtils.getInstance().compareDate();
    	};
	    
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('projectName').withTitle('Projects'),
	        DTColumnBuilder.newColumn('startDate').withTitle('Start Date').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        }),
	        DTColumnBuilder.newColumn('endDate').withTitle('End Date').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        })
        ];
		
		var vm = {
			'title' : 'Project',
		    'formId' : 'projectForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['addProject'],
		    'updateUrl' : perfUrl['updateProject'],
		    'deleteUrl' : perfUrl['deleteProject'],
		    'loadListUrl': perfUrl['loadProjects']
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	ProjectController.$inject = ['$scope','$controller', 'DTColumnBuilder'];
	mainApp.controller('projectController', ProjectController);
})(angular);