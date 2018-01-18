(function(angular) {
	/* Employee controller */
	var EmployeeController = function($scope, $controller, employeeAPIservice, DTColumnBuilder , $compile, DTOptionsBuilder) {
	    
		$scope.loadEmployees = function(){
			employeeAPIservice.loadEmployees().success(function (response) {
		        $scope.employees = response.entity;
		        $scope.dtInstance.DataTable.clear().draw();
		        $scope.dtInstance.DataTable.rows.add($scope.employees).draw();
		    });
		};
		$scope.loadEmployees();
	    $scope.addEmployee = function(){
	    	$scope.data = {};
	    	perfUtils.getInstance().resetForm();
	        $('#employeeForm').modal();
	    };
	
	    $scope.save = function(){
	        employeeAPIservice.addEmployee($scope.data).success(function (){
	        	perfUtils.getInstance().successMsg('Employee Saved Successfully!');
	            $scope.dtInstance.reloadData();
	            $('#employeeForm').modal('hide');
	        });
	    };
	    
	    $scope.update = function(){
	        employeeAPIservice.updateEmployee($scope.data).success(function (){
	        	$scope.dtInstance.dataTable.fnUpdate($scope.data, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
	            perfUtils.getInstance().successMsg('Employee updated Successfully!');
	            $('#employeeForm').modal('hide');
	        });
	    };
	
	    $scope.dtColumns = [
            DTColumnBuilder.newColumn('employeeId').withTitle('ID'),
            DTColumnBuilder.newColumn('firstName').withTitle('First Name').renderWith(function(data, type, full) {
                return full.firstName+' '+full.lastName;
            }),
            DTColumnBuilder.newColumn('superviserFirstName').withTitle('Supervisor').notVisible().renderWith(function(data, type, full) {
                return full.superviserFirstName+' '+full.superviserLastName;
            }),
            DTColumnBuilder.newColumn('email').withTitle('Email'),
            DTColumnBuilder.newColumn('designations.designation').withTitle('Designation')
        ];
	    
	    $scope.title = 'Employees';
        var paramObj = {
            "vm" : $scope,
            "compile" : $compile,
            "DtOptionsBuilder" : DTOptionsBuilder,
            "DTColumnBuilder" : DTColumnBuilder,
            "service" : employeeAPIservice,
            'editFormId' : 'employeeForm',
            'sortCol': 1,
            'sEmptyTable' : 'Loading..',
            "deleteRow" : false
        };
        perfDatatable.loadTable.init(paramObj);

	    angular.extend(this, $controller('empProfileController', {
	        $scope: $scope
	    }));
	};
	EmployeeController.$inject = ['$scope','$controller', 'employeeAPIservice', 'DTColumnBuilder' , '$compile', 'DTOptionsBuilder'];
	mainApp.controller('employeeController', EmployeeController);
})(angular);