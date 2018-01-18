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