(function(angular) {
	var em, repJb, columnBuilder, brIndex, data, scope, paramObj;
	/*Reports by Job title Controller*/
	mainApp.controller('reportsJobtitleController', function($scope, reportJobtitleAPIservice, employeeAPIservice) {
	    scope = $scope;
	    $scope.data = {};
	    $scope.brIndex = 0;
	
	    $scope.stDate = {
	        opened: false
	    };
	    $scope.endDate = {
	        opened: false
	    };
	    $scope.reportStartDate = function() {
	        $scope.stDate.opened = true;
	    };
	    $scope.reportEndDate = function() {
	        $scope.endDate.opened = true;
	    };
	    
	    scope.startsAt = new Date(new Date().getFullYear()+'-01-01');
	    scope.endsAt = new Date(new Date().getFullYear()+'-12-31');
	
	    $('table').on('click', 'tbody tr', function () {
	        if($scope.brIndex <= 1){
	            if($(this).find('td:first').html() === 'CHENNAI CONSULTING'
	                || $(this).find('td:first').html() === 'CHENNAI ADMIN'){
	                if($scope.brIndex === 0){
	                    $('#jobTitleBreadCrumb').append('<li>'+$(this).find('td:first').html()+'</li>');
	                    $scope.sbu = $(this).find('td:first').html();
	                    $scope.brIndex++;
	                    configTable(repJb);
	                }
	            } else {
	                if($scope.brIndex === 1){
	                    $('#jobTitleBreadCrumb').append('<li>'+$(this).find('td:first').html()+'</li>');
	                    $scope.designation = $(this).find('td:first').html();
	                    $scope.brIndex++;
	                    configTable(repJb);
	                }
	            }
	        }
	    });
	
	    $('#jobTitleBreadCrumb').on('click', 'li', function(){
	        $scope.brIndex = $('#jobTitleBreadCrumb li').index(this);
	        $('#jobTitleBreadCrumb li:gt('+$scope.brIndex+')').remove();
	        configTable(repJb);
	    });
	
	    function configTable(repJb){
	        if($scope.brIndex === 0){
	            $scope.sbu = null;
	            $scope.designation = null;
	            if(repJb){
	                repJb.dtInstance.dataTable.fnSetColumnVis(0, true);
	                repJb.dtInstance.dataTable.fnSetColumnVis(1, false);
	            }
	        } else if($scope.brIndex === 1){
	            $scope.designation = null;
	            if(repJb){
	                repJb.dtInstance.dataTable.fnSetColumnVis(0, false);
	                repJb.dtInstance.dataTable.fnSetColumnVis(1, true);
	            }
	        } else if($scope.brIndex === 2){
	            $scope.loadEmployeeByDesHistory();
	        }
	        if($scope.brIndex <= 1)
	            $scope.loadBySbu();
	    }
	
	    $scope.loadBySbu = function(){
	        reportJobtitleAPIservice.reportsLoadBySbu(scope.startsAt.getTime(), scope.endsAt.getTime(),
	                $scope.sbu, $scope.designation).success(function (response) {
	            repJb.dtInstance.DataTable.clear().draw();
	            repJb.dtInstance.DataTable.rows.add(response.entity).draw();
	        });
	    };
	
	    $scope.loadEmployeeByDesHistory = function(){
	        employeeAPIservice.loadEmployeeByDesHistory(scope.startsAt.getTime(), scope.endsAt.getTime(),
	                $scope.designation).success(function (response) {
	            $scope.employees = response.entity;
	            em.dtInstance.DataTable.clear().draw();
	            em.dtInstance.DataTable.rows.add($scope.employees).draw();
	        });
	    };
	
	    $scope.validate = function(){
	    	perfUtils.getInstance().compareDate();
	    };
	    
	    configTable();
	
	    $scope.searchLeave = function(){
	        configTable();
	    };
	});
	
	mainApp.controller('reportJobTitleControllerTable', reportJobTitleControllerTable);
	
	function reportJobTitleControllerTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, reportJobtitleAPIservice) {
	    repJb = this;
	    columnBuilder = DTColumnBuilder;
	    repJb.dtColumns = [
	        DTColumnBuilder.newColumn('sbu').withTitle('SBU'),
	        DTColumnBuilder.newColumn('designation').withTitle('Job Title').notVisible(),
	        DTColumnBuilder.newColumn('employeeCount').withTitle('Count')
	    ];
	    paramObj = {
	        "vm" : repJb,
	        "scope" : $scope,
	        "compile" : $compile,
	        "DtOptionsBuilder" : DTOptionsBuilder,
	        "DTColumnBuilder" : DTColumnBuilder,
	        "service" : reportJobtitleAPIservice,
	        "actions" : false
	    };
	    perfDatatable.loadTable.init(paramObj);
	}
	
	mainApp.controller('employeeTableCtrl', employeeTableCtrl);
	
	function employeeTableCtrl($scope, $compile, DTOptionsBuilder, DTColumnBuilder, employeeAPIservice) {
	    em = this;
	    em.dtColumns = [
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
	    paramObj = {
	        "vm" : em,
	        "scope" : $scope,
	        "compile" : $compile,
	        "DtOptionsBuilder" : DTOptionsBuilder,
	        "DTColumnBuilder" : DTColumnBuilder,
	        "service" : employeeAPIservice,
	        'sortCol': 1,
	        "actions" : false
	    };
	    perfDatatable.loadTable.init(paramObj);
	}
})(angular);