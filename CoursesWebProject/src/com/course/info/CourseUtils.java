package com.course.info;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import com.course.model.Course;

public class CourseUtils {

	@SuppressWarnings("unchecked")
	/**
	 * Populates Array with course bean from JSON object
	 * @param jobj
	 * @return
	 */
	public static List<Course> populateField(JSONObject jobj) {
		
		List<Course> courseList = new ArrayList<>();
		Course course = new Course();
		Iterator<String> it = jobj.keys();
    	while(it.hasNext())
    	{
    		try {
    			findCourseField(jobj, it.next(), course);
    		} catch (JSONException e) {
    			e.printStackTrace();
    		}
    	}	
    	courseList.add(course);
    	return courseList;
	}
	
	/**
	 * Finds the course bean field from json object 
	 * @param json
	 * @param key
	 * @param c
	 * @throws JSONException
	 */
	public static void findCourseField(JSONObject json, String key, Course c) 
			throws JSONException{
	
		switch(key) {
			case "courseId" : {
				String value = json.getString(key);
				if(value !=null && value.equalsIgnoreCase(""))
					c.setCourseId(0L);
				else
					c.setCourseId(Long.parseLong(value));
				break;
			}
			case "courseLeader" :
				c.setCourseLeader(json.getString(key));
				break;
			case "duration" :
				c.setCourseDuration(Integer.parseInt(json.getString(key)));
				break;
			case "courseCredits" :
				c.setCourseCredits(Integer.parseInt(json.getString(key)));
				break;
			case "courseName" :
				c.setCourseName(json.getString(key));
				break;
		}
	}
}
