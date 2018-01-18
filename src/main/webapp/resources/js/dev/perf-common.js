(function(window){
	var lastRequestTime, timeoutHandle;
	//register the interceptor as a service
	mainApp.factory('perfInterceptor', ['$q', '$rootScope', function($q, $rootScope) {
	  var loadingCount = 0;
	  return {
	    'request': function(config) {
	        if(++loadingCount === 1)
	            $rootScope.$broadcast('loading:progress');
	        if(timeoutHandle){
	            window.clearTimeout(timeoutHandle);
	        }
	        perfUtils.getInstance().init();
	        lastRequestTime = new Date().getTime();
	        config.headers = config.headers || {};
	        return config;
	    },
	    'requestError': function(rejection) {
	        return $q.reject(rejection);
	    },
	    'response': function(response) {
	        if(--loadingCount === 0)
	            $rootScope.$broadcast('loading:finish');
	        if(response.data.status === 500){
	        	perfUtils.getInstance().errorMsg('An Error Occured!');
	    		return $q.reject(response);
	        } else if(response.data.status === 409){
	    		perfUtils.getInstance().errorMsg(response.data.entity.errorMessage);
	    		return $q.reject(response);
	    	}
	        return response;
	    },
	    'responseError': function(rejection) {
	        if(--loadingCount === 0)
	           $rootScope.$broadcast('loading:finish');
	        if (rejection.status === 401) {
	           window.location.href = "logout";
	        }
	        return $q.reject(rejection);
	    }
	  };
	}]);
	
	mainApp.config(['$httpProvider', function($httpProvider){
	    $httpProvider.interceptors.push('perfInterceptor');
	}]);
	
	var $menu = $('#menu');
	var $btnMenu = $('.btn-menu');
	var $img = $('img');
	
	//set home li selected on login
	$('#menu li a[href="#/home"]').parent().addClass('mm-selected');
	
	// mmenu customization
	$menu.mmenu({
	  navbars: [
	      {
		    position: "top",
		    content: [ "searchfield", "breadcrumbs" ],
		    height: 2
		  },
		  {
		      "position": "bottom",
		      "content": [
		         "<span id='version' class='fa' href='#/'>Version:</span>"
		      ]
		  }
	  ],
	  extensions: ['widescreen', 'theme-dark', 'effect-menu-slide'],
	  offCanvas: {
	    position  : "left",
	    zposition : "back"
	  },
	  setSelected: true,
	  onClick: {
	      setSelected: true
	  },
	  searchfield: true
	}).on('click', 'a[href^="#/"]', function() {
		$('#menu li.mm-selected').removeClass('mm-selected');
		$(this).parents().addClass('mm-selected');
	    window.location.href=$(this).attr('href');
	    return false;
	});
	
	// toggle menu
	var api = $menu.data("mmenu");
	
	$('#sidePanel').on('click', function(e) {
	    e.preventDefault();
	    if ($(this).hasClass('mm-opened')) {
	        api.close();
	        $menu.hide();
	    } else {
	        api.open();
	        $menu.show();
	    }
	});
	
	// change toggle behavior for subpanels
	$menu.find(".mm-next").addClass("mm-fullsubopen");
	
	//Dashboard 1 click event on actions
	$('#divContainer').on('click', '.feature i',  function() {
	    window.location.href=$(this).attr('nav');
	});
	
	/*
	 * Reset the form whenever its closed.
	 */
	$(document).on('hidden.bs.modal', 'div[role="dialog"]', function () {
		var formId=$(this).attr('id');
		$('#'+formId+' .help-block').empty();
		$('#'+formId+' p.text-danger').remove();
		$('#'+formId+' .has-error').removeClass('has-error');
		if(typeof scope !== 'undefined')
			scope.data = {};
	});
}(window));
var urlPrefix = 'v-';
var PerfWidgetCache = [];
var lastRequestTime = new Date().getTime();
var timeoutHandle;

(function(){
	mainApp.constant('factoryData',{
    });
	mainApp.constant('filterNames',{
        revertNewLine: 'revertNewLine',
        splitColon: 'splitColon',
        calculateDayDiff: 'calculateDayDiff'
    });
}());
var perfUrl = {
    'validateSession'  : 'user/validateSession',
    'getVersion' : 'version',
    'loadAllEmployees'  : urlPrefix+'employee/loadAllEmployees',
    'loadEmployees'  : urlPrefix+'employee/loadEmployees',
    'loadEmployee'     : urlPrefix+'employee/loadEmployee',
    'loadEmployeeById' : urlPrefix+'employee/loadEmployeeById?employeeId=',
    'updateEmployee'   : urlPrefix+'employee/updateEmployee',
    'addEmployee'      : urlPrefix+'employee/addEmployee',
    'loadEmployeeByDesHistory' : urlPrefix+'employee/loadEmployeeByDesHistory/{fromDate}/{toDate}/{designation}',
    'loadDesignations' : urlPrefix+'designation/loadDesignations',
    'loadDesignationById' : urlPrefix+'designation/loadDesignationById?id=',
    'addDesignation'   : urlPrefix+'designation/addDesignation',
    'updateDesignation': urlPrefix+'designation/updateDesignation',
    'deleteDesignation' : urlPrefix+'designation/deleteDesignation',
    'loadProjects' : urlPrefix+'projects/loadProjects',
    'addProject'   : urlPrefix+'projects/addProject',
    'updateProject': urlPrefix+'projects/updateProject',
    'deleteProject': urlPrefix+'projects/deleteProject',
    'loadProjectById' : urlPrefix+'projects/loadProjectById?projectPk=',
    'loadProjectMembersByProjectId' : urlPrefix+'projectmembers/loadProjectMembersByProjectId?projectId=',
    'loadAllProjectMembers' : urlPrefix+'projectmembers/loadAllProjectMembers',
    'loadProjectMemberById' : urlPrefix+'projectmembers/loadProjectMemberById?projectMemberId=',
    'saveProjectMember' : urlPrefix+'projectmembers/saveProjectMember',
    'updateProjectMember' : urlPrefix+'projectmembers/updateProjectMember',
    'deleteProjectMember' : urlPrefix+'projectmembers/deleteProjectMember',
    'importPto' : urlPrefix+'leave/fetchExcel',
    'carryLeaves' : urlPrefix+'leave/carryLeaves/{year}',
    'getCarryLeaves' : urlPrefix+'leave/getCarryLeaves/{year}',
    'applyLeave' : urlPrefix+'leave/applyLeave',
    'loadAllLeaves' : urlPrefix+'leave/loadAllLeaves/{leaveType}/{calYear}',
    'updateLeave' : urlPrefix+'leave/updateLeave',
    'deleteLeave' : urlPrefix+'leave/deleteLeave',
    'loadLeaveById' : urlPrefix+'leave/loadLeaveById?leaveId=',
    'loadMyLeaves' : urlPrefix+'leave/loadMyLeaves/{leaveType}/{calYear}',
    'getLeaveBalance' : urlPrefix+'leave/getLeaveBalance/{leaveType}/{calYear}/{calMonth}',
    'loadLeaveReport': urlPrefix+'leave/loadLeaveReport',
    'loadAllLeaveReport': urlPrefix+'leave/loadAllLeaveReport',
    'loadNotificationCount' : urlPrefix+'notification/loadNotificationCount',
    'reportsLoadBySbu' : urlPrefix+'reportJobTitle/loadBySbu/{fromDate}/{toDate}/{sbu}/{designation}',
    'loadRoles' : urlPrefix+'roles/loadRoles',
    'loadRolesById' : urlPrefix+'roles/loadRolesById?id=',
    'addRoles'   : urlPrefix+'roles/addRoles',
    'updateRoles': urlPrefix+'roles/updateRoles',
    'deleteRoles' : urlPrefix+'roles/deleteRoles',
    'loadComponents' : urlPrefix+'components/loadComponents',
    'loadComponentsById' : urlPrefix+'components/loadComponentsById?id=',
    'addComponent'   : urlPrefix+'components/addComponent',
    'updateComponent': urlPrefix+'components/updateComponent',
    'deleteComponent' : urlPrefix+'components/deleteComponent',
    'saveEmpRoles' : urlPrefix+'emproles/saveEmpRoles',
    'loadNonEmpByRoles' : urlPrefix+'emproles/loadNonEmpByRoles?roleId=',
    'loadEmpByRoles' : urlPrefix+'emproles/loadEmpByRoles?roleId='
};
function perfUtils(){};
perfUtils.getInstance = function(){
    var obj = PerfWidgetCache['perfIns'];
    if(!obj)
        obj = PerfWidgetCache['perfIns'] = new perfUtils();
    return obj;
};

perfUtils.prototype = {
    init: function(){
        if((new Date().getTime()-lastRequestTime)/(1000*60) > 30){
            window.location.href = "logout";
        } else {
            timeoutHandle = window.setTimeout('perfUtils.getInstance().init()', 10000);
        }
    },
    compareDate: function(){
    	if(new Date(moment.utc($('#startDt').val(), "DD-MMM-YYYY")).getTime() > new Date(moment.utc($('#endDt').val(), "DD-MMM-YYYY")).getTime()){
    		var errorMsg = '<p class="text-danger"></p>';
    		$('#startDt').parent().addClass('has-error');
        	$(errorMsg).html($('#startDt').attr('name')+' must be lesser than '+$('#endDt').attr('name')+'.').insertAfter($('#startDt'));
    	}
    },
    validateFutureDate: function(ids){
    	$.each(ids, function(i, id){
    		if(new Date(moment.utc($('#'+id).val(), "DD-MMM-YYYY")).getTime() > new Date().getTime()){
    			var errorMsg = '<p class="text-danger"></p>';
    			$('#'+id).parent().addClass('has-error');
    			$(errorMsg).html($('#'+id).attr('name')+' must be lesser than present date.').insertAfter($('#'+id));
    		}
    	});
    },
    resetForm: function(){
    	$('form .help-block').html('');
    	$('form').find(':input[name]').val('');
    },
    successMsg: function(msg){
    	$.alert({
		    title: 'Message:',
		    columnClass: 'col-md-6 col-md-offset-3',
		    content: msg,
		    confirmButtonClass: 'btn-success'
		});
    },
    errorMsg: function(msg){
    	$.alert({
		    title: 'Error:',
		    theme: 'black',
		    columnClass: 'col-md-6 col-md-offset-3',
		    content: msg,
		    confirmButtonClass: 'btn-danger'
		});
    }
};

(function(){
	var shortErr = ' is too short.', longErr=' is too long.', reqEr=' is required.', invalidErr=' is invalid.';
	var errorMsg = '<p class="text-danger"></p>';
	
	function validateField(ele, eleValue, regEx, errBlock){
	    var eleName = $(ele).attr('name'), isReq = $(ele).attr('required'), minLen = $(ele).attr('ng-minlength'), maxLen = $(ele).attr('ng-maxlength');
	    var error = '';
	    if((typeof isReq !== typeof undefined)
	            &&($.trim(eleValue).length === 0)){
	    	error = reqEr;
	    } else if(eleValue.length > 0){
	    	if(regEx !== '' && !regEx.test(eleValue)){
	         	error = invalidErr;
	        } else if((typeof minLen !== typeof undefined) && $.trim(eleValue).length < minLen){
	            error = shortErr;
	        } else if((typeof maxLen !== typeof undefined) && $.trim(eleValue).length > maxLen){
	        	error = longErr;
	        }
	    }
	    if($.trim(error).length !== 0){
	    	$(ele).parent().addClass('has-error');
	    	$(errorMsg).html(eleName+error).insertAfter($(ele));
	    }
	}
	
	function validateForm(form, scope){
	    var errBlock = $(form).find('.help-block');
	    $(errBlock).empty();
	    $(form).find('div').add('p').removeClass('has-error');
	    $(form).find('p.text-danger').remove();
	    $(form).find('ol.nya-bs-select, :input[name]').each(function(i, ele){
	    	var eleType = $(ele).attr('validType');
	        var eleValue = '', regEx='';
	        if(eleType !== undefined){
	        	if(eleType === 'select'){
	                eleValue = $(ele).find(':selected').text() === "" ? $(ele).attr('ng-selected'): $(ele).find(':selected').text();
	                if($(ele).attr('multiple') && eleValue === '[]')
	                	eleValue = "";
	            } else {
	            	eleValue = $(ele).val();
	                if(eleType === 'text-only'){
	                    regEx = /^[a-zA-Z ]*$/;
	                } else if(eleType === 'number-only'){
	                    regEx = /^[0-9]+$/;
	                } else if(eleType === 'email'){
	                    regEx = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	                } else if(eleType === 'date'){
	                    regEx = /^(([0-9])|([0-2][0-9])|([3][0-1]))\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\-\d{4}$/;
	                } else if(eleType === 'date-time'){
	                    regEx = /^(0?[1-9]|[12][0-9]|3[01])-(jan|Jan|JAN|feb|Feb|FEB|mar|Mar|MAR|apr|Apr|APR|may|May|MAY|jun|Jun|JUN|jul|Jul|JUL|aug|Aug|AUG|sep|Sep|SEP|oct|Oct|OCT|nov|Nov|NOV|dec|Dec|DEC)-(19|20)\d\d\s([0-1][0-9]|[2][0-3]):([0-5][0-9])(\s{0,1})(AM|PM|am|pm|aM|Am|pM|Pm{2,2})$/;
	                } else if(eleType === 'alpha-numeric'){
	                    regEx = /^[a-zA-Z0-9 ]*$/;
	                } else if(eleType === 'all-chars'){
	                	regEx = '';
	                }
	            }
	            validateField(ele, eleValue, regEx, errBlock);
	        }
	    });
	    try{
			scope.validate();
		} catch(err){
		}
	    return $(form).find('p.text-danger').length === 0?true:false;
	}
	
	mainApp.directive('ajaxLoading', function () {
	    return {
	        restrict: 'A',
	        link: function (scope) {
	            scope.$on("loading:progress", function () {
	                $('#overlay').show();
	            });
	            return scope.$on("loading:finish", function () {
	                $('#overlay').hide();
	            });
	        }
	    };
	}).directive('button', function() {
	    return {
	        restrict: 'E',
	        link: function(scope, elem, attr) {
	            if(attr.type === 'submit'){
	                elem.on('click', function() {
	            		if(validateForm($(elem).parents('form'), scope)){
	        				scope[attr.action]();
	                    }
	                });
	            }
	        }
	    };
	}).directive('fileModel', ['$parse', function ($parse) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	            var model = $parse(attrs.fileModel);
	            var modelSetter = model.assign;
	            
	            element.bind('change', function(){
	                scope.$apply(function(){
	                    modelSetter(scope, element[0].files[0]);
	                });
	            });
	        }
	    };
	}]);
}());
(function(angular) {
	mainApp.factory('commonAPIservice',['$http', function($http) {
	    var commonAPI = {};
	    commonAPI.loadRecords = function(url) {
	        return $http({
	          method: 'get',
	          url: url
	        });
	    };    
	    commonAPI.add = function(url,data) {
	        return $http({
	          method: 'post',
	          data : data,
	          url: url
	        });
	    };
	    commonAPI.updateDel = function(url, data) {
	        return $http({
	          method: 'put',
	          data : data,
	          url: url
	        });
	    };
	    return commonAPI;
	}]);
})(angular);
(function(angular) {
	var AbstractController = function($controller, vm, commonAPIservice, $compile, DTOptionsBuilder, DTColumnBuilder){
		var _this = this; _this.vm = vm;  _this.service = commonAPIservice;
		vm.scope.add = function(){
	        _this.add();
	    };
	    vm.scope.save = function(){
	        _this.save();
	    };
	    vm.scope.update = function(){
	        _this.update();
	    };
	    vm.scope.del = function(){
	        _this.del(vm.deleteUrl);
	    };
	    
	    vm.scope.title = vm.title;
	   
	    var paramObj = {
            "vm" : vm.scope,
            "compile" : $compile,
            "DtOptionsBuilder" : DTOptionsBuilder,
            "DTColumnBuilder" : DTColumnBuilder,
            "service" : commonAPIservice,
            'loadListUrl' : vm.loadListUrl,
            'editFormId' : vm.formId,
            'deleteFormId' : 'deleteRecord'
        };
        perfDatatable.loadTable.init(paramObj);
	};
	
	AbstractController.prototype.add = function(){
		this.vm.scope.data = {};
		perfUtils.getInstance().resetForm();
		$('#'+this.vm.formId).modal();
	};
	
	AbstractController.prototype.save = function(){
		var _this = this;
		this.service.add(_this.vm.addUrl, _this.vm.scope.data).success(function (response) {
    		perfUtils.getInstance().successMsg(_this.vm.title+' Saved Successfully!');
    		_this.vm.scope.dtInstance.reloadData();
    		$('#'+_this.vm.formId).modal('hide');
        });
	};
	
	AbstractController.prototype.update = function(){
		var _this = this;
		this.service.updateDel(_this.vm.updateUrl, _this.vm.scope.data).success(function (response) {
			$('#'+_this.vm.formId).modal('hide');
    		perfUtils.getInstance().successMsg(_this.vm.title+' updated Successfully!');
    		_this.vm.scope.dtInstance.reloadData();
        });
	};
	
	AbstractController.prototype.del = function(){
		var _this = this;
		this.service.updateDel(_this.vm.deleteUrl, _this.vm.scope.data).success(function (response) {
			_this.vm.scope.dtInstance.DataTable.row('.selected').remove().draw(false);
			$('#deleteRecord').modal('hide');
    		perfUtils.getInstance().successMsg(_this.vm.title+' deleted Successfully!');
        });
	};

	AbstractController.$inject = ['_this', 'vm', 'commonAPIservice','$compile', 'DTOptionsBuilder', 'DTColumnBuilder'];
	mainApp.controller('AbstractController', AbstractController);
})(angular);
(function(root){
    var perfDatatable = root.perfDatatable || {};
    this.params = null;
    perfDatatable.loadTable = {
        init: function(params){
            this.params = params;
            var editRow = true, deleteRow = true, actions = true;
            params.vm.message = '';
            params.vm.dtInstance = {};
            params.vm.datalist = {};
            params.url = (params.loadListUrl)?params.loadListUrl:'';
            params.editRow = (params.editRow === undefined)?editRow:params.editRow;
            params.deleteRow = (params.deleteRow === undefined)?deleteRow:params.deleteRow;
            params.actions = (params.actions === undefined)?actions:params.actions;
            params.responsive = params.responsive?params.responsive: false;
            perfDatatable.loadTable.loadDataTable(params);
        },
        loadDataTable: function(params){
            params.vm.dtOptions = params.DtOptionsBuilder.fromSource(params.url)
            .withDataProp('entity')
            .withDisplayLength(7)
            .withOption('lengthMenu', [7, 10, 25, 50, 100])
            .withDOM('pitrfl')
            .withBootstrap()
            .withOption('responsive', params.responsive)
            .withOption('createdRow', createdRow)
            .withOption('aaSorting', [params.sortCol === undefined? 0: params.sortCol, 'asc'])
            .withPaginationType('full_numbers')
            .withOption("oLanguage", {"sEmptyTable": params.vm.sEmptyTable === undefined?"No Records Found.": params.vm.sEmptyTable})
            .withColumnFilter()
            .withButtons([
                {
                    extend : 'excel',
                    title: 'Perficient Chennai - '+params.vm.title,
                    exportOptions: {
                        columns: params.actions === false? '':':not(:last-child)'
                    }
                },
                {
                    extend : 'print',
                    exportOptions: {
                        columns: params.actions === false? '':':not(:last-child)'
                    }
                }
            ]);
            if(params.actions)
                params.vm.dtColumns.push(params.DTColumnBuilder.newColumn(null).withTitle('Actions').notSortable().renderWith(actionsHtml));

            function createdRow(row) {
                // Recompiling so we can bind Angular directive to the DT
                if(params.scope)
	                params.compile(angular.element(row).contents())(params.scope);
	            else
		            params.compile(angular.element(row).contents())(params.vm);
            }
            function actionsHtml(data) {
                params.vm.datalist[data.pk] = data;
                var editRecord='', deleteRecord ='';
                if(params.editRow){
                    editRecord = '<button class="btn btn-edit" data-toggle="modal" onclick="perfDatatable.loadTable.popRecord(this, '+data.pk+', '+params.editFormId+')">' +
                    '   <i class="fa fa-pencil"></i>' +
                    '</button>&nbsp;';
                }
                if(params.deleteRow){
                    deleteRecord = '<button class="btn btn-danger" data-toggle="modal" onclick="perfDatatable.loadTable.popRecord(this, '+data.pk+', '+params.deleteFormId+')">' +
                    '   <i class="fa fa-trash-o"></i>' +
                    '</button>';
                }
                return  editRecord+deleteRecord;
            };
        },
        popRecord: function(ele, id, formId){
            this.params.vm.dtInstance.DataTable.$('tr.selected').removeClass('selected');
            $(ele).parents('tr').addClass('selected');
        	this.params.vm.data = angular.copy(this.params.vm.datalist[id]);        
            this.params.vm.$apply();	
            $(formId).modal('show');
        }
    };
    root.perfDatatable = perfDatatable;
})(this);