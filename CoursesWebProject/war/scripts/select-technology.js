$(function() {

	$("#select-tech").change(function() {
		$("#deleteInfo").hide();
		 findPage($("#select-tech").val());
	});
	
	defaultPage();
})

function defaultPage() {
	   $("#find-page").load("json.html"); 
}

function findPage(id) {
	if(id == "json") {
	  $("#find-page").load("json.html");
	}
	else if(id == "xml") {
	  $("#find-page").load("xml.html");
	}
	else if(id == "string")
	  $("#find-page").load("string.html");
	else if(id == "insertjson")
	  $("#find-page").load("insertupdatejsoncourse.html");
	else if(id == "insertxml")
		  $("#find-page").load("insertupdatexmlcourse.html");

}