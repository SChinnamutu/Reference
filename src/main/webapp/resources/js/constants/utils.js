function perfUtils(){};
perfUtils.getInstance = function(){
    var obj = PerfWidgetCache['perfIns'];
    if(!obj)
        obj = PerfWidgetCache['perfIns'] = new perfUtils();
    return obj;
};

perfUtils.prototype = {
    init: function(){
        if((new Date().getTime()-lastRequestTime)/(1000*60) > 30){
            window.location.href = "logout";
        } else {
            timeoutHandle = window.setTimeout('perfUtils.getInstance().init()', 10000);
        }
    },
    compareDate: function(){
    	if(new Date(moment.utc($('#startDt').val(), "DD-MMM-YYYY")).getTime() > new Date(moment.utc($('#endDt').val(), "DD-MMM-YYYY")).getTime()){
    		var errorMsg = '<p class="text-danger"></p>';
    		$('#startDt').parent().addClass('has-error');
        	$(errorMsg).html($('#startDt').attr('name')+' must be lesser than '+$('#endDt').attr('name')+'.').insertAfter($('#startDt'));
    	}
    },
    validateFutureDate: function(ids){
    	$.each(ids, function(i, id){
    		if(new Date(moment.utc($('#'+id).val(), "DD-MMM-YYYY")).getTime() > new Date().getTime()){
    			var errorMsg = '<p class="text-danger"></p>';
    			$('#'+id).parent().addClass('has-error');
    			$(errorMsg).html($('#'+id).attr('name')+' must be lesser than present date.').insertAfter($('#'+id));
    		}
    	});
    },
    resetForm: function(){
    	$('form .help-block').html('');
    	$('form').find(':input[name]').val('');
    },
    successMsg: function(msg){
    	$.alert({
		    title: 'Message:',
		    columnClass: 'col-md-6 col-md-offset-3',
		    content: msg,
		    confirmButtonClass: 'btn-success'
		});
    },
    errorMsg: function(msg){
    	$.alert({
		    title: 'Error:',
		    theme: 'black',
		    columnClass: 'col-md-6 col-md-offset-3',
		    content: msg,
		    confirmButtonClass: 'btn-danger'
		});
    }
};
