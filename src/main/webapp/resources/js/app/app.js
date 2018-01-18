var mainApp = angular.module("perficientHr",
        ['ngRoute', 'ngResource', 'ngAnimate', 'ngMessages',
         'mwl.calendar', 'ui.bootstrap', 'nya.bootstrap.select',
         'datatables', 'datatables.bootstrap', 'datatables.buttons', 'datatables.columnfilter',
         'ngSanitize', 'ui.select', 'ui.multiselect']);

(function(window){
	mainApp.value('user', {
	    loggedUser:{}
	});
	
	/*Set the selected menu/sub-menu open on refresh/reload*/
	var pageEle = '#'+window.location.href.split('#')[1];
	$("#menu li a[href='"+pageEle+"']").parent().addClass('Selected');
	
	var rand = Math.floor(Math.random()*(3-1+1)+1);
	
	mainApp.config(function($routeProvider) {
	  $routeProvider
	    .when('/home', {
	        templateUrl: 'html/dashboard2.html',
	        controller: 'dashboardController'
	    })
	    .when('/employees', {
	        templateUrl: 'html/employees.html',
	        controller: 'employeeController'
	    })
	    .when('/profile', {
	        templateUrl: 'html/profile.html',
	        controller: 'profileController'
	    })
	    .when('/pto', {
	        templateUrl: 'html/pto.html'
	    })
	    .when('/candidate', {
	        templateUrl: 'html/candidate.html'
	    })
	    .when('/interview', {
	        templateUrl: 'html/interview.html'
	    })
	    .when('/designations', {
	        templateUrl: 'html/designations.html',
	        controller: 'designationController'
	    })
	    .when('/projects', {
	        templateUrl: 'html/projects.html',
	        controller: 'projectController'
	    })
	    .when('/projectmembers', {
	        templateUrl: 'html/projectmembers.html',
	        controller: 'projectMembersController'
	    })
	    .when('/importpto', {
	        templateUrl: 'html/importpto.html',
	        controller: 'importPtoController'
	    })
	    .when('/wfh', {
	        templateUrl: 'html/wfh.html'
	    })
	    .when('/notifications', {
	        templateUrl: 'html/dashboard1.html'
	    })
	    .when('/report/jobtitle', {
	        templateUrl: 'html/reports_jobtitle.html',
	        controller: 'reportsJobtitleController'
	    })
	    .when('/report/reports_pto', {
	        templateUrl: 'html/reports_pto.html'
	        
	    })
	    .when('/report/reports_wfh', {
	        templateUrl: 'html/reports_wfh.html'
	    })
	    .when('/roles', {
	        templateUrl: 'html/roles.html',
	        controller: 'rolesController',
	        controllerAs: 'roles'
	    })
	    .when('/components', {
	        templateUrl: 'html/components.html',
	        controller: 'componentsController'
	    })
	    .when('/emproles', {
	        templateUrl: 'html/emproles.html',
	        controller: 'empRolesController'
	    })
	    .when('/goals', {
	        templateUrl: 'html/empgoals.html',
	        controller: 'empGoalsController'
	    })
	    .when('/componentroles', {
	        templateUrl: 'html/componentroles.html',
	        controller: 'componentRolesController'
	    })
	    .when('/table', {
	        templateUrl: 'html/table.html',
	    })
	    .otherwise({
	        redirectTo: '/home'
	    });
	});
}(window));