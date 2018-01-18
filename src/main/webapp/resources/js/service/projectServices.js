mainApp.factory('projectAPIservice', ['$http', function($http) {
    var projectAPI = {};
    projectAPI.loadById = function(projectPk) {
        return $http({
          method: 'get',
          url: perfUrl['loadProjectById']+projectPk
        });
    };
    projectAPI.loadProjects = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadProjects']
        });
    };
    projectAPI.addProject = function(project) {
        return $http({
          method: 'post',
          data : project,
          url: perfUrl['addProject']
        });
    };
    projectAPI.updateProject = function(project) {
        return $http({
          method: 'put',
          data : project,
          url: perfUrl['updateProject']
        });
    };
    projectAPI.deleteProject = function(project) {
        return $http({
          method: 'put',
          data : project,
          url: perfUrl['deleteProject']
        });
    };
    return projectAPI;
}]);