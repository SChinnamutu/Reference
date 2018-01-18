/*Header Controller*/
mainApp.controller('headerController',['$scope','user','profileAPIservice','dashboardAPIservice', 
    function($scope, user, profileAPIservice, dashboardAPIservice) {
	    profileAPIservice.getProfileDetails().success(function (response) {
	        $scope.user = response.entity;
	        user.loggedUser = response.entity;
	    });
	    dashboardAPIservice.getVersion().success(function (response) {
	        $('#version').html('Version: '+response.entity);
	    });
	}
]);
/*Menu Controller*/
mainApp.controller('menuController', ['$scope','user',
    function($scope, user) {
		
	}]
);
/* Profile controller */
mainApp.controller('profileController', ['$scope', '$controller', 'profileAPIservice', 'employeeAPIservice', function($scope, $controller, profileAPIservice, employeeAPIservice) {
     $scope.data = null;

     $scope.isProfile = true;     
     employeeAPIservice.loadEmployees().success(function (response) {
        $scope.employees = response.entity;
        profileAPIservice.getProfileDetails().success(function (profileResponse) {
            $scope.data = profileResponse.entity;
        });
     });

     $scope.submit = function() {
       if ($scope.data) {
          employeeAPIservice.updateEmployee($scope.data).success(function () {
             perfUtils.getInstance().successMsg('Profile updated Successfully!');
          });
       }
     };
     angular.extend(this, $controller('empProfileController', {
         $scope: $scope
     }));
}]);

mainApp.controller('empProfileController', ['$scope', 'designationAPIservice', function($scope, designationAPIservice) {
    $scope.dob = {
        opened: false
    };

    $scope.dobDate = function() {
        $scope.dob.opened = true;
    };

    $scope.joinDate = {
        opened: false
    };

    $scope.joinDate = function() {
        $scope.joinDate.opened = true;
    };
    
    $scope.jobEffDate = {
        opened: false
    };

    $scope.jobEffDate = function() {
        $scope.jobEffDate.opened = true;
    };
    
    $scope.lastWorkDate = {
        opened: false
    };

    $scope.lastWorkDate = function() {
        $scope.lastWorkDate.opened = true;
    };

    $scope.validate = function(){
		perfUtils.getInstance().validateFutureDate(['birthDate', 'joinDate']);
	};
    
    designationAPIservice.getDesignationDetails().success(function (response) {
        $scope.designations = response.entity;
    });
}]);
mainApp.factory('profileAPIservice', ['$http',function($http) {
    var profileAPI = {};
    profileAPI.getProfileDetails = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployee']
        });
    };
    return profileAPI;
}]);
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
mainApp.factory('employeeAPIservice',['$http', function($http) {
    var employeeAPI = {};
    employeeAPI.loadById = function(empId) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployeeById']+empId
        });
    };
    employeeAPI.loadAllEmployees = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadAllEmployees']
        });
    };
    employeeAPI.loadEmployees = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployees']
        });
    };
    employeeAPI.addEmployee = function(employee) {
        return $http({
          method: 'post',
          data : employee,
          url: perfUrl['addEmployee']
        });
    };
    employeeAPI.updateEmployee = function(employee) {
        return $http({
          method: 'put',
          data : employee,
          url: perfUrl['updateEmployee']
        });
    };
    employeeAPI.loadEmployeeByDesHistory = function(fromDate, toDate, designation) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployeeByDesHistory'].replace('{fromDate}', fromDate).replace('{toDate}', toDate).replace('{designation}', designation)
        });
    };
    return employeeAPI;
}]);
/*Dashboard Controller*/
mainApp.controller('dashboardController', function($scope, dashboardAPIservice) {
}).controller('approvalReqCtrl', function() {
});
mainApp.factory('dashboardAPIservice',['$http', function($http) {
    var dashboardAPI = {};
    dashboardAPI.getVersion = function() {
        return $http({
          ContentType: 'application/text',
          method: 'get',
          url: perfUrl['getVersion']
        });
    };
    return dashboardAPI;
}]);
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
mainApp.factory('designationAPIservice', ['$http', function($http) {
    var designationAPI = {};
    designationAPI.getDesignationDetails = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadDesignations']
        });
    };
    designationAPI.loadById = function(id) {
        return $http({
          method: 'get',
          url: perfUrl['loadDesignationById']+id
        });
    };
    designationAPI.addDesignation = function(designation) {
        return $http({
          method: 'post',
          data : designation,
          url: perfUrl['addDesignation']
        });
    };
    designationAPI.updateDesignation = function(designation) {
        return $http({
          method: 'put',
          data : designation,
          url: perfUrl['updateDesignation']
        });
    };
    designationAPI.deleteDesignation = function(designation) {
        return $http({
          method: 'put',
          data : designation,
          url: perfUrl['deleteDesignation']
        });
    };
    return designationAPI;
}]);
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
mainApp.factory('projectAPIservice', ['$http', function($http) {
    var projectAPI = {};
    projectAPI.loadById = function(projectPk) {
        return $http({
          method: 'get',
          url: perfUrl['loadProjectById']+projectPk
        });
    };
    projectAPI.loadProjects = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadProjects']
        });
    };
    projectAPI.addProject = function(project) {
        return $http({
          method: 'post',
          data : project,
          url: perfUrl['addProject']
        });
    };
    projectAPI.updateProject = function(project) {
        return $http({
          method: 'put',
          data : project,
          url: perfUrl['updateProject']
        });
    };
    projectAPI.deleteProject = function(project) {
        return $http({
          method: 'put',
          data : project,
          url: perfUrl['deleteProject']
        });
    };
    return projectAPI;
}]);
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
mainApp.factory('rolesAPIservice', ['$http', function($http) {
    var rolesAPI = {};
    rolesAPI.getRolesDetails = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadRoles']
        });
    };
    rolesAPI.loadById = function(id) {
        return $http({
          method: 'get',
          url: perfUrl['loadRolesById']+id
        });
    };
    rolesAPI.addRoles = function(roles) {
        return $http({
          method: 'post',
          data : roles,
          url: perfUrl['addRoles']
        });
    };
    rolesAPI.updateRoles = function(roles) {
        return $http({
          method: 'put',
          data : roles,
          url: perfUrl['updateRoles']
        });
    };
    rolesAPI.deleteRoles = function(roles) {
        return $http({
          method: 'put',
          data : roles,
          url: perfUrl['deleteRoles']
        });
    };
    return rolesAPI;
}]);
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
mainApp.factory('componentsAPIservice', ['$http',function($http) {
    var componentsAPI = {};
    componentsAPI.getComponentsDetails = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadComponents']
        });
    };
    componentsAPI.loadById = function(id) {
        return $http({
          method: 'get',
          url: perfUrl['loadComponentsById']+id
        });
    };
    componentsAPI.addComponents = function(components) {
        return $http({
          method: 'post',
          data : components,
          url: perfUrl['addComponent']
        });
    };
    componentsAPI.updateComponents = function(components) {
        return $http({
          method: 'put',
          data : components,
          url: perfUrl['updateComponent']
        });
    };
    componentsAPI.deleteComponents = function(components) {
        return $http({
          method: 'put',
          data : components,
          url: perfUrl['deleteComponent']
        });
    };
    return componentsAPI;
}]);
(function(angular) {
	/* ProjectMembersController*/
	var ProjectMembersController = function($scope, $controller, DTColumnBuilder, projectAPIservice, employeeAPIservice){
		var _this = this;
		$scope.pmStDate = {
	        opened: false
	    };
	    $scope.pmEndDate = {
	        opened: false
	    };
	    $scope.pmStartDate = function() {
	        $scope.pmStDate.opened = true;
	    };
	    $scope.pmEndDate = function() {
	        $scope.pmEndDate.opened = true;
	    };
	    projectAPIservice.loadProjects().success(function(response) {
	        $scope.projects = response.entity;
	    });

	    employeeAPIservice.loadAllEmployees().success(function(response) {
	        $scope.employees = response.entity;
	    });
	    
	    $scope.validate = function(){
    		perfUtils.getInstance().compareDate();
    		console.log('st dt ', $scope.data.projectId.startDate);
    		/*if(new Date(moment.utc($('#startDt').val(), "DD-MMM-YYYY")).getTime() > new Date(moment.utc($('#endDt').val(), "DD-MMM-YYYY")).getTime()){
        		var errorMsg = '<p class="text-danger"></p>';
        		$('#startDt').parent().addClass('has-error');
            	$(errorMsg).html($('#startDt').attr('name')+' must be lesser than '+$('#endDt').attr('name')+'.').insertAfter($('#startDt'));
        	}*/
    	};
    	
		$scope.dtColumns = [
            DTColumnBuilder.newColumn('projectId.projectName').withTitle('Project Name'),
	        DTColumnBuilder.newColumn('employeeId').withTitle('Employee Name').renderWith(function(full) {
	            return full.firstName+', '+full.lastName;
	        }),
	        DTColumnBuilder.newColumn('employeeId.designations.designation').withTitle('Designation'),
	        DTColumnBuilder.newColumn('dtStarted').withTitle('Start Date').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        }),
	        DTColumnBuilder.newColumn('dtEnded').withTitle('End Date').renderWith(function(data) {
	            return moment(data).format("DD-MMM-YYYY");
	        })
        ];
		
		var vm = {
			'title' : 'Project Members',
		    'formId' : 'projectMembersForm',
		    'scope' : $scope,
		    'addUrl' : perfUrl['saveProjectMember'],
		    'updateUrl' : perfUrl['updateProjectMember'],
		    'deleteUrl' : perfUrl['deleteProjectMember'],
		    'loadListUrl': perfUrl['loadAllProjectMembers']
		};
		angular.extend(this, $controller('AbstractController', {_this: _this, vm:vm}));
	};
	ProjectMembersController.$inject = ['$scope','$controller', 'DTColumnBuilder', 'projectAPIservice', 'employeeAPIservice'];
	mainApp.controller('projectMembersController', ProjectMembersController);
})(angular);
mainApp.factory('projectMemberAPIservice', ['$http', function($http) {
    var projectMembersAPI = {};
    projectMembersAPI.loadById = function(projectMemberPk) {
        return $http({
          method: 'get',
          url: perfUrl['loadProjectMemberById']+projectMemberPk
        });
    };
    projectMembersAPI.loadAllProjectMembers = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadAllProjectMembers']
        });
    };
    projectMembersAPI.saveProjectMember = function(projectMember) {
        return $http({
          method: 'post',
          data : projectMember,
          url: perfUrl['saveProjectMember']
        });
    };
    projectMembersAPI.updateProjectMember = function(projectMember) {
        return $http({
          method: 'put',
          data : projectMember,
          url: perfUrl['updateProjectMember']
        });
    };
    projectMembersAPI.deleteProjectMember = function(projectMember) {
        return $http({
          method: 'put',
          data : projectMember,
          url: perfUrl['deleteProjectMember']
        });
    };
    return projectMembersAPI;
}]);
mainApp.controller('importPtoController', function($scope, ptoAPIService){
	
	$(document).on('change', ':file', function() {
	    var input = $(this),
	        numFiles = input.get(0).files ? input.get(0).files.length : 1,
	        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	    input.trigger('fileselect', [numFiles, label]);
	});
	
	$(':file').on('fileselect', function(event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;
        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }
    });
	
    $scope.uploadFile = function(){
    	var file = $scope.uploadPto;
        ptoAPIService.uploadFileToUrl(file).success(function(data){
            $('input[type="file"]').val('');
            if(data)
            	perfUtils.getInstance().successMsg('PTO document processed successfully!');
            else
                $scope.msg="An error occurred during document processing!";
        });
    };
    
    /*$scope.carryLeaves = function(){
    	console.log('data ', $scope.data);
    	ptoAPIService.carryLeaves($scope.data.year).success(function(data){
    		perfUtils.getInstance().successMsg('Leave Balance processed successfully!');
    	});
    };*/
});
mainApp.factory('ptoAPIService',['$http', function ($http) {
    var importPtoAPI = {};
    importPtoAPI.uploadFileToUrl = function(file) {
         var fd = new FormData();
         fd.append('uploadFiles',file);
         return $http({
             method: 'post',
             data : fd,
             url: perfUrl['importPto'],
             transformRequest: angular.identity,
             headers: {'Content-Type': undefined}
         });
    };
    importPtoAPI.carryLeaves = function(year){
    	return $http({
            method: 'post',
            url: perfUrl['carryLeaves'].replace('{year}', year)
        });
    };
    return importPtoAPI;
}]);
(function(angular) {
	mainApp.controller('wfhController', function($scope, $controller) {
	    var scope = this;
	    scope.events = [];
	    $scope.title="WFH";
	    $scope.leaveType="WFH";
	    $scope.btnTheme = "btn-danger";
	    angular.extend(this, $controller('leaveController', {
	        $scope: $scope
	    }));
	});
	
	mainApp.controller('ptoController', function($scope, $controller) {
	    var scope = this;
	    scope.events = [];
	    $scope.title="PTO";
	    $scope.leaveType="PTO";
	    $scope.isPto = true;
	    $scope.btnTheme = "btn-success";
	    angular.extend(this, $controller('leaveController', {
	        $scope: $scope
	    }));
	});
	
	/**
	 * AngularJS default filter with the following expression:
	 * "person in people | filter: {name: $select.search, age: $select.search}"
	 * performs an AND between 'name: $select.search' and 'age: $select.search'.
	 * We want to perform an OR.
	 */
	mainApp.filter('propsFilter', function() {
	  return function(items, props) {
	    var out = [];
	    if (angular.isArray(items)) {
	      var keys = Object.keys(props);
	      items.forEach(function(item) {
	        var itemMatches = false;
	        for (var i = 0; i < keys.length; i++) {
	          var prop = keys[i];
	          var text = props[prop].toLowerCase();
	          if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
	            itemMatches = true;
	            break;
	          }
	        }
	        if (itemMatches) {
	          out.push(item);
	        }
	      });
	    } else {
	      // Let the output be the input untouched
	      out = items;
	    }
	    return out;
	  };
	});
	
	var LeaveController = function($scope, moment, user, leaveAPIservice, employeeAPIservice, calendarConfig, DTColumnBuilder, $compile, DTOptionsBuilder) {
	    var obj = this;
	    //These variables MUST be set as a minimum for the calendar to work
	    obj.calendarView = 'month';
	    obj.viewDate = new Date();
	    obj.viewChangeEnabled = true;
	    obj.checkLeaves = 'all';
	    obj.calYear = new Date(obj.viewDate).getFullYear();
	    obj.calMonth = new Date(obj.viewDate).getMonth();
	    scope = $scope;
	    $scope.data = {};
	    $scope.employees = [];
	    $scope.employeesList;
	    $scope.leaveBalance = 0;
	    $scope.data.requestType = $scope.leaveType;
	    var eventArr = [];
	    calendarConfig.displayEventEndTimes = true;
	    calendarConfig.templates.calendarSlideBox = 'html/templates/calendarSlideBoxTemplate.html';
	    calendarConfig.templates.calendarMonthCell = 'html/templates/calendarMonthCell.html';
	    calendarConfig.templates.calendarMonthCellEvents = 'html/templates/calendarMonthCellEvents.html';
	
	    this.toggle = function($event, field, event) {
	      $event.preventDefault();
	      $event.stopPropagation();
	      event[field] = !event[field];
	    };
	
	    this.timeSpanClicked = function(calendarCell, $event){
	        if(calendarCell.events.length > 0){
	        	$scope.dtInstance.DataTable.clear().draw();
	        	$scope.dtInstance.DataTable.rows.add(calendarCell.events).draw();
	            $('#leaveList').modal();
	        }
	    };
	
	    $scope.toggleYear = function(calendarDate){
	        if(obj.calYear !== new Date(calendarDate).getFullYear()){
	            obj.calYear = new Date(calendarDate).getFullYear();
	            $scope.toggleLeave(obj.checkLeaves);
	        }
	        obj.calMonth = new Date(calendarDate).getMonth();
	        $scope.getLeaveBalance();
	        $scope.getCarryLeaves();
	    };
	    
	    $scope.getCarryLeaves = function(){
	    	var empJoinYear = moment(user.loggedUser.joinDate).year();
	    	$scope.carriedOn = 0;
	    	if($scope.isPto && empJoinYear < obj.calYear){
	    		leaveAPIservice.getCarryLeaves((obj.calYear-1)).success(function(data){
	    			var leaves = data.entity/8;
	    			if((obj.calYear-1) === empJoinYear){
	    				var prevLeaves = round((12-moment(user.loggedUser.joinDate).month())*1.67).toFixed(1);
    					$scope.carriedOn = (prevLeaves - leaves) > 10? 10:(prevLeaves - leaves);
	    			} else
	    				$scope.carriedOn = (20 - leaves) > 10? 10:(20 - leaves);
				});	
	    	}
	    };
	
	    $scope.getCarryLeaves();
	    
	    $scope.openModal = function(){
	        $('#leaveModal').modal();
	    };
	
	    $scope.applyLeave = function(){
	        $scope.data = {};
	        $scope.data.employeeId = user.loggedUser.pk;
	        $scope.data.notificationToList = [];
	        $scope.data.notificationToList.push($scope.employees[getIndex(user.loggedUser.pk)], $scope.employees[getIndex(user.loggedUser.supervisor)]);
	        $scope.data.requestType = $scope.leaveType;
	        $scope.data.dtEndHalf = "SECOND";
	        $scope.data.dtFromHalf = "FIRST";
	        $scope.openModal();
	    };
	
	    getIndex = function(pk){
	    	var index = $scope.employees.findIndex(function(item, i){
	      	  return item.pk === pk;
	      	});
	    	return index;
	    };
	    
	    employeeAPIservice.loadEmployees().success(function(response) {
	        $scope.employeesList = response.entity;
	        $scope.employees = response.entity;
	        $scope.toggleLeave(obj.checkLeaves);
	        $scope.getLeaveBalance();
	    });

	    $scope.toggleLeave = function(val){
	        obj.checkLeaves = val;
	        if(val === 'all'){
	            leaveAPIservice.loadAllLeaves($scope.leaveType, obj.calYear).success(function (response) {
	                $scope.displayLeave(response.entity);
	            });
	        } else {
	            leaveAPIservice.loadMyLeaves($scope.leaveType, obj.calYear).success(function (response) {
	                $scope.displayLeave(response.entity);
	            });
	        }
	    };
	    
	    $scope.validate = function(){
    		perfUtils.getInstance().compareDate();
    	};
	    
	    function round(value) {
	        return Math.round(value * 2) / 2;
	    }
	
	    $scope.getLeaveBalance = function(){
	        var joinDate = moment(user.loggedUser.joinDate).format("DD/MM/YYYY");
	        if(moment(joinDate,"DD/MM/YYYY").year() === obj.calYear){
	        	var month = (obj.calMonth+1)-moment(joinDate,"DD/MM/YYYY").month();
	        	month = month < 0?0:month;
	        	leaveAPIservice.getLeaveBalance($scope.leaveType, obj.calYear, (obj.calMonth+1)).success(function (response) {
		            $scope.leaveBalance = round(((response.entity)/8).toFixed(1))+"/"+round((month*1.67).toFixed(1));
		        });	
	        } else if(moment(joinDate,"DD/MM/YYYY").year() <= obj.calYear){
	        	var month = (obj.calMonth+1);
	        	leaveAPIservice.getLeaveBalance($scope.leaveType, obj.calYear, (obj.calMonth+1)).success(function (response) {
		            $scope.leaveBalance = round(((response.entity)/8).toFixed(1))+"/"+round((month*1.67).toFixed(1));
		        });
	        } else {
	        	$scope.leaveBalance = "0/0";
	        }
	    };
	
	    $scope.displayLeave = function(data){
	        $scope.scope.events.splice(0, $scope.scope.events.length);
	        $.each(data, function(i, val){
	             val.startsAt = new Date(val.startsAt);
	             val.endsAt = new Date(val.endsAt);
	             val.type = $scope.displayType;
	             var notifyListPk = [];
	             if(val.notificationToList){
	                 $.each(val.notificationToList, function(i, item){
	                     notifyListPk.push(item.pk);
	                 });
	                 val.notificationToList.splice(0, val.notificationToList.length);
	                 $.each(notifyListPk, function(i, item){
	                     val.notificationToList.push($scope.employees[getIndex(item)]);
	                 });
	             }
	             $scope.scope.events[val.pk] = val;
	             eventArr[val.pk] = i;
	         });
	    };
	
	    $scope.saveLeave = function(){
	        $scope.data.requestType = $scope.leaveType;
	        $.each($scope.data.notificationToList, function(i, val){
	            if(val['_uiSelectChoiceDisabled'] !== undefined){
	                delete val['_uiSelectChoiceDisabled'];
	            }
	        });
	        leaveAPIservice.applyLeave($scope.data).success(function (response) {
	            response.startsAt = new Date(response.entity.startsAt);
	            response.endsAt = new Date(response.entity.endsAt);
	            $scope.scope.events[response.entity.pk] = response.entity;
	            perfUtils.getInstance().successMsg($scope.title+" Saved Successfully!");
	            $('#leaveModal').modal('hide');
	            $scope.getLeaveBalance();
	        });
	    };
	
	    $scope.updateLeave = function(){
	        $.each($scope.data.notificationToList, function(i, val){
	            if(val['_uiSelectChoiceDisabled'] !== undefined){
	                delete val['_uiSelectChoiceDisabled'];
	            }
	        });
	        leaveAPIservice.updateLeave($scope.data).success(function (response) {
	            $.grep($scope.scope.events, function (element, index) {
	                if($scope.data.pk === element.pk){
	                    $scope.scope.events[index] = $scope.data;
	                }
	                return element.pk === $scope.data.pk;
	            });
	            $scope.data.hours = response.entity.hours;
	            $scope.dtInstance.dataTable.fnUpdate($scope.data, $scope.dtInstance.DataTable.$('tr.selected'), undefined, false);
	            perfUtils.getInstance().successMsg($scope.title+" Updated Successfully!");
	            $('#leaveModal').modal('hide');
	            $scope.getLeaveBalance();
	        });
	    };
	
	    $scope.deleteLeave = function(){
	        leaveAPIservice.deleteLeave($scope.data).success(function () {
	            $.grep($scope.scope.events, function (element, index) {
	                if($scope.data.pk === element.pk){
	                    $scope.scope.events.splice(index, 1);
	                }
	                return element.pk === $scope.data.pk;
	            });
	            $scope.message($scope.title+" Deleted Successfully!");
	            perfUtils.getInstance().successMsg($scope.title+" Deleted Successfully!");
	            $('#deleteLeave').modal('hide');
	            $scope.getLeaveBalance();
	        });
	    };
	
	    $scope.onDtChange = function(){
	        var time9 = 60*1000*60*9;
	        var time1_30 = 60*1000*60*13.5;
	        var time5_30 = 60*1000*60*17.5;
	        if($scope.data.startsAt && $scope.data.dtFromHalf) {
	            var stDt = new Date($scope.data.startsAt).toDateString();
	            if($scope.data.dtFromHalf === 'FIRST'){
	                stDt = new Date(stDt).getTime() + (time9);
	            } else {
	                stDt = new Date(stDt).getTime() + (time1_30);
	            }
	            $scope.data.startsAt = new Date(stDt);
	        }
	        if($scope.data.endsAt && $scope.data.dtEndHalf){
	            var endDt = new Date($scope.data.endsAt).toDateString();
	            if($scope.data.dtEndHalf === 'FIRST'){
	                endDt = new Date(endDt).getTime() + (time1_30);
	            } else {
	                endDt = new Date(endDt).getTime() + (time5_30);
	            }
	            $scope.data.endsAt = new Date(endDt);
	        }
	    };
	    
	    $scope.dtColumns = [
	        DTColumnBuilder.newColumn('title').withTitle('Title'),
	        DTColumnBuilder.newColumn('employeeView').withTitle('Employee Name').renderWith(function(data, type, full) {
	            return full.employeeView.firstName+' '+full.employeeView.lastName;
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
	         "vm" : $scope,
	         "compile" : $compile,
	         "DtOptionsBuilder" : DTOptionsBuilder,
	         "DTColumnBuilder" : DTColumnBuilder,
	         "service" : leaveAPIservice,
	         'editFormId' : 'leaveModal',
	         "deleteRow" : false
	     };
	     perfDatatable.loadTable.init(paramObj);
	};
	LeaveController.$inject = ['$scope', 'moment', 'user', 'leaveAPIservice', 'employeeAPIservice', 'calendarConfig', 'DTColumnBuilder' , '$compile', 'DTOptionsBuilder'];
	mainApp.controller('leaveController', LeaveController);
})(angular);
mainApp.factory('leaveAPIservice',['$http', function($http) {
    var leaveAPI = {};
    leaveAPI.loadAllLeaves = function(leaveType, calYear) {
        return $http({
          method: 'get',
          url: perfUrl['loadAllLeaves'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)
        });
    };
    leaveAPI.applyLeave = function(data) {
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['applyLeave']
        });
    };
    leaveAPI.updateLeave = function(data) {
        return $http({
          method: 'put',
          data : data,
          url: perfUrl['updateLeave']
        });
    };
    leaveAPI.deleteLeave = function(data) {
        return $http({
          method: 'put',
          data : data,
          url: perfUrl['deleteLeave']
        });
    };
    leaveAPI.loadById = function(id){
      return $http({
        method: 'get',
        url: perfUrl['loadLeaveById']+id
      });
    };
    leaveAPI.loadMyLeaves = function(leaveType, calYear){
      return $http({
        method: 'get',
        url: perfUrl['loadMyLeaves'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)
      });
    };
    leaveAPI.getLeaveBalance = function(leaveType, calYear, calMonth){
        return $http({
          method: 'get',
          url: perfUrl['getLeaveBalance'].replace('{leaveType}', leaveType).replace('{calYear}', calYear).replace('{calMonth}', calMonth)
        });
    };
    leaveAPI.loadLeaveReport = function(data){
      return $http({
        method: 'post',
        data : data,
        url: perfUrl['loadLeaveReport']
      });
    };
    leaveAPI.loadAllLeaveReport = function(data){
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['loadAllLeaveReport']
        });
      };
    leaveAPI.getCarryLeaves = function(year){
    	return $http({
            method: 'get',
            url: perfUrl['getCarryLeaves'].replace('{year}', year)
        });
    };
    return leaveAPI;
}]);
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
mainApp.factory('reportJobtitleAPIservice', ['$http',function($http) {
    var reportJobtitleAPI = {};
    reportJobtitleAPI.reportsLoadBySbu = function(fromDate, toDate, sbu, designation) {
        return $http({
          method: 'get',
          url: perfUrl['reportsLoadBySbu'].replace('{fromDate}', fromDate).replace('{toDate}', toDate).replace('{sbu}', sbu).replace('{designation}', designation)
        });
    };
    return reportJobtitleAPI;
}]);
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
(function(angular) {
/*Roles Management by employees Controller*/
	var EmpRolesController =  function($scope, $controller, rolesAPIservice, employeeAPIservice, emprolesAPIservice, DTColumnBuilder , $compile, DTOptionsBuilder) {
		rolesAPIservice.getRolesDetails().success(function (response) {
	        $scope.roles = response.entity;
	    });

		$scope.data = {};
		$scope.data.employee = [];
		
		employeeAPIservice.loadAllEmployees().success(function (response) {
	        $scope.employeeList = response.entity;
	    });
		
	    $scope.manageRoles = function(){
	        emprolesAPIservice.saveEmpRoles($scope.data).success(function () {
	            perfUtils.getInstance().successMsg('Employee Roles Saved Successfully!');
	            $scope.onRoleChange();
	        });
	    };
	    
	    $scope.onRoleChange = function(){
	    	emprolesAPIservice.loadEmpByRoles($scope.data.role.pk).success(function (response) {
	    		$scope.data.employee = [];
	    		$.each(response.entity, function(i, val){
	    			$scope.data.employee.push(val.employee);
	    		});
	    		/*$scope.dtInstance.DataTable.clear().draw();
	    		$scope.dtInstance.DataTable.rows.add(response.entity).draw();*/
	        });
	    };
	    
	    $scope.manageRoles = function(){
	        console.log('in apply ', $('form input[type="checkbox"]'));
	        $.each($('form input[type="checkbox"]'), function(i, val){
	        	console.log('name ', val.id, ' value ', $(val).prop('checked'));
	        });
	    };
	    
	    var jsonData = {
			"components" :[
			    {"name":"Administration", "type": "Menu",
			     "child": [{"name":"JobTitle", "type": "Page",
			    	 "btn":[{"name":"Save", "type":"btn"}, {"name":"Update", "type":"btn"},{"name":"Delete", "type":"btn"}]},{"name":"Roles", "type": "Page"}]			    
			    },{"name":"Employees", "type": "Page"}
		    ]
		}
		
		var componentsHtml = '';
		$scope.createComponentHtml = function(val){
			var html = $scope.createComponent(val);
			if(val.child){
				$.each(val.child, function(i, child){
					html+='<ul><li class="well">';
					html+= $scope.createComponentHtml(child);
					if(child.btn){
						html+='<ul><li>';
							$.each(child.btn, function(i, btn){
								html+=$scope.createComponent(btn);
							});
						html+='</li></ul>';
					}
					html+='</li></ul>';
				});
			}
			return html;
		};
		
		$scope.createComponent = function(val){
			var btnClass = 'btn-default';
			if(val.type === "Menu"){
				btnClass = 'btn-info';
			} else if(val.type === "Page"){
				btnClass = 'btn-primary';
			} else if(val.type === "btn"){
				console.log('in btn')
				if(val.name.includes('Save'))
					btnClass = 'btn-success';
				else if(val.name.includes('Upd'))
					btnClass = 'btn-warning';
				else if(val.name.includes('Del'))
					btnClass = 'btn-danger';
			}
			var html='<input type="checkbox" name="" id="'+val.name+'" autocomplete="off" />';
			html+='<div class="[ btn-group ]">';
			html+='<label for="'+val.name+'" class="[ btn '+btnClass+' ]">';
			html+='<span class="[ glyphicon glyphicon-check ]"></span>';
			html+='<span class="[ glyphicon glyphicon-unchecked ]"></span>';
			html+='</label>';
			html+='<label for="'+val.name+'" class="[ btn '+btnClass+' active ]">';
			html+= val.name;
			html+='</label>';
			html+='</div>';
			return html;
		};
		
		$.each(jsonData.components, function(i, val){
			componentsHtml += '<li class="well">'+$scope.createComponentHtml(val)+'</li>';
		});
		
		$('#componentsList').html(componentsHtml);
		
		$('input[type="checkbox"]').click(function(){
			if($(this).prop('checked')){
				$.each($(this).parent('li').parents('li').children('input[type="checkbox"]'), function(i, val){
					if(!$(this).prop('checked')){
						$(this).prop('checked', true);
					}
				});
			} else {
				$(this).parent('li').find('input[type="checkbox"]').prop('checked', false);
			}
		});
	    
	    $scope.dtColumns = [
            DTColumnBuilder.newColumn('roleId').withTitle('Role Name').renderWith(function(data, type, full) {
                return data.roleName;
            }),
            DTColumnBuilder.newColumn('employee').withTitle('Employee').renderWith(function(data, type, full) {
                return data.firstName+' '+data.lastName;
            })
        ];
	    
        var paramObj = {
            "vm" : $scope,
            'title' : 'Employee Roles Management',
            "compile" : $compile,
            "DtOptionsBuilder" : DTOptionsBuilder,
            "DTColumnBuilder" : DTColumnBuilder,
            "service" : emprolesAPIservice,
            'sortCol': 1,
            'sEmptyTable' : 'Loading..'
        };
        perfDatatable.loadTable.init(paramObj);
	};
	
	EmpRolesController.$inject = ['$scope','$controller', 'rolesAPIservice', 'employeeAPIservice', 'emprolesAPIservice', 'DTColumnBuilder' , '$compile', 'DTOptionsBuilder'];
	mainApp.controller('empRolesController', EmpRolesController);
})(angular);
mainApp.factory('emprolesAPIservice',['$http', function($http) {
    var emprolesAPI = {};
    emprolesAPI.loadEmpByRoles = function(roleId) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmpByRoles']+roleId
        });
    };
    emprolesAPI.loadNonEmpByRoles = function(roleId) {
        return $http({
          method: 'get',
          url: perfUrl['loadNonEmpByRoles']+roleId
        });
    };
    emprolesAPI.saveEmpRoles = function(emproles) {
    	return $http({
            method: 'post',
            data : emproles,
            url: perfUrl['saveEmpRoles']
        });
    };
    return emprolesAPI;
}]);
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