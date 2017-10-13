package com.course.info;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.course.dao.CoursesDAO;
import com.course.model.Course;
/**
 * Implemenation class of inteface CourseInfo.java which has methods
 * for interaction with the database
 * 
 * @author Vibin(15091275)
 *
 */
public class CourseInfoImpl implements CourseInfo {
	
	/**
	 *  @param List<Courses>
	 */
	@Override
	public String saveCourse(List<Course> courseList) {
		
		String msg="";
		for(Course c : courseList) {
				if(c.getCourseId() == 0) {
					msg = addCourse(c);
				}
				else {
					msg = updateCourse(c);	
				}
		}
		return msg;
	}
	
	@Override
	public List<Course> searchCourse(String searchStr) {
		
		return CoursesDAO.INSTANCE.getCourseByName(searchStr);
	}

	@Override
	public String addCourse(Course course) {
		String msg="";
		CoursesDAO.INSTANCE.insertCourse(course);
		msg = insertMessage();
		return msg;	
	}
	
	@Override
	public List<Course> retrieveCourse(String courseId) {
		return CoursesDAO.INSTANCE.getCourseById(courseId);
	}


	@Override
	public String removeCourse(String courseId) {
		String msg="";
		CoursesDAO.INSTANCE.deleteCourse(Long.parseLong(courseId));
		msg = insertMessage();
		return msg;	
	}

	@Override
	public String updateCourse(Course course) {
		String msg="";
		CoursesDAO.INSTANCE.updateCourse(course);
		msg = updateMessage();
		return msg;
	}
	
	/**
	 *  Insert Course data in data.
	 *  Data is getting populated from XML form 
	 */
	public String saveXMLCourseData(Map<String, String> data) {
		
		String msg = "";
		Course c = new Course();	
		String id = data.get(CourseInfo.COURSE_ID);
		if(id == null || id.equalsIgnoreCase(""))
			c.setCourseId(0L);
		else
			c.setCourseId(Long.parseLong(data.get(CourseInfo.COURSE_ID)));
		c.setCourseName(data.get(CourseInfo.COURSE_NAME));
		c.setCourseLeader(data.get(CourseInfo.COURSE_LEADER));
		c.setCourseCredits(Integer.parseInt(data.get(CourseInfo.COURSE_CREDITS)));
		c.setCourseDuration(Integer.parseInt(data.get(CourseInfo.COURSE_DURATION)));
		
		if(c.getCourseId() == 0) {
			msg = addCourse(c);
		}
		else {
			msg = updateCourse(c);	
		}
		
		return msg;
	}
	
	// Message
	public String updateMessage() {
		return "<h2>Success!! Course Information Updated</h2>";
	}
	// Message
	public String insertMessage() {
		return "<h2>Success!! Course Information Added</h2>";
	}

	// Message
	public String errorMessage() {
		return "<h2>Fail!! Ensure data is correct.</h2>";
	}

	/**
	 * Get the page count (how many paging we need)
	 */
	@Override
	public int getCountPaging(int maxRecordInPage, String name) {
		
		int countDB = CoursesDAO.INSTANCE.getCountCourses(name);
		int pagingLength = countDB/maxRecordInPage;
		if(countDB % maxRecordInPage == 0){
			return pagingLength;
		}
		return pagingLength+1;
	}

}
