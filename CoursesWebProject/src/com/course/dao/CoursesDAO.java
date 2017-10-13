package com.course.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.course.model.Course;  


public enum CoursesDAO {   
	INSTANCE;    
	
	public List<Course> listCourses() {     
		EntityManager em = EMFService.get().createEntityManager();      
		Query q = em.createQuery("select m from Course m");     
		List<Course> courses = q.getResultList();     
		return courses;   
		}    
	
	public void insertCourse(Course course) {
		
		synchronized (this) {      
			EntityManager em = EMFService.get().createEntityManager();      
			em.persist(course);       
			em.close();     
			}   
		}  
	
	/*
	 * This method updates the course 
	 * 
	 */
	public void updateCourse(Course course) {
		
		synchronized (this) {      
			EntityManager em = EMFService.get().createEntityManager(); 
			Course c = em.find(Course.class, course.getCourseId());
			c.setCourseLeader(course.getCourseLeader());
			c.setCourseCredits(course.getCourseCredits());
			c.setCourseDuration(course.getCourseDuration());
			c.setCourseName(course.getCourseName());
			em.persist(c);       
			em.close();     
			}   
		}  
	
	public List<Course> getCourseByName(String courseName) {     
		EntityManager em = EMFService.get().createEntityManager();     
		String sql = "select t from Course t";
		Query q = em.createQuery(sql); 
		List<Course> courses = q.getResultList();
		
		if(!courseName.equals(""))
		 courses = findCoursesByName(courses, courseName);
		
		return courses;   
	}   
	
	public List<Course> findCoursesByName(List<Course> courses, String name) {
		
		List<Course> newList = new ArrayList<>();
		for(Course c : courses) {
			if(c.getCourseName().contains(name)){
				newList.add(c);
			}
		}
		
		return newList;
	}
	
	public int getCountCourses(String name) {
		EntityManager em = EMFService.get().createEntityManager();     
		Query q = em.createQuery("select t from Course t");
		List<Course> courses = q.getResultList();
		
		if(!name.equals(""))
		 courses = findCoursesByName(courses, name);
		return courses.size();   
	}
	
	public List<Course> getCourseById(String courseId) {     
			EntityManager em = EMFService.get().createEntityManager();     
			Query q = em.createQuery("select t from Course t where t.courseId = :courseId");  
			q.setParameter("courseId", Long.parseLong(courseId));     
			List<Course> courses = q.getResultList();
			return courses;   
			}   
	
	public void deleteCourse(long id) {     
		EntityManager em = EMFService.get().createEntityManager();     
		try {       
			Course course = em.find(Course.class, id);       
			em.remove(course);     
		} 
		finally {       
			em.close();     
			}   
		} 
}