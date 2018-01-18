mainApp.factory('projectMemberAPIservice', ['$http', function($http) {
    var projectMembersAPI = {};
    projectMembersAPI.loadById = function(projectMemberPk) {
        return $http({
          method: 'get',
          url: perfUrl['loadProjectMemberById']+projectMemberPk
        });
    };
    projectMembersAPI.loadAllProjectMembers = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadAllProjectMembers']
        });
    };
    projectMembersAPI.saveProjectMember = function(projectMember) {
        return $http({
          method: 'post',
          data : projectMember,
          url: perfUrl['saveProjectMember']
        });
    };
    projectMembersAPI.updateProjectMember = function(projectMember) {
        return $http({
          method: 'put',
          data : projectMember,
          url: perfUrl['updateProjectMember']
        });
    };
    projectMembersAPI.deleteProjectMember = function(projectMember) {
        return $http({
          method: 'put',
          data : projectMember,
          url: perfUrl['deleteProjectMember']
        });
    };
    return projectMembersAPI;
}]);