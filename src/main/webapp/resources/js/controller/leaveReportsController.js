(function(angular) {
	var lr, splitList, data, scope;
	mainApp.controller('wfhReportController', function($scope, $controller) {
	    $scope.title="WFH";
	    $scope.requestType="WFH";
	    angular.extend(this, $controller('leaveReportController', {
	        $scope: $scope
	    }));
	});
	
	mainApp.controller('ptoReportController', function($scope, $controller) {
	    $scope.title="PTO";
	    $scope.requestType="PTO";
	    $scope.isPto = true;
	    angular.extend(this, $controller('leaveReportController', {
	        $scope: $scope
	    }));
	});
	
	mainApp.controller('leaveReportController', function($scope, moment, leaveAPIservice, employeeAPIservice) {
	    scope = $scope;
	    $scope.data = {};
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
	    $scope.data.requestType = $scope.requestType;
	    
	    $scope.validate = function(){
    		perfUtils.getInstance().compareDate();
    	};
	    
	    $scope.searchLeave = function(){
	        leaveAPIservice.loadAllLeaveReport($scope.data).success(function(response) {
	        	if($scope.title === 'PTO'){
	        		$.each(response.entity, function(i, val){	        	
	        			val[0] = $scope.getCarryLeaves(val);
		        		val[5] = Math.round(($scope.getLeaveBalance(new Date(val[5]).getTime())*1.67)*2)/2;
		        	});
	        	}
	            lr.dtInstance.DataTable.clear().draw();
	            lr.dtInstance.DataTable.rows.add(response.entity).draw();
	        });
	    };
	
	    $scope.getCarryLeaves = function(val){
	    	var empJoinYear = moment(val[5]).year();
	    	var carriedOn = 0;
	    	var prevYear = new Date($scope.data.startsAt).getFullYear()
	    	if($scope.title === 'PTO' && empJoinYear < prevYear){
    			var leaves = val[0]/8;
    			if((prevYear-1) === empJoinYear){
    				var prevLeaves = Math.round((12-moment(val[5]).month())*1.67).toFixed(1);
    				return carriedOn = (prevLeaves - leaves) > 10? 10:(prevLeaves - leaves);
    			} else
    				return carriedOn = (20 - leaves) > 10? 10:(20 - leaves);
	    	}
	    	return carriedOn;
	    };
	    
	    $scope.getLeaveBalance = function(joinDt){
	        var startDt = new Date($scope.data.startsAt);
	        var endDt = new Date($scope.data.endsAt);
	        var joinDate = moment(joinDt).year()+" "+(moment(joinDt).month()+1)+" "+"01";
	        var stDate = startDt.getFullYear()+" "+(startDt.getMonth()+1)+" 01";
	        var endDate = endDt.getFullYear()+" "+(endDt.getMonth()+1)+" 31";
	        if(joinDt >= startDt.getTime() && joinDt <= endDt.getTime()){
	        	return (moment(endDate, "yyyy MM dd").diff(moment(joinDate, "yyyy MM dd"), 'months', true));
	        } else if(joinDt <= startDt.getTime()){
	        	return (moment(endDate, "yyyy MM dd").diff(moment(stDate, "yyyy MM dd"), 'months', true));
	        } else {
	        	return 0;
	        }
	    };
	    
	    $(document).on('click', '#leaveReportTable table.dataTable tbody tr', function () {
	    	$scope.data.empId = $(this).find('td:eq(0)').html();
	    	leaveAPIservice.loadLeaveReport($scope.data).success(function(response) {
	        	splitList.dtInstance.DataTable.clear().draw();
	        	splitList.dtInstance.DataTable.rows.add(response.entity).draw();
	            $('#leaveTableList').modal();
	        });
	    });
	    
	    employeeAPIservice.loadEmployees().success(function(response) {
	        $scope.employees = response.entity;
	    });
	});
	
	mainApp.controller('leaveReportControllerTable', leaveReportControllerTable);
	
	function leaveReportControllerTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, leaveAPIservice) {
	    lr = this;
	    lr.dtColumns = [
	        DTColumnBuilder.newColumn('pk').withTitle('id').notVisible().renderWith(function(data, type, full) {
	        	return full[1];
	        }),
	        DTColumnBuilder.newColumn('employeeId').withTitle('Employee Id').renderWith(function(data, type, full) {
	        	return full[1];
	        }),
	        DTColumnBuilder.newColumn('employeeView').withTitle('Employee Name').renderWith(function(data, type, full) {
	            return full[2]+' '+full[3];
	        }),
	        DTColumnBuilder.newColumn('hours').withTitle('Leaves Taken').renderWith(function(data, type, full) {
	            return (full[4]/8);
	        })
	    ];
	    if($scope.title === 'PTO'){
	    	lr.dtColumns.push(DTColumnBuilder.newColumn('balance').withTitle('Total Leaves').renderWith(function(data, type, full) {
	    		return full[5];
	        }),
	        DTColumnBuilder.newColumn('balance').withTitle('Carried Leaves').renderWith(function(data, type, full) {
	            return full[0];
	        }));
	    }
	    
	    var paramObj = {
	         "vm" : lr,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : leaveAPIservice,
	         'editFormId' : 'leaveModal',
	         'actions': false
	    };
	    perfDatatable.loadTable.init(paramObj);console.log('in search ', $scope.title);
	}
	
	mainApp.controller('leaveSplitUpControllerTable', leaveSplitUpControllerTable);
	
	function leaveSplitUpControllerTable($scope, $compile, DTOptionsBuilder, DTColumnBuilder, leaveAPIservice) {
		splitList = this;
		splitList.dtColumns = [
	        DTColumnBuilder.newColumn('title').withTitle('Title'),
	        DTColumnBuilder.newColumn('employeeView').withTitle('Employee Name').renderWith(function(data, type, full) {
	            return full.employeeView.firstName+' '+full.employeeView.lastName;
	        }),
	        DTColumnBuilder.newColumn('requestType').withTitle('Type').renderWith(function(data, type, full) {
	            return full.requestType;
	        }),
	        DTColumnBuilder.newColumn('comments').withTitle('Comments').renderWith(function(data, type, full) {
	            return full.comments;
	        }),
	        DTColumnBuilder.newColumn('startsAt').withTitle('Starts').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY hh:mm A");
	        }),
	        DTColumnBuilder.newColumn('endsAt').withTitle('Ends').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY hh:mm A");
	        }),
	        DTColumnBuilder.newColumn('hours').withTitle('Days').renderWith(function(data) {
	            return (data/8);
	        })
	     ];
	     var paramObj = {
	         "vm" : splitList,
	         "scope" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : leaveAPIservice,
	         'editFormId' : 'leaveModal',
	         'actions': false,
	         "sortCol" : 3
	     };
	     perfDatatable.loadTable.init(paramObj);
	}
})(angular);