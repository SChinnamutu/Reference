mainApp.factory('notificationAPIservice', ['$http', function($http) {
    var notificationAPI = {};
    notificationAPI.loadNotificationCount = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadNotificationCount']
        });
    };
    return notificationAPI;
}]);