/*Notification Controller*/
mainApp.controller('notificationController', function($scope, user, profileAPIservice) {
    profileAPIservice.getProfileDetails().success(function (response) {
        $scope.user = response.entity;
    });
});