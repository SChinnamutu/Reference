mainApp.factory('emprolesAPIservice',['$http', function($http) {
    var emprolesAPI = {};
    emprolesAPI.loadEmpByRoles = function(roleId) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmpByRoles']+roleId
        });
    };
    emprolesAPI.loadNonEmpByRoles = function(roleId) {
        return $http({
          method: 'get',
          url: perfUrl['loadNonEmpByRoles']+roleId
        });
    };
    emprolesAPI.saveEmpRoles = function(emproles) {
    	return $http({
            method: 'post',
            data : emproles,
            url: perfUrl['saveEmpRoles']
        });
    };
    return emprolesAPI;
}]);