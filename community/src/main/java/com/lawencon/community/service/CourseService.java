package com.lawencon.community.service;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.CourseDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.Course;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.course.PojoCourseInsertReq;
import com.lawencon.community.util.Generate;
import com.lawencon.security.principal.PrincipalService;

public class CourseService {
	private final CourseDao courseDao;
	private final CategoryDao categoryDao;
	private final CategoryService categoryService;
	private final FileDao fileDao;
	private final UserService userService;
	
	@Inject
	private PrincipalService principalService;
	
	public CourseService(final CourseDao courseDao, final CategoryDao categoryDao, final FileDao fileDao, final UserService userService, final CategoryService categoryService) {
		this.courseDao = courseDao;
		this.categoryDao = categoryDao;
		this.fileDao = fileDao;
		this.userService = userService;
		this.categoryService = categoryService;
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
	
	public Course getRefById(final String id) {
		return courseDao.getRefById(id);
	}
	
	public Course getByIdAndDetach(final String id) {
		return courseDao.getByIdAndDetach(id);
	}
	
	public List<Course> getAll(){
		return courseDao.getAll();
	}
	
	public PojoInsertRes insertRes(final PojoCourseInsertReq data) {
		final PojoInsertRes pojo = new PojoInsertRes();
		Course courseInsert = null;
		final Course course = new Course();
		File fileInsert = null;
		final File file = new File();
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		final Category category = categoryService.getRefById(data.getCategoryId());
		file.setFileName(data.getFile().getFileName());
		file.setFileContent(data.getFile().getFileContent());
		file.setFileExtension(data.getFile().getFileExtension());
		ConnHandler.begin();
		fileInsert = fileDao.insert(file);
		ConnHandler.commit();
		course.setCourseCode(Generate.generateCode(5));
		course.setCategory(category);
		course.setCourseName(data.getCourseName());
		course.setStartDate(data.getStartDate());
		course.setEndDate(data.getEndDate());
		course.setUser(user);
		course.setPrice(data.getPrice());
		course.setProvider(data.getProvider());
		course.setTrainer(data.getTrainer());
		course.setLocationName(data.getLocationName());
		course.setFile(fileInsert);
		courseInsert = insert(course);
		pojo.setId(courseInsert.getId());
		pojo.setMessage("Berhasil membuat course");
		return pojo;
	}
	
}
