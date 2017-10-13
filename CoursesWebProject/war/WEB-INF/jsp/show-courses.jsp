Course ID##Course Name##Credits##Duration(Yr)##Leader## 
<%@page import="com.course.model.Course"%>
<%@page import="java.util.List"%>
<% List<Course> course = (List<Course>)request.getAttribute("courses"); %>
<% for(int i=0;i<course.size();i++) { %>
<% out.print(course.get(i).getCourseId()); %>##<a href="#" id="<%= course.get(i).getCourseId() %>" name="update"><% out.print(course.get(i).getCourseName()); %></a>##<% out.print(course.get(i).getCourseCredits()); %>##<% out.print(course.get(i).getCourseDuration()); %>##<% out.print(course.get(i).getCourseLeader());%>##<a href="#" name="delete" id="<%= course.get(i).getCourseId()%>"><img id="deleteimage" src="./images/delete.png"/></a>
<%}%>