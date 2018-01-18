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