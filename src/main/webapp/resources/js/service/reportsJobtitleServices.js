mainApp.factory('reportJobtitleAPIservice', ['$http',function($http) {
    var reportJobtitleAPI = {};
    reportJobtitleAPI.reportsLoadBySbu = function(fromDate, toDate, sbu, designation) {
        return $http({
          method: 'get',
          url: perfUrl['reportsLoadBySbu'].replace('{fromDate}', fromDate).replace('{toDate}', toDate).replace('{sbu}', sbu).replace('{designation}', designation)
        });
    };
    return reportJobtitleAPI;
}]);