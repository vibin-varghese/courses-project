/**
 *  sets the hidden maxRows element to number of
 *  paging required to show all courses or any courses
 * @param count
 */
function setPagingNos(count) {
	$("#nextpaging").val(count);
}

/**
 * Disables or enables Previous button based on which page we are in
 * and number of records fetched
 */
function checkPagingPrevious() {

	if(($("#paging").val() > 0)) {
		  $("#previous").removeAttr('disabled');
	}
	else {
		  $("#previous").attr('disabled', 'disabled');
	}
}

/**
 * Disables or enables Next button based on which page we are in
 * and number of records fetched
 */
function checkPagingNext() {

	if($("#paging").val() == $("#nextpaging").val()-1) {
		$("#next").attr('disabled', 'disabled');
	}
	else {
		$("#next").removeAttr('disabled');
	}
}

/**
 * Disables Next button based on which page we are in
 * and number of records fetched
 */
function disablePagingNext() {
   $("#next").attr('disabled', 'disabled');
}

/**
 * Enables Next button based on which page we are in
 * and number of records fetched
 */
function enablePagingNext() {
	   $("#next").removeAttr('disabled');
	}

/**
 * This function shows previous and next button if any rows present
 * and enables/disables them based on number of records fetched.
 */
function pagingFunction(rows) {

	if(rows.length > 0) {
		$("#showpaging").show();
		checkPagingPrevious();
		checkPagingNext();
	} 
}


/**
 *  Extract values from XML
 * @param element 
 * @param subElementNames
 * @returns {Array}
 */
function getElementValues(element, subElementNames) {
	  var values = new Array(subElementNames.length);
	  var id;
	  for(var i=0; i<subElementNames.length; i++) {
	    var name = subElementNames[i];
	    var subElement = element.getElementsByTagName(name)[0];
	    if(name == " ") 
	       var content = "";
	    else
	       var content = getBodyContent(subElement);	 
	    if(name == "courseId") {
	    	id = content;
	    }
	    if(name == "courseName") {
	    	content = createHyperLink(id, content);
	    }
	    if(name == " ") {
	    	content = createHyperLinkDelete(id, "Delete");
	    }
	    
	    values[i] = content;
	  }
	  return(values);
	}

// Given an element, returns the body content.
function getBodyContent(element) {
  element.normalize();
  return(element.childNodes[0].nodeValue);
}

/**
 * Error Message to display when no courses found
 */
function showNoResults() {
	$( "#showpaging" ).hide();
	$( "#course-name-results" ).html( "<h2>No Courses Found!</h2>" );
	$( "#course-name-results" ).show();
}

/**
 * 
 * @param id
 * @param name
 * @returns {String}
 */
function createHyperLink(id, name) {
	return '<a href="#" name="update" id="' + id +'">'+name+'</a>'; 
}

function createHyperLinkDelete(id) {
	return '<a href="#" name="delete" id="' + id +'"><img id="deleteimage" src="./images/delete.png"/></a>'; 
}

// Takes as input an array of headings (to go into th elements)
// and an array-of-arrays of rows (to go into td
// elements). Builds an xhtml table from the data.

function getTable(headings, rows) {
  var table = "<table class='ajaxTable'>\n" +
              getTableHeadings(headings) +
              getTableBody(rows) +
              "</table>";
  return(table);
}

function getTableHeadings(headings) {
  var firstRow = "  <tr class='resultHeading'>";
  for(var i=0; i<headings.length; i++) {
    firstRow += "<th>" + headings[i] + "</th>";
  }
  firstRow += "</tr>\n";
  return(firstRow);
}

function getTableBody(rows) {
  var body = "";
  for(var i=0; i<rows.length; i++) {
    body += "  <tr class='resultRow'>";
    var row = rows[i];
    for(var j=0; j<row.length; j++) {
      body += "<td>" + row[j] + "</td>";
    }
    body += "</tr>\n";
  }
  return(body);
}

function checkSearchParameterChanged() {
	if($("#searchStr").val() !=  $("#name").val()) {
		$("#paging").val("0");
		$("#searchStr").val($("#name").val());
	}
}