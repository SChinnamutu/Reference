mainApp.factory('employeeAPIservice',['$http', function($http) {
    var employeeAPI = {};
    employeeAPI.loadById = function(empId) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployeeById']+empId
        });
    };
    employeeAPI.loadAllEmployees = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadAllEmployees']
        });
    };
    employeeAPI.loadEmployees = function() {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployees']
        });
    };
    employeeAPI.addEmployee = function(employee) {
        return $http({
          method: 'post',
          data : employee,
          url: perfUrl['addEmployee']
        });
    };
    employeeAPI.updateEmployee = function(employee) {
        return $http({
          method: 'put',
          data : employee,
          url: perfUrl['updateEmployee']
        });
    };
    employeeAPI.loadEmployeeByDesHistory = function(fromDate, toDate, designation) {
        return $http({
          method: 'get',
          url: perfUrl['loadEmployeeByDesHistory'].replace('{fromDate}', fromDate).replace('{toDate}', toDate).replace('{designation}', designation)
        });
    };
    return employeeAPI;
}]);