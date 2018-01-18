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