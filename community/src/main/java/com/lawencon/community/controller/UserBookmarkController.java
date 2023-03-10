//package com.lawencon.community.controller;
//
//import java.util.List;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllRes;
//import com.lawencon.community.service.UserBookmarkService;
//
//@RestController
//@RequestMapping("bookmark")
//public class UserBookmarkController {
//	private final UserBookmarkService userBookmarkService;
//	
//	public UserBookmarkController(UserBookmarkService userBookmarkService) {
//		this.userBookmarkService = userBookmarkService;
//	}
//	
//	@GetMapping()
//	private ResponseEntity<List<PojoUserBookmarkGetAllRes>> getAllByUser(){
//		final List<PojoUserBookmarkGetAllRes> res = userBookmarkService.getAllByUserRes();
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
//
//}
