<!DOCTYPE html>
<html lang="en-us">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>Perficient Chennai - WFM</title>
	<base href="${pageContext.request.contextPath}/">
	<link rel="shortcut icon" type="image/x-icon" href="images/p.png" />
	<link rel="stylesheet" href="css/login.min.css">
	<!-- <script src="dist/js/dev/login.min.js"></script> -->
	<script src="js/lib/jquery.js"></script>
	<script src="js/lib/bootstrap.js"></script>
	<script src="js/hash/md5.js"></script>
	<script src="js/hash/sha512.js"></script>
	<script src="js/login/login.js"></script>
</head>
<body class="loginpage">
<%
 // Characters allowed for the salt string
 String SALTCHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
 StringBuffer salt = new StringBuffer();
 java.util.Random rnd = new java.util.Random();
 // build a random 9 chars salt 
 int aStart = 6;
 int aEnd = 10;
 long range = (long)aEnd - (long)aStart + 1;
 // compute a fraction of the range, 0 <= frac < range
 long fraction = (long)(range * rnd.nextDouble());
 int randomNumber =  (int)(fraction + aStart);
 while (salt.length() < randomNumber) {
   int index = (int) (rnd.nextFloat() * SALTCHARS.length());
   salt.append(SALTCHARS.substring(index, index+1));
 }
 String saltStr=salt.toString();
 // Salt String is stored in session so that  we can retrieve in the serverside which is used to add with encrypted(MD5) password retrieved from the database
 session.setAttribute("ran",saltStr);  
%>
<html:hidden property="ran" value="<%=saltStr%>"/>
	<section>
	<div class="carousel slide carousel-fade" data-ride="carousel">
	<!-- Wrapper for slides -->
		<div class="carousel-inner" role="listbox">
			<div class="item active"></div>
			<div class="item"></div>
			<div class="item"></div>
		</div>
	</div>
	<div class="opeiningscreen">
		<div class="logodiv">
			<a class="top-p" href="#"><img class="zoomin-p" src="images/p.png"></a>
			<ul class="brandname">
				<li>P</li>
				<li>E</li>
				<li>R</li>
				<li>F</li>
				<li>I</li>
				<li>C</li>
				<li>I</li>
				<li>E</li>
				<li>N</li>
				<li>T</li>
			</ul>
			<ul class="brandbottom">
				<li><img src="images/l1.png"></li>
				<li><img src="images/l2.png"></li>
				<li><img src="images/l3.png"></li>
			</ul>
			<div class="registerbody opacityanim">
				<div class="registerbody-left  col-xs-12">
					<form name="loginForm" onsubmit="return submitLogin('<%=saltStr%>')"  method="post" action="doLogin" modelAttribute="loginBean">
						<ul>
							<li>
								<div class="form-group">
									<label class="animatetoptobottom">Email Address:</label>
									<div class="input-divs animatetoptobottom">
										<input type="text" id="username" name="username" class="form-control" placeholder="Enter username" autofocus="true"  required>
									</div>
								</div>
							</li>
							<li>
								<div class="form-group">
								<div class="icnholder"><i class="regisr regit4"></i></div>
								<label class="animatetoptobottom">Password:</label>
								<div class="input-divs animatetoptobottom">
									<input type="password" id="password" name="password" class="form-control" placeholder="Enter password" required>
								</div>
								</div>
							</li>
							<li>
								<div class="form-group text-center">
									<input type="submit" class="btn btn-primary" value="Log In" />
								</div>
							</li>
						</ul>
					</form>
				</div>
			</div>
		</div>
	</div>
</section>
</body>
</html>