(function(window){
	var lastRequestTime, timeoutHandle;
	//register the interceptor as a service
	mainApp.factory('perfInterceptor', ['$q', '$rootScope', function($q, $rootScope) {
	  var loadingCount = 0;
	  return {
	    'request': function(config) {
	        if(++loadingCount === 1)
	            $rootScope.$broadcast('loading:progress');
	        if(timeoutHandle){
	            window.clearTimeout(timeoutHandle);
	        }
	        perfUtils.getInstance().init();
	        lastRequestTime = new Date().getTime();
	        config.headers = config.headers || {};
	        return config;
	    },
	    'requestError': function(rejection) {
	        return $q.reject(rejection);
	    },
	    'response': function(response) {
	        if(--loadingCount === 0)
	            $rootScope.$broadcast('loading:finish');
	        if(response.data.status === 500){
	        	perfUtils.getInstance().errorMsg('An Error Occured!');
	    		return $q.reject(response);
	        } else if(response.data.status === 409){
	    		perfUtils.getInstance().errorMsg(response.data.entity.errorMessage);
	    		return $q.reject(response);
	    	}
	        return response;
	    },
	    'responseError': function(rejection) {
	        if(--loadingCount === 0)
	           $rootScope.$broadcast('loading:finish');
	        if (rejection.status === 401) {
	           window.location.href = "logout";
	        }
	        return $q.reject(rejection);
	    }
	  };
	}]);
	
	mainApp.config(['$httpProvider', function($httpProvider){
	    $httpProvider.interceptors.push('perfInterceptor');
	}]);
	
	var $menu = $('#menu');
	var $btnMenu = $('.btn-menu');
	var $img = $('img');
	
	//set home li selected on login
	$('#menu li a[href="#/home"]').parent().addClass('mm-selected');
	
	// mmenu customization
	$menu.mmenu({
	  navbars: [
	      {
		    position: "top",
		    content: [ "searchfield", "breadcrumbs" ],
		    height: 2
		  },
		  {
		      "position": "bottom",
		      "content": [
		         "<span id='version' class='fa' href='#/'>Version:</span>"
		      ]
		  }
	  ],
	  extensions: ['widescreen', 'theme-dark', 'effect-menu-slide'],
	  offCanvas: {
	    position  : "left",
	    zposition : "back"
	  },
	  setSelected: true,
	  onClick: {
	      setSelected: true
	  },
	  searchfield: true
	}).on('click', 'a[href^="#/"]', function() {
		$('#menu li.mm-selected').removeClass('mm-selected');
		$(this).parents().addClass('mm-selected');
	    window.location.href=$(this).attr('href');
	    return false;
	});
	
	// toggle menu
	var api = $menu.data("mmenu");
	
	$('#sidePanel').on('click', function(e) {
	    e.preventDefault();
	    if ($(this).hasClass('mm-opened')) {
	        api.close();
	        $menu.hide();
	    } else {
	        api.open();
	        $menu.show();
	    }
	});
	
	// change toggle behavior for subpanels
	$menu.find(".mm-next").addClass("mm-fullsubopen");
	
	//Dashboard 1 click event on actions
	$('#divContainer').on('click', '.feature i',  function() {
	    window.location.href=$(this).attr('nav');
	});
	
	/*
	 * Reset the form whenever its closed.
	 */
	$(document).on('hidden.bs.modal', 'div[role="dialog"]', function () {
		var formId=$(this).attr('id');
		$('#'+formId+' .help-block').empty();
		$('#'+formId+' p.text-danger').remove();
		$('#'+formId+' .has-error').removeClass('has-error');
		if(typeof scope !== 'undefined')
			scope.data = {};
	});
}(window));