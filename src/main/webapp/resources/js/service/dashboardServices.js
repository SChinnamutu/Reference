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