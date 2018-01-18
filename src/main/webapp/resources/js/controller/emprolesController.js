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