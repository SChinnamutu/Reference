mainApp.controller('importPtoController', function($scope, ptoAPIService){
	
	$(document).on('change', ':file', function() {
	    var input = $(this),
	        numFiles = input.get(0).files ? input.get(0).files.length : 1,
	        label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
	    input.trigger('fileselect', [numFiles, label]);
	});
	
	$(':file').on('fileselect', function(event, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text'),
            log = numFiles > 1 ? numFiles + ' files selected' : label;
        if( input.length ) {
            input.val(log);
        } else {
            if( log ) alert(log);
        }
    });
	
    $scope.uploadFile = function(){
    	var file = $scope.uploadPto;
        ptoAPIService.uploadFileToUrl(file).success(function(data){
            $('input[type="file"]').val('');
            if(data)
            	perfUtils.getInstance().successMsg('PTO document processed successfully!');
            else
                $scope.msg="An error occurred during document processing!";
        });
    };
    
    /*$scope.carryLeaves = function(){
    	console.log('data ', $scope.data);
    	ptoAPIService.carryLeaves($scope.data.year).success(function(data){
    		perfUtils.getInstance().successMsg('Leave Balance processed successfully!');
    	});
    };*/
});