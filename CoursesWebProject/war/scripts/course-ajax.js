/**
 *   this function takes user from current page to update page 
 *   based based on JSON format
 */
function setOnClickUpdateJSON() {
	$('a').click(function() {
	  var courseId = $(this).attr('id');
	  if($(this).attr('name') == "update") {
		  $("#course-id").val(courseId);
		  $('#select-tech').val("insertjson").change();
		  }
		});
}

/**
 *   this function takes user from current page to update page 
 *   based based on XML format
 */
function setOnClickUpdateXML() {
	$('a').click(function() {
	  var courseId = $(this).attr('id');
	  if($(this).attr('name') == "update") {
		  $("#course-id").val(courseId);
		  $('#select-tech').val("insertxml").change();
		  }
		});
}
/**
 * when user clicks delete on a course record,
 * it asks for confirmation if yes, then deletes the record
 * otherwise no action
 */
function setOnClickDelete() {
	
 $('a').click(function() {

if($(this).attr('name') == "delete") { 
	var courseId = $(this).attr('id');
	var params =
     { 
	   courseId: courseId
	 };
    if (confirm('Are you sure you want to delete the record?')) {
		$.ajax({ 
			url: "delete-courses",
			data: params,
	        success: showCourses });
		} else {
		    // Do nothing!
			}
	 }
  });
}

function next() {
	var pageCount = $("#paging").val();
	$("#deleteInfo").hide();
	$("#paging").val(parseInt(pageCount, 10) + 1);

	if($("#paging").val() == $("#nextpaging").val()-1) {
		disablePagingNext();
	}

	callCourseServlet();
}

function previous() {
	$("#deleteInfo").hide();
	
	var pageCount = $("#paging").val();
	$("#paging").val(parseInt(pageCount, 10) - 1);

	if($("#paging").val() < $("#nextpaging").val()) {
		enablePagingNext();
	}
	

	
	callCourseServlet();
}

/**
 *  main function is called when user clicks on delete link and contacts 
 *  server to fetch if any records stored
 *  It also displays the record deleted message 
 */
function showCourses() {
	var params =
     { 
	   courseId: $("#course-id").val()
     };

	callCourseServlet();
	$("#deleteInfo").html("<h1>Record Deleted</h1>")
	$("#deleteInfo").show();
}

/**
 * contacts server to fetch if any records(courses) stored in DB
 */
function callCourseServlet() {

	checkSearchParameterChanged();
	
	var pagingCount =
    { 
	   maxRows: $("#maxRows").val(),
	   courseName: $("#name").val(),
    };
	var params =
     { 
	   courseName: $("#name").val(),
	   format: $("#select-tech").val(),
	   maxRows: $("#maxRows").val(),
	   paging: $("#paging").val()
     };
	
	if($("#select-tech").val() == "xml") {
		//call servlet to return how many paging count required to display 
		//all courses
		$.ajax({ url: "paging-courses",
	        data: pagingCount,
	        type: "POST",
	        success: setPagingNos });
		//call a servlet to fetch courses to display
		$.ajax({ url: "show-courses",
	        dataType: "xml",
	        type: "POST",
	        data: params,
	        success: showXMLCourses });
	}
	else if ($("#select-tech").val() == "json") {
		//call servlet to return how many paging count required to display 
		//all courses
		$.ajax({ url: "paging-courses",
	        data: pagingCount,
	        type: "POST",
	        success: setPagingNos });
		//call a servlet to fetch courses to display
		$.ajax({ url: "show-courses",
	        dataType: "json",
	        type: "POST",
	        data: params,
	        success: showJsonCourses });		
	}
	else if ($("#select-tech").val() == "string") {
		//call servlet to return how many paging count required to display 
		//all courses
		$.ajax({ url: "paging-courses",
	        data: pagingCount,
	        type: "POST",
	        success: setPagingNos });
		//call a servlet to fetch courses to display
		$.ajax({ url: "show-courses",
	        	dataType: "text",
	        	type: "POST",
		        data: params,
		        success: showTextCourses });
		}
}

/**
 * displays any courses available in tabular format
 * this function is called when we use String(Text format )
 * @param rawData(response data from server)
 * @returns
 */
function showTextCourses(rawData) {
	    var rowStrings = rawData.split(/[\n\r]+/);
    var headings = ["Course ID","Course Name","Credits","Duration(Yr)","Leader", " "];
    var rows = new Array(rowStrings.length-1);
    for(var i=1; i<rowStrings.length; i++) {
      rows[i-1] = rowStrings[i].split("##");
    }
    if(rows.length == 1) {
		if($("#paging").val() == 0)
			return showNoResults();
		else
			previous();
    }
    rows = checkBlankRows(rows);
    var table = getTable(headings, rows);
    
    pagingFunction(rows);
    
    if(rows.length > 0) {
    	$("#hideResultsName").show();
    }
    $("#course-name-results").html(table);
    $("#course-name-results").show();
	    setOnClickUpdateJSON();
	    setOnClickDelete();
	}

/**
 * removes any blank rows in result(rows)
 * @param rows
 * @returns {Array}
 */
function checkBlankRows(rows) {
	
	var newRows = new Array(rows.length-1);
    for(var i=0; i<rows.length-1; i++) {
	      newRows[i] = rows[i];
	}
   return newRows;
}

/**
 * displays any courses available in tabular format
 * this function is called when we use XML format )
 * @param xmlDocument(response data from server)
 * @returns
 */
function showXMLCourses(xmlDocument) {
	var headings = ["Course ID", "Course Name", "Credits", "Duration(Yr)", "Leader", " "]; 
    var course = xmlDocument.getElementsByTagName("course");
    if(course.length == 0) {
		if($("#paging").val() == 0)
			return showNoResults();
		else
			previous();
    }
    var rows = new Array(course.length);
    var subElementNames = ["courseId", "courseName", "courseCredits", "courseDuration", "courseLeader", " "];
    for(var i=0; i<course.length; i++) {
      
      rows[i] = getElementValues(course[i], subElementNames);
    }
    var table = getTable(headings, rows);
	pagingFunction(rows);
    
    if(rows.length > 0) {
    	$("#hideResultsName").show();
    }
    $("#course-name-results").html(table);
    $("#course-name-results").show();
    setOnClickUpdateXML();
    setOnClickDelete();
}

/**
 * displays any courses available in tabular format
 * this function is called when we use JSON format )
 * @param jsonData(response data from server)
 * @returns
 */
function showJsonCourses(jsonData) {
    var headings = ["Course ID", "Course Name", "Credits", "Duration(Yr)", 
                "Leader", " "];     
	var rows = new Array();
	if(jsonData.length == 0) {
		if($("#paging").val() == 0)
			return showNoResults();
		else
			previous();
	}
	for(var i=0; i<jsonData.length; i++) {       
		var course = jsonData[i];       
		var link = createHyperLink(course.courseId, course.courseName);
		var dellink = createHyperLinkDelete(course.courseId);
		rows[i] = [course.courseId, link, course.courseCredits, 
		           course.courseDuration, course.courseLeader, dellink];    
	}   
	var table = getTable(headings, rows);
	pagingFunction(rows);
	if(rows.length > 0) {
    	$("#hideResultsName").show();
    }
	$("#course-name-results").html(table);
	$("#course-name-results").show();
	setOnClickUpdateJSON();
	setOnClickDelete();
}

function findMaxRows() {
	jQuery.get("./resources/config.txt", function(rawData) {
		   
	    var rowStrings = rawData.split(/[\n\r]+/);
	    for(var i=0; i<rowStrings.length; i++) {
	    	if(rowStrings[i].search("maxRows") == 0){ 
	            $("#maxRows").val(rowStrings[i].split("=")[1]);
	            continue;
	    	}
	    }
    });
}
