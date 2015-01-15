var API_BASE_URL="http://localhost:8080/photo-api";

$(document).ready(function(){
var username = $.cookie("username");
var password = $.cookie("password");
$("#UserLogged").text(username);
getPhotosMain(username);


});

$("#navbarinicio").click(function(e) {
	e.preventDefault();
	window.location.replace("main.html");
});


$("#searchfoto").click(function(e) {
	e.preventDefault();
	$.cookie("busqueda", ("#fotobuscada").val());
	$.cookie("fototitle", ("#fotobuscada").val());
	window.location.replace("busqueda.html");
});



$("#searchuser").click(function(e) {
	e.preventDefault();
	getUsuario($("#userbuscado").val());
});


	
$("#cat1").click(function(e) {
	e.preventDefault();
	$.cookie("busqueda",("moda").val();
	$.cookie("searchcat", "moda");
	window.location.replace("busqueda.html");
});
	
$("#cat2").click(function(e) {
	e.preventDefault();
	$.cookie("searchcat", "deportes");
	window.location.replace("busqueda.html");
});

$("#cat3").click(function(e) {
	e.preventDefault();
	$.cookie("searchcat", "coches");
	window.location.replace("busqueda.html");
});

$("#cat4").click(function(e) {
	e.preventDefault();
	$.cookie("searchcat", "viajes");
	window.location.replace("busqueda.html");
});

	function getUsuario(search) {
	var url = API_BASE_URL + '/users/' + search;


	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		contentType: 'application/vnd.photo.api.user+json',
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
	
				var user = data;
				window.location.replace("profile.html");

			}).fail(function() {
				$('<div class="alert alert-danger"> <strong>Oh!</strong> El usuario no existe  </div>').appendTo($("#searchuserform"));
	});
	}
	
	
	
	
	
	function getPhotosMain(username) {
	var url = API_BASE_URL + '/photos/user/' + username;
	
	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json',
	}).done(function(data, status, jqxhr) {
				var imatges = data.photos;
				
				$.each(imatges, function(i, v) {
					var imatge = v;
					
					$('<li><img src="img/'+imatge.idphoto +'" alt="#"  /></li>').appendTo($('#galeriamain'));
				});
				

	}).fail(function() {
		alert;
	});
}

