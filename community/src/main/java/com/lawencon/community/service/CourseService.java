package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.CourseDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.Course;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.course.PojoCourseInsertReq;

public class CourseService {
	private final CourseDao courseDao;
	private final CategoryDao categoryDao;
	private final FileDao fileDao;
	
	public CourseService(final CourseDao courseDao, final CategoryDao categoryDao, FileDao fileDao) {
		this.courseDao = courseDao;
		this.categoryDao = categoryDao;
		this.fileDao = fileDao;
	}
	
	public Course insert(final Course data) {
		Course insertCourse = null;
		ConnHandler.begin();
		insertCourse = insert(data);
		ConnHandler.commit();
		return insertCourse;
	}
	
	public Course update(final Course data) {
		Course updateCourse = null;
		ConnHandler.begin();
		updateCourse = update(data);
		ConnHandler.commit();
		return updateCourse;
	}
	
	public Optional<Course> getById(final String id){
		return courseDao.getById(id);
	}
	
	public Course getByIdAndDetach(final String id) {
		return courseDao.getByIdAndDetach(id);
	}
	
	public List<Course> getAll(){
		return courseDao.getAll();
	}
	
	public PojoInsertRes insertRes(final PojoCourseInsertReq data) {
		final PojoInsertRes pojo = new PojoInsertRes();
		final Course course = new Course();
		
		return pojo;
	}
	
}
