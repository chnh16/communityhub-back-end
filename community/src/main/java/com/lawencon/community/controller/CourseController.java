package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.course.PojoCourseGetAllRes;
import com.lawencon.community.pojo.course.PojoCourseInsertReq;
import com.lawencon.community.pojo.course.PojoCourseResGetByCategoryId;
import com.lawencon.community.pojo.course.PojoCourseUpdateReq;
import com.lawencon.community.pojo.usercourse.PojoUserCourseGetByUserIdRes;
import com.lawencon.community.pojo.usercourse.PojoUserCourseInsertReq;
import com.lawencon.community.service.CourseService;

@RestController
@RequestMapping("courses")
public class CourseController {
	
private final CourseService courseService;
	
	public CourseController(CourseService courseService) {
		this.courseService = courseService;
	}
	
	@PostMapping("/add")
	private ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoCourseInsertReq data){
		final PojoInsertRes res = courseService.insertRes(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@GetMapping
	private ResponseEntity<List<PojoCourseGetAllRes>> getAllRes(){
		final List<PojoCourseGetAllRes> res = courseService.getAllRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping
	private ResponseEntity<PojoUpdateRes> update(@RequestBody final PojoCourseUpdateReq data){
		final PojoUpdateRes res = courseService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<PojoCourseResGetByCategoryId>> getByCategoryId(@PathVariable("id") final String id) {
		final List<PojoCourseResGetByCategoryId> res = courseService.getByCategoryId(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable final String id){
		final PojoDeleteRes res = courseService.delete(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/buy-course")
	private ResponseEntity<PojoInsertRes> inserCourseEvent(@RequestBody final PojoUserCourseInsertReq data){
		final PojoInsertRes res = courseService.inserCourseEvent(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@GetMapping("user-course")
	public ResponseEntity<List<PojoUserCourseGetByUserIdRes>> getByUserId(final String id) {
		final List<PojoUserCourseGetByUserIdRes> res = courseService.getByUserId(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/user-course/{id}")
	public ResponseEntity<PojoDeleteRes> deleteUserEvent(@PathVariable final String id){
		final PojoDeleteRes res = courseService.deleteUserEvent(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
