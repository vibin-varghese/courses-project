package com.course.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *  Information about the courses
 * @author Vibin
 *
 */
@XmlRootElement(name="course")
@XmlAccessorType (XmlAccessType.FIELD)
@Entity
public class Course {

	@Id   
	@SequenceGenerator(name="seq", initialValue=1, allocationSize=1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq")
	private Long courseId;
	private String courseName;
	private int courseCredits;
	private int courseDuration;
	private String courseLeader;
	
	public Course() {
		
	}	
	
	public Course(int courseId, String courseName) {
		
	}
	
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getCourseCredits() {
		return courseCredits;
	}

	public void setCourseCredits(int courseCredits) {
		this.courseCredits = courseCredits;
	}

	public int getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(int courseDuration) {
		this.courseDuration = courseDuration;
	}

	public String getCourseLeader() {
		return courseLeader;
	}

	public void setCourseLeader(String courseLeader) {
		this.courseLeader = courseLeader;
	}

	public Long getCourseId() {
		return courseId;
	}

	public void setCourseId(Long courseId) {
		this.courseId = courseId;
	}
}
