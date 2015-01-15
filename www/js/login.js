var API_BASE_URL="http://localhost:8080/photo-api";
var username;
var password;


$("#button-register").click(function(e) {
	e.preventDefault();
	if ( $('#RegisterPassword').val()=="" || $('#RegisterUsername').val()=="" )
	{}
	else {
	var user = new Object();
	user.username = $('#RegisterUsername').val();
	user.password = $('#RegisterPassword').val();
	register(JSON.stringify(user));	
	}
});

$("#button-sign-in").click(function(e) {
	e.preventDefault();
	if ( $('#SignInPassword').val()=="" || $('#SignInUsername').val()=="" )
	{}
	else {
	var user2 = new Object();
	user2.username = $('#SignInUsername').val();
	user2.password = $('#SignInPassword').val();
	login(JSON.stringify(user2));	
	}
});

function register(user){
	var url = API_BASE_URL + '/users/register';
	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		contentType: 'application/vnd.photo.api.user+json',
		data: user
	}).done(function (data, status, jqxhr) {
			window.location.replace("login.html");
	}).fail(function (jqXHR, textStatus) {
		$("#alertregister").show();
	});
}

function login(user){
	var url = API_BASE_URL + '/users/login';
	$.ajax({
		url : url,
		type : 'POST',
		crossDomain : true,
		contentType: 'application/vnd.photo.api.user+json',
		data: user
	}).done(function (data, status, jqxhr) {
		$.cookie("username", data.username);
		//$.cookie("password", user.password);
		//console.log('username:'+user.username);
		console.log($.cookie("username"));
		window.location.replace("main.html");
		
	}).fail(function (jqXHR, textStatus) {
		$("#alertlogin").show();
	});
}


