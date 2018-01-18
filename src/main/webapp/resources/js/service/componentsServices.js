mainApp.factory('componentsAPIservice', ['$http',function($http) {
    var componentsAPI = {};
    componentsAPI.getComponentsDetails = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadComponents']
        });
    };
    componentsAPI.loadById = function(id) {
        return $http({
          method: 'get',
          url: perfUrl['loadComponentsById']+id
        });
    };
    componentsAPI.addComponents = function(components) {
        return $http({
          method: 'post',
          data : components,
          url: perfUrl['addComponent']
        });
    };
    componentsAPI.updateComponents = function(components) {
        return $http({
          method: 'put',
          data : components,
          url: perfUrl['updateComponent']
        });
    };
    componentsAPI.deleteComponents = function(components) {
        return $http({
          method: 'put',
          data : components,
          url: perfUrl['deleteComponent']
        });
    };
    return componentsAPI;
}]);