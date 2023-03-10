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
import com.lawencon.community.pojo.post.PojoPostGetAllRes;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.postfile.PojoPostFileGetAllRes;
import com.lawencon.community.pojo.postfile.PojoPostFileInsertReq;
import com.lawencon.community.pojo.userlike.PojoUserLikeInsertReq;
import com.lawencon.community.service.PostService;

@RestController
@RequestMapping("post")
public class PostController {
	
	private final PostService postService;
	

	public PostController(final PostService postService) {
		this.postService = postService;
	}
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPost(@RequestBody PojoPostInsertReq data){
		final PojoInsertRes res = postService.insertPost(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("list-post")
	public ResponseEntity<List<PojoPostGetAllRes>> getAllIndustry(){
		final List<PojoPostGetAllRes> res = postService.getAllPost();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> updateIndustry(@RequestBody PojoPostUpdateReq data){
		final PojoUpdateRes res = postService.updatePost(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> deleteIndustry(@PathVariable("id") String id){
		final PojoDeleteRes res = postService.deleteRes(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("post-file")
	public ResponseEntity<PojoInsertRes> insertPostFile(@RequestBody PojoPostFileInsertReq data){
		final PojoInsertRes res = postService.insertPostFile(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("list-post-file/{postId}")
	public ResponseEntity<List<PojoPostFileGetAllRes>> getAllPostFile(@PathVariable("postId") String postId){
		final List<PojoPostFileGetAllRes> res = postService.getAllPostFile(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("post-file/{id}")
	public ResponseEntity<PojoDeleteRes> deletePostFile(@PathVariable("id") String id){
		final PojoDeleteRes res = postService.deletePostFileRes(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("like")
	public ResponseEntity<PojoInsertRes> insertUserLike(@RequestBody PojoUserLikeInsertReq data){
		final PojoInsertRes res = postService.insertUserLike(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("like/{id}")
	public ResponseEntity<PojoDeleteRes> deleteUserLike(@PathVariable("id") String id){
		final PojoDeleteRes res = postService.deleteUserLikeRes(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
