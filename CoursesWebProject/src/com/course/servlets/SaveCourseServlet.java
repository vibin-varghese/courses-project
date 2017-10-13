package com.course.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.course.info.CourseInfo;
import com.course.info.CourseInfoImpl;
import com.course.info.CourseUtils;
import com.course.model.Course;

/**
 * Servlet implementation class SaveCourseServlet
 */
@WebServlet("/SaveCourseServlet")
public class SaveCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveCourseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("unchecked")
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Pragma", "no-cache");

	    String param = request.getParameter("param");
	    List<Course> courseList = null;
	    try {
	    	JSONObject jObj = new JSONObject(param); 
	    	courseList = CourseUtils.populateField(jObj);
	    } catch (JSONException e) {
	    	e.printStackTrace();
	    }
	
		CourseInfo courseInfo = new CourseInfoImpl();
		String msg = courseInfo.saveCourse(courseList);
		
		PrintWriter pw = response.getWriter();
		pw.println(msg);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
