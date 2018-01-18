mainApp.factory('ptoAPIService',['$http', function ($http) {
    var importPtoAPI = {};
    importPtoAPI.uploadFileToUrl = function(file) {
         var fd = new FormData();
         fd.append('uploadFiles',file);
         return $http({
             method: 'post',
             data : fd,
             url: perfUrl['importPto'],
             transformRequest: angular.identity,
             headers: {'Content-Type': undefined}
         });
    };
    importPtoAPI.carryLeaves = function(year){
    	return $http({
            method: 'post',
            url: perfUrl['carryLeaves'].replace('{year}', year)
        });
    };
    return importPtoAPI;
}]);