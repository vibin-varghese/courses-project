package com.course.info;

import java.util.List;
import java.util.Map;

import com.course.model.Course;

/**
 * CourseInfo is an interface of useful methods for 
 * CRUD operations on database
 * @author Vibin(1509175)
 *
 */
public interface CourseInfo {
	
	public static final String COURSE_ID = "courseId";
	public static final String COURSE_NAME = "courseName";
	public static final String COURSE_LEADER = "courseLeader";
	public static final String COURSE_CREDITS = "courseCredits";
	public static final String COURSE_DURATION = "courseDuration";
	
	public String saveCourse(List<Course> course);
	
	public String addCourse(Course course);
	
	public String updateCourse(Course course);
	
	public String removeCourse(String courseId);
	
	public List<Course> retrieveCourse(String courseId);
	
	public List<Course> searchCourse(String searchStr);
	
	public String saveXMLCourseData(Map<String, String> data);

	public int getCountPaging(int maxRecordInPage, String name);
}
