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