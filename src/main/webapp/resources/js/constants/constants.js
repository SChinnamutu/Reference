var urlPrefix = 'v-';
var PerfWidgetCache = [];
var lastRequestTime = new Date().getTime();
var timeoutHandle;

(function(){
	mainApp.constant('factoryData',{
    });
	mainApp.constant('filterNames',{
        revertNewLine: 'revertNewLine',
        splitColon: 'splitColon',
        calculateDayDiff: 'calculateDayDiff'
    });
}());