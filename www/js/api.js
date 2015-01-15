var PHOTOS_API_HOME="http://localhost:8080/photo-api";
var USERNAME = "cris";
var PASSWORD = "1234";

$.ajaxSetup({
    headers: { 'Authorization': "Basic "+ btoa(USERNAME+':'+PASSWORD) }
});

function Link(rel, linkheader){
	this.rel = rel;
	this.href = decodeURIComponent(linkheader.find(rel).template().template);
	this.type = linkheader.find(rel).attr('type');
	this.title = linkheader.find(rel).attr('title');
	
	function buildLinks(linkheaders){
	var links = {};
	$.each(linkheaders, function(i,linkheader){
		var linkhdr = $.linkheaders(linkheader);
		var rels = linkhdr.find().attr('rel').split(' ');
		$.each(rels, function(key,rel){
			var link = new Link(rel, linkhdr);
			links[rel] = link;
		});
	});

	return links;
	}
}
$("#button_sign_in").click(function(e) {
	e.preventDefault();
	getUser($("#Username").val());
});

function getUser(Username) {
	var url = API_BASE_URL + '/Users/' + USERNAME + '/' + PASSWORD;
	$("#get_USER_RESULT").text('');

	$.ajax({
		url : url,
		type : 'GET',
		crossDomain : true,
		dataType : 'json'
	}).done(function(data, status, jqxhr) {

		var USER = data;

		$("#get_USER_RESULT").text('');
		$('<h4> Name: ' + USER.name + '</h4>').appendTo($('#get_USER_RESULT'));
		$('<p>').appendTo($('#get_USER_RESULT'));	
			
			

	}).fail(function() {
		$('<div class="alert alert-danger"> <strong>Oh!</strong> Repository not found </div>').appendTo($("#get_USER_RESULT"));
	});


}