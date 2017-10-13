package com.course.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.json.JSONArray;

import com.course.info.CourseInfo;
import com.course.info.CourseInfoImpl;
import com.course.model.Course;
import com.course.model.Courses;

/**
 * Servlet implementation class CourseServlet
 */
@WebServlet("/CourseServlet")
public class CourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CourseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Pragma", "no-cache");

		String format = request.getParameter("format");
		int id = 0;
		String idparam = request.getParameter("courseId");
		if(idparam != null && !idparam.equalsIgnoreCase("")) {
		  id = Integer.parseInt(request.getParameter("courseId")); 
		}
		//System.out.println(format);
		if(format == null) {
			format = "json";
		}
		String courseName = request.getParameter("courseName");
		
		CourseInfo course = new CourseInfoImpl();
		
		List<Course> result;
		if(id > 0) {
		   result = course.retrieveCourse(String.valueOf(id)); 
		} else {
			result = course.searchCourse(courseName);
		}
		
		if(id == 0)
		 result = checkPaging(result, request.getParameter("paging"), request.getParameter("maxRows"));
		
		JSONArray arr=null;
		String outputPage=null;
		Marshaller jaxbMarshaller = null;
		
		if("xml".equalsIgnoreCase(format)) {
			response.setContentType("text/xml");
			try {
					JAXBContext jaxbContext = JAXBContext.newInstance(Courses.class);
				    jaxbMarshaller = jaxbContext.createMarshaller();
					jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			     } catch (JAXBException e) {
				    e.printStackTrace();
 	             }
	    } else if ("json".equalsIgnoreCase(format)) {
	      response.setContentType("application/json");
	      arr = new JSONArray(result);
	      //System.out.println(arr);
	    } else {
	      response.setContentType("text/plain");
	      request.setAttribute("courses", result);
	      outputPage = "/WEB-INF/jsp/show-courses.jsp";
	    }
		
		if("json".equalsIgnoreCase(format)) {
	    	PrintWriter pw = response.getWriter();
	        pw.println(arr);
	    } else if ("xml".equalsIgnoreCase(format)) {
	    	try {
	    		PrintWriter pw = response.getWriter();
	    		Courses c = new Courses();
	    		c.setEmployees(result);
	    		jaxbMarshaller.marshal(c, pw);
	    	} catch (JAXBException e) {
	    		e.printStackTrace();
	    	}
	    }
	    else {
		    RequestDispatcher dispatcher =
		      request.getRequestDispatcher(outputPage);
		    dispatcher.include(request, response);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

	/**
	 * Returns only courses required for paging
	 * if in a page 10 rows required, returns 10 rows
	 * @param courses
	 * @param paging
	 * @param maxRows
	 * @return
	 */
	public List<Course> checkPaging(List<Course> courses, String paging, String maxRows) {
		int max = Integer.parseInt(maxRows);
		int fromIndex = Integer.parseInt(paging)* max;
		int toIndex = fromIndex + Integer.parseInt(maxRows);
		
		if( courses.size() == 0)
			return courses;
		
		if(courses.size() < toIndex) 
		{
			toIndex = courses.size();
		}
		return courses.subList(fromIndex, toIndex);
	}
}
