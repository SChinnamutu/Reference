mainApp.factory('leaveAPIservice',['$http', function($http) {
    var leaveAPI = {};
    leaveAPI.loadAllLeaves = function(leaveType, calYear) {
        return $http({
          method: 'get',
          url: perfUrl['loadAllLeaves'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)
        });
    };
    leaveAPI.applyLeave = function(data) {
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['applyLeave']
        });
    };
    leaveAPI.updateLeave = function(data) {
        return $http({
          method: 'put',
          data : data,
          url: perfUrl['updateLeave']
        });
    };
    leaveAPI.deleteLeave = function(data) {
        return $http({
          method: 'put',
          data : data,
          url: perfUrl['deleteLeave']
        });
    };
    leaveAPI.loadById = function(id){
      return $http({
        method: 'get',
        url: perfUrl['loadLeaveById']+id
      });
    };
    leaveAPI.loadMyLeaves = function(leaveType, calYear){
      return $http({
        method: 'get',
        url: perfUrl['loadMyLeaves'].replace('{leaveType}', leaveType).replace('{calYear}', calYear)
      });
    };
    leaveAPI.getLeaveBalance = function(leaveType, calYear, calMonth){
        return $http({
          method: 'get',
          url: perfUrl['getLeaveBalance'].replace('{leaveType}', leaveType).replace('{calYear}', calYear).replace('{calMonth}', calMonth)
        });
    };
    leaveAPI.loadLeaveReport = function(data){
      return $http({
        method: 'post',
        data : data,
        url: perfUrl['loadLeaveReport']
      });
    };
    leaveAPI.loadAllLeaveReport = function(data){
        return $http({
          method: 'post',
          data : data,
          url: perfUrl['loadAllLeaveReport']
        });
      };
    leaveAPI.getCarryLeaves = function(year){
    	return $http({
            method: 'get',
            url: perfUrl['getCarryLeaves'].replace('{year}', year)
        });
    };
    return leaveAPI;
}]);