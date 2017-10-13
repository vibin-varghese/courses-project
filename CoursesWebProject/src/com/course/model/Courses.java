package com.course.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "courses")
@XmlAccessorType (XmlAccessType.FIELD)
public class Courses 
{
    @XmlElement(name = "course")
    private List<Course> courses = null;
 
    public List<Course> getEmployees() {
        return courses;
    }
 
    public void setEmployees(List<Course> courses) {
        this.courses = courses;
    }
}
