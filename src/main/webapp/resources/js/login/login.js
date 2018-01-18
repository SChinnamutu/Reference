$(document).ready(function(){
	$('.carousel').carousel();
	
	submitLogin = function(saltStr){
		var username= $('#username').val();
		var pwd= $('#password').val();
		if($.trim(username).length == 0 || $.trim(pwd).length == 0){
			alert('Username/password cannot be empty.');
			return false;
		} 
		console.log('saltStr', saltStr);
		var encSaltPass = encryptLoginPassword(saltStr,username,password);
		var encSaltSHAPass = encryptSha2LoginPassword(saltStr,username,password);
		console.log('encSaltPass', encSaltPass);
		console.log('encSaltSHAPass', encSaltSHAPass);
		return true;
	};
});


$(document).ready(function(){
		var IDLE_TIMEOUT = 20; //seconds
		var _idleSecondsCounter = 0;
		document.onclick = function() {
		    _idleSecondsCounter = 10;
		};
		document.onmousemove = function() {
		    _idleSecondsCounter = 10;
		};
		document.onkeypress = function() {
		    _idleSecondsCounter = 10;
		};
		window.setInterval(CheckIdleTime, 1000);
		
		function CheckIdleTime() {
		    _idleSecondsCounter++;
		    var oPanel = document.getElementById("SecondsUntilExpire");
		    if (oPanel)
		        oPanel.innerHTML = (IDLE_TIMEOUT - _idleSecondsCounter) + "";
		    if (_idleSecondsCounter >= IDLE_TIMEOUT) {
		        alert("Time expired!");
		        document.location.href = "login.jsp";
		    }
		}  
});		