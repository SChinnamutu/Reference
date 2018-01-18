(function(){
	var shortErr = ' is too short.', longErr=' is too long.', reqEr=' is required.', invalidErr=' is invalid.';
	var errorMsg = '<p class="text-danger"></p>';
	
	function validateField(ele, eleValue, regEx, errBlock){
	    var eleName = $(ele).attr('name'), isReq = $(ele).attr('required'), minLen = $(ele).attr('ng-minlength'), maxLen = $(ele).attr('ng-maxlength');
	    var error = '';
	    if((typeof isReq !== typeof undefined)
	            &&($.trim(eleValue).length === 0)){
	    	error = reqEr;
	    } else if(eleValue.length > 0){
	    	if(regEx !== '' && !regEx.test(eleValue)){
	         	error = invalidErr;
	        } else if((typeof minLen !== typeof undefined) && $.trim(eleValue).length < minLen){
	            error = shortErr;
	        } else if((typeof maxLen !== typeof undefined) && $.trim(eleValue).length > maxLen){
	        	error = longErr;
	        }
	    }
	    if($.trim(error).length !== 0){
	    	$(ele).parent().addClass('has-error');
	    	$(errorMsg).html(eleName+error).insertAfter($(ele));
	    }
	}
	
	function validateForm(form, scope){
	    var errBlock = $(form).find('.help-block');
	    $(errBlock).empty();
	    $(form).find('div').add('p').removeClass('has-error');
	    $(form).find('p.text-danger').remove();
	    $(form).find('ol.nya-bs-select, :input[name]').each(function(i, ele){
	    	var eleType = $(ele).attr('validType');
	        var eleValue = '', regEx='';
	        if(eleType !== undefined){
	        	if(eleType === 'select'){
	                eleValue = $(ele).find(':selected').text() === "" ? $(ele).attr('ng-selected'): $(ele).find(':selected').text();
	                if($(ele).attr('multiple') && eleValue === '[]')
	                	eleValue = "";
	            } else {
	            	eleValue = $(ele).val();
	                if(eleType === 'text-only'){
	                    regEx = /^[a-zA-Z ]*$/;
	                } else if(eleType === 'number-only'){
	                    regEx = /^[0-9]+$/;
	                } else if(eleType === 'email'){
	                    regEx = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/;
	                } else if(eleType === 'date'){
	                    regEx = /^(([0-9])|([0-2][0-9])|([3][0-1]))\-(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\-\d{4}$/;
	                } else if(eleType === 'date-time'){
	                    regEx = /^(0?[1-9]|[12][0-9]|3[01])-(jan|Jan|JAN|feb|Feb|FEB|mar|Mar|MAR|apr|Apr|APR|may|May|MAY|jun|Jun|JUN|jul|Jul|JUL|aug|Aug|AUG|sep|Sep|SEP|oct|Oct|OCT|nov|Nov|NOV|dec|Dec|DEC)-(19|20)\d\d\s([0-1][0-9]|[2][0-3]):([0-5][0-9])(\s{0,1})(AM|PM|am|pm|aM|Am|pM|Pm{2,2})$/;
	                } else if(eleType === 'alpha-numeric'){
	                    regEx = /^[a-zA-Z0-9 ]*$/;
	                } else if(eleType === 'all-chars'){
	                	regEx = '';
	                }
	            }
	            validateField(ele, eleValue, regEx, errBlock);
	        }
	    });
	    try{
			scope.validate();
		} catch(err){
		}
	    return $(form).find('p.text-danger').length === 0?true:false;
	}
	
	mainApp.directive('ajaxLoading', function () {
	    return {
	        restrict: 'A',
	        link: function (scope) {
	            scope.$on("loading:progress", function () {
	                $('#overlay').show();
	            });
	            return scope.$on("loading:finish", function () {
	                $('#overlay').hide();
	            });
	        }
	    };
	}).directive('button', function() {
	    return {
	        restrict: 'E',
	        link: function(scope, elem, attr) {
	            if(attr.type === 'submit'){
	                elem.on('click', function() {
	            		if(validateForm($(elem).parents('form'), scope)){
	        				scope[attr.action]();
	                    }
	                });
	            }
	        }
	    };
	}).directive('fileModel', ['$parse', function ($parse) {
	    return {
	        restrict: 'A',
	        link: function(scope, element, attrs) {
	            var model = $parse(attrs.fileModel);
	            var modelSetter = model.assign;
	            
	            element.bind('change', function(){
	                scope.$apply(function(){
	                    modelSetter(scope, element[0].files[0]);
	                });
	            });
	        }
	    };
	}]);
}());