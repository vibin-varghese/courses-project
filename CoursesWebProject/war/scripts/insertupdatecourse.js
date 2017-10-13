
$(document).ready(function() {
	
	$("#courseDuration").change(insertCredits);
	$("#save-course").click(saveCourse);
	$("#reset-course").click(resetData);
	$("#name").focus(function() {
		$( "#course-save-results" ).empty();
	})
	$("#courseDuration").focus(function() {
		$( "#course-save-results" ).empty();
	})
	$("#courseCredits").focus(function() {
		$( "#course-save-results" ).empty();
	})
	$("#courseLeader").focus(function() {
		$( "#course-save-results" ).empty();
	});
	if($("#course-id").val()>0) {
		retrieveCourse($("#course-id").val());
		$("#updateHeading").show();
		$("#insertHeading").hide();
	}
	else {
		$("#updateHeading").hide();
		$("#insertHeading").show();		
	}
});

function insertCredits() {
	if($("#courseDuration").val() == 1) {
		$("#courseCredits").val("100");
	}
	if($("#courseDuration").val() == 2) {
		$("#courseCredits").val("200");
	}
	if($("#courseDuration").val() == 3) {
		$("#courseCredits").val("300");
	}
	if($("#courseDuration").val() == 4) {
		$("#courseCredits").val("400");
	}
}

function validate() {
	var check;
	if(($("#name").val()) == "") {
		check = true;
	} else if(($("#courseLeader").val()) == "") {
		check = true;
	} else if(($("#courseDuration").val()) == 0) {
		check = true;
	}
	return check;
}

/**
 * Makes JSON String to be send to server
 * @returns {String}
 */
function makeJSONParamterString() {
	
	var params = 
    { 
	   courseName: $("#name").val(),
	   duration: $("#courseDuration").val(),
	   courseCredits: $("#courseCredits").val(),
	   courseLeader: $("#courseLeader").val(),
	   courseId: $("#course-id").val()
	 };

	  return "param=" + escape(JSON.stringify(params));
}

/**
 * Makes XML SOAP Message to be send to server
 * @returns {String}
 */

function makeXMLParamterString() {
	
	var xmlparam = '<?xml version="1.0" encoding="utf-8"?>' +
	 '<soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" ' +
	                'xmlns:xsd="http://www.w3.org/2001/XMLSchema" ' +
	                'xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">' + 
	   '<soap:Body>' +
	     '<course xmlns="http://www.webserviceX.NET/">' +
	       '<courseId>' + $("#course-id").val() + '</courseId>' +
	       '<courseName>' + $("#name").val() + '</courseName>' +
	       '<courseDuration>' + $("#courseDuration").val() + '</courseDuration>' +
	       '<courseCredits>' + $("#courseCredits").val() + '</courseCredits>' +
	       '<courseLeader>' + $("#courseLeader").val() + '</courseLeader>' +
	     '</course>' +
	   '</soap:Body>' +
	 '</soap:Envelope>';

	  return xmlparam;
}

/**
 *  Save and inserts the course record in database 
 *  whether its json or xml forms
 */
function saveCourse() {

	  var move = validate();
	  if(!move && $("#select-tech").val() == 'insertjson') {
		  var params = makeJSONParamterString();
	      $.ajax({ 
			url: "save-courses",
			dataType: "html",
			type: "POST",
	        data: params,
	        success : saveMessage
	       });
	  }
	  else if (!move && $("#select-tech").val() == 'insertxml'){
		  var params = makeXMLParamterString();
	      $.ajax({ 
			url: "save-xml-courses",
			dataType: "xml",
			type: "POST",
	        data: params,
	        contentType: "text/xml",
	        success : saveXMLMessage
	       });
		  
	  }
	  else {
		  errorMessage();
	  }
}

/**
 * Message received from server in SOAP format
 * @param xmldata
 */
function saveXMLMessage(xmldata) {
	var resp = xmldata.getElementsByTagName("Response")[0];
    $( "#course-save-results" ).html(getBodyContent(resp));
    resetData();
}

/**
 * Retrieves course from DB based on courseId(id)
 * @param id
 */
function retrieveCourse(id) {
	var param =
    { 
	   courseId: id
	 };
	
     $.ajax({ 
			url: "show-courses",
			dataType: "json",
			method: "POST",
	        data: param,
	        success : displayCourse
	       });
}

/**
 * Displays course in form
 * @param jsonData
 */
function displayCourse (jsonData) {
	for(var i=0; i<jsonData.length; i++) { 
    	var course = jsonData[i];       
    	$("#name").val(course.courseName);
    	$('#courseDuration').val(course.courseDuration).change();
    	$("#courseLeader").val(course.courseLeader);
    }  
}

//Message displayed
function saveMessage(data) {
	$( "#course-save-results" ).html( data );
	resetData();
}

//Error Message
function errorMessage(data) {
	$( "#course-save-results" ).html( "<h2>Please enter required data.</h2>" );
}

/**
 * Resets data
 */
function resetData() {
	$("#course-id").val("");
	$("#name").val("");
	$("#courseDuration").val("");
	$("#courseLeader").val("");
	$("#courseCredits").val("");
	$("#updateHeading").hide();
	$("#insertHeading").show();		
}

