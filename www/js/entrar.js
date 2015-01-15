var PHOTOS_API_HOME="http://localhost:8080/photo-api";
var username;
var password;

$("#button-regist").click(function(e) {
	e.preventDefault();
	$('#username-auth').val("");
	$('#password-auth').val("");
	$("#register-detail").show();
	$("#login-detail").hide();
});

$("#button-register").click(function(e) {
	e.preventDefault();
	if ( $('#password-regist').val()=="" || $('#RegistUsername').val()=="" )
	{
	   $("#error-auth-div").hide();
	   $("#alerta-auth-div").show();
	}
	else {
	
	var user = new Object();
	user.username = $('#RegistUsername').val();
	user.userpass = $('#password-regist').val();
		
	register(JSON.stringify(user));	
	}
});