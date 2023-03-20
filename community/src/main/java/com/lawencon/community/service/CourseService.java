package com.lawencon.community.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.CategoryDao;
import com.lawencon.community.dao.CourseDao;
import com.lawencon.community.dao.FileDao;
import com.lawencon.community.dao.UserCourseDao;
import com.lawencon.community.model.Category;
import com.lawencon.community.model.Course;
import com.lawencon.community.model.File;
import com.lawencon.community.model.User;
import com.lawencon.community.model.UserCourse;
import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.course.PojoCourseGetAllRes;
import com.lawencon.community.pojo.course.PojoCourseInsertReq;
import com.lawencon.community.pojo.course.PojoCourseResGetByCategoryId;
import com.lawencon.community.pojo.course.PojoCourseUpdateReq;
import com.lawencon.community.pojo.course.PojoCourserGetAllResData;
import com.lawencon.community.pojo.usercourse.PojoUserCourseGetByUserIdRes;
import com.lawencon.community.pojo.usercourse.PojoUserCourseInsertReq;
import com.lawencon.community.util.Generate;
import com.lawencon.security.principal.PrincipalService;

@Service
public class CourseService {
	private final CourseDao courseDao;
	private final CategoryDao categoryDao;
	private final CategoryService categoryService;
	private final FileDao fileDao;
	private final UserService userService;
	private final UserCourseDao userCourseDao;
	
	@Inject
	private PrincipalService principalService;
	
	public CourseService(final CourseDao courseDao, final CategoryDao categoryDao, final FileDao fileDao, final UserService userService, final CategoryService categoryService, UserCourseDao userCourseDao) {
		this.courseDao = courseDao;
		this.categoryDao = categoryDao;
		this.fileDao = fileDao;
		this.userService = userService;
		this.categoryService = categoryService;
		this.userCourseDao = userCourseDao;
	}
	
	public Course insert(final Course data) {
		Course insertCourse = null;
		ConnHandler.begin();
		insertCourse =courseDao.insert(data);
		ConnHandler.commit();
		return insertCourse;
	}
	
	public Course update(final Course data) {
		Course updateCourse = null;
		ConnHandler.begin();
		updateCourse = courseDao.update(data);
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
	
	public boolean deleteById(final String id) {
		boolean eventDelete = false;

		try {
			ConnHandler.begin();
			eventDelete = courseDao.deleteById(Course.class, id);
			ConnHandler.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return eventDelete;

	}
	
	public PojoInsertRes insertRes(final PojoCourseInsertReq data) {
		final PojoInsertRes pojo = new PojoInsertRes();
		Course courseInsert = null;
		final Course course = new Course();
		File fileInsert = null;
		final File file = new File();
		final User user = userService.getByRefId(principalService.getAuthPrincipal());

		

		final Category category = categoryDao.getByIdRef(Category.class, data.getCategoryId());
		category.setId(data.getCategoryId());
		course.setCategory(category);
		

		file.setFileName(data.getFile().getFileName());
		file.setFileContent(data.getFile().getFileContent());
		file.setFileExtension(data.getFile().getFileExtension());
		
		ConnHandler.begin();
		fileInsert = fileDao.insert(file);
		ConnHandler.commit();
		
		course.setCourseCode(Generate.generateCode(5));
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
	
	public List<PojoCourseGetAllRes> getAllRes() {
		final List<PojoCourseGetAllRes> pojos = new ArrayList<>();
		final List<Course> res = getAll();

		for (int i = 0; i < res.size(); i++) {
			final PojoCourseGetAllRes pojo = new PojoCourseGetAllRes();
			final Course course = res.get(i);

			ConnHandler.getManager().detach(course);
			pojo.setFile(course.getFile().getFileName());
			pojo.setId(course.getId());
			pojo.setCourseName(course.getCourseName());
			pojo.setTrainer(course.getTrainer());
			pojo.setProvider(course.getProvider());
			pojo.setLocationName(course.getLocationName());
			pojo.setCategoryId(course.getCategory().getCategoryName());
			pojo.setStartDate(course.getStartDate());
			pojo.setEndDate(course.getEndDate());
			pojo.setPrice(course.getPrice());
			pojo.setVer(course.getVersion());
			
			pojos.add(pojo);
		}
		return pojos;
	}
	
	public List<PojoCourseResGetByCategoryId> getByCategoryId(final String id) {
		final List<PojoCourseResGetByCategoryId> pojos = new ArrayList<>();
		final List<Course> res = courseDao.getByCategoryId(id);

		for (int i = 0; i < res.size(); i++) {
			final PojoCourseResGetByCategoryId pojo = new PojoCourseResGetByCategoryId();
			final Course course = res.get(i);

			ConnHandler.getManager().detach(course);
			pojo.setFileId(course.getFile().getFileName());
			pojo.setId(course.getId());
			pojo.setCourseCode(course.getCourseCode());
			pojo.setCourseName(course.getCourseName());
			pojo.setTrainer(course.getTrainer());
			pojo.setProvider(course.getProvider());
			pojo.setLocationName(course.getLocationName());
			pojo.setCategoryId(course.getCategory().getCategoryName());
			pojo.setStartDate(course.getStartDate());
			pojo.setEndDate(course.getEndDate());
			pojo.setPrice(course.getPrice());
			pojo.setVer(course.getVersion());

			pojos.add(pojo);
		}

		return pojos;
	}
	
	public PojoUpdateRes update(final PojoCourseUpdateReq data) {
		Course courseUpdate = null;
	
		courseUpdate = getByIdAndDetach(data.getId());
		
		final Course course = courseUpdate;

		course.setCourseName(data.getCourseName());
		course.setProvider(data.getProvider());
		course.setLocationName(data.getLocationName());
		course.setTrainer(data.getTrainer());
		course.setStartDate(data.getStartDate());
		course.setEndDate(data.getEndDate());
		course.setPrice(data.getPrice());
		course.setVersion(data.getVer());
		
		final Category category = categoryDao.getByIdRef(Category.class ,data.getCategoryId());
		category.setId(data.getCategoryId());
		course.setCategory(category);
		
		final File fileInsert = new File();
		fileInsert.setFileName(data.getFile().getFileName());
		fileInsert.setFileExtension(data.getFile().getFileExtension());
		fileInsert.setFileContent(data.getFile().getFileContent());
		
		ConnHandler.begin();
		final File file = fileDao.insert(fileInsert);
		ConnHandler.commit();
		course.setFile(file);
	
		courseUpdate = update(course);

		final PojoUpdateRes pojoUpdate = new PojoUpdateRes();
		pojoUpdate.setVer(data.getVer());
		pojoUpdate.setMessage("Updated");
		return pojoUpdate;

	}
	
	public PojoDeleteRes delete(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		deleteById(id);
		res.setMessage("Course Berhasil Dihapus");
		return res;
	}
	
/** User Course Service */
	
	public PojoInsertRes inserCourseEvent(final PojoUserCourseInsertReq data) {
		final UserCourse userCourse = new UserCourse();
		
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		user.setId(principalService.getAuthPrincipal());
		userCourse.setUser(user);

		final Course course = courseDao.getByIdRef(Course.class, data.getCourseId());
		course.setId(data.getCourseId());
		userCourse.setCourse(course);

		UserCourse userCourseInsert = null;
		ConnHandler.begin();
		userCourseInsert = userCourseDao.insert(userCourse);
		ConnHandler.commit();

		final PojoInsertRes pojoInsertRes = new PojoInsertRes();
		pojoInsertRes.setId(userCourseInsert.getId());
		pojoInsertRes.setMessage("Anda Berhasil Membeli Course");
		return pojoInsertRes;
	}
	
	public List<PojoUserCourseGetByUserIdRes> getByUserId(final String id) {
		final List<PojoUserCourseGetByUserIdRes> pojos = new ArrayList<>();
		final List<UserCourse> res = userCourseDao.getByUserId(principalService.getAuthPrincipal());

		for (int i = 0; i < res.size(); i++) {
			final PojoUserCourseGetByUserIdRes pojo = new PojoUserCourseGetByUserIdRes();
			final UserCourse userCourse = res.get(i);

			ConnHandler.getManager().detach(userCourse);
			
			pojo.setId(userCourse.getId());
			pojo.setUserId(userCourse.getUser().getProfile().getFullName());
			pojo.setCourseId(userCourse.getCourse().getCourseName());

			pojos.add(pojo);
		}

		return pojos;
	}
	
	public PojoDeleteRes deleteUserEvent(final String id) {
		final PojoDeleteRes res = new PojoDeleteRes();
		ConnHandler.begin();
		userCourseDao.delete(id);
		ConnHandler.commit();
		res.setMessage("Berhasil Dihapus");
		return res;
	}
	
	
	public PojoCourseGetAllRes getCourseById(final String id) {
		final Optional<Course> course = getById(id);
		final PojoCourseGetAllRes pojoCourseResGetAll = new PojoCourseGetAllRes();
		pojoCourseResGetAll.setId(course.get().getId());
		pojoCourseResGetAll.setCourseName(course.get().getCourseName());
		pojoCourseResGetAll.setProvider(course.get().getProvider());
		pojoCourseResGetAll.setLocationName(course.get().getLocationName());
		pojoCourseResGetAll.setStartDate(course.get().getStartDate());
		pojoCourseResGetAll.setEndDate(course.get().getEndDate());
		pojoCourseResGetAll.setPrice(course.get().getPrice());
		pojoCourseResGetAll.setCategoryId(course.get().getCategory().getId());
		pojoCourseResGetAll.setFile(course.get().getFile().getId());
		pojoCourseResGetAll.setVer(course.get().getVersion());
		return pojoCourseResGetAll;
	}
	
	
	public PojoCourserGetAllResData getCoursePage(final Integer limit, final Integer offset) {
		final List<PojoCourseGetAllRes> pojos = new ArrayList<>();
		final List<Course> res = courseDao.getCoursePage(limit, offset);

		for (int i = 0; i < res.size(); i++) {
			final PojoCourseGetAllRes pojo = new PojoCourseGetAllRes();
			final Course course = res.get(i);

			ConnHandler.getManager().detach(course);
			pojo.setFile(course.getFile().getFileName());
			pojo.setId(course.getId());
			pojo.setCourseName(course.getCourseName());
			pojo.setTrainer(course.getTrainer());
			pojo.setProvider(course.getProvider());
			pojo.setLocationName(course.getLocationName());
			pojo.setCategoryId(course.getCategory().getCategoryName());
			pojo.setStartDate(course.getStartDate());
			pojo.setEndDate(course.getEndDate());
			pojo.setPrice(course.getPrice());
			pojo.setVer(course.getVersion());
			
			pojos.add(pojo);
		} 
		
		final PojoCourserGetAllResData pojoCourseData = new PojoCourserGetAllResData();
		pojoCourseData.setData(pojos);
		pojoCourseData.setTotal(courseDao.getTotalCourse());
		
		return pojoCourseData;
	}
}
