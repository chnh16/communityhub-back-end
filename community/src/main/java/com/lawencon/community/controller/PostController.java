package com.lawencon.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;
import com.lawencon.community.pojo.post.PojoPostGetAllRes;
import com.lawencon.community.pojo.post.PojoPostInsertReq;
import com.lawencon.community.pojo.post.PojoPostUpdateReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailGetAllRes;
import com.lawencon.community.pojo.postdetail.PojoPostDetailInsertReq;
import com.lawencon.community.pojo.postdetail.PojoPostDetailUpdateReq;
import com.lawencon.community.pojo.postfile.PojoPostFileInsertListReq;
import com.lawencon.community.pojo.posttype.PojoPostTypeGetAllRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllByUser;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkGetAllRes;
import com.lawencon.community.pojo.userbookmark.PojoUserBookmarkInsertReq;
import com.lawencon.community.pojo.userlike.PojoUserLikeGetAllByUser;
import com.lawencon.community.pojo.userlike.PojoUserLikeGetAllRes;
import com.lawencon.community.pojo.userlike.PojoUserLikeInsertReq;
import com.lawencon.community.service.PostService;
import com.lawencon.community.service.PostTypeService;

@RestController
@RequestMapping("post")
public class PostController {
	@Autowired
	private PostService postService;
	
	@Autowired
	private PostTypeService postTypeService;
	
	@PostMapping
	public ResponseEntity<PojoInsertRes> insertPost(@RequestBody PojoPostInsertReq data){
		final PojoInsertRes res = postService.insertPost(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("list-post")
	public ResponseEntity<List<PojoPostGetAllRes>> getAllPost(@RequestParam("limit") int limit, @RequestParam("offset") int offset){
		final List<PojoPostGetAllRes> res = postService.getAllPost(limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PojoPostGetAllRes> getPostById(@PathVariable("id") String id){
		final PojoPostGetAllRes res = postService.getPostById(id);
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
	public ResponseEntity<PojoInsertRes> insertPostFile(@RequestBody PojoPostFileInsertListReq data){
		final PojoInsertRes res = postService.insertPostFile(data);
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
	
	@GetMapping("likes/{postId}")
	public ResponseEntity<List<PojoUserLikeGetAllRes>> getAllUserLike(@PathVariable("postId") String postId){
		final List<PojoUserLikeGetAllRes> res = postService.getLikeByUser(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("like/{postId}")
	public ResponseEntity<PojoDeleteRes> deleteUserLikeRes(@PathVariable("postId") String postId){
		final PojoDeleteRes res = postService.deleteUserLikeRes(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("detail/add")
	public ResponseEntity<PojoInsertRes> insertPostDetail(@RequestBody PojoPostDetailInsertReq data){
		final PojoInsertRes res = postService.insertDetail(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("list-detail")
	public ResponseEntity<List<PojoPostDetailGetAllRes>> getAllDetail(){
		final List<PojoPostDetailGetAllRes> res = postService.getAllPostDetailRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("detail/{postId}")
	public ResponseEntity<List<PojoPostDetailGetAllRes>> getDetailByPostId(@PathVariable("postId") final String postId){
		final List<PojoPostDetailGetAllRes> res = postService.getDetailByPostId(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("update-detail")
	public ResponseEntity<PojoUpdateRes> updatePostDetail(@RequestBody PojoPostDetailUpdateReq data){
		final PojoUpdateRes res = postService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("delete-detail/{id}")
	public ResponseEntity<PojoDeleteRes> deletePostDetail(@PathVariable("id") String id){
		final PojoDeleteRes res = postService.deletePostDetail(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("bookmark")
	public ResponseEntity<PojoInsertRes> insertBookmark(@RequestBody PojoUserBookmarkInsertReq data){
		final PojoInsertRes res = postService.insertUserBookmark(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("bookmark/{id}")
	public ResponseEntity<PojoDeleteRes> deleteBookmark(@PathVariable("id") String id){
		final PojoDeleteRes res = postService.deleteBookmark(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("list-bookmark")
	public ResponseEntity<List<PojoUserBookmarkGetAllRes>> getAllBookmark(){
		final List<PojoUserBookmarkGetAllRes> res = postService.getAllByUserRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("total-bookmark/{postId}")
	public ResponseEntity<PojoUserBookmarkGetAllRes> getAllCountedBookmark(@PathVariable("postId") String postId){
		final PojoUserBookmarkGetAllRes res = postService.getCountBookmark(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("type")
	public ResponseEntity<List<PojoPostTypeGetAllRes>> getAllPostType(){
		final List<PojoPostTypeGetAllRes> res = postTypeService.getAllRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("polling/{id}")
	public ResponseEntity<PojoInsertRes> insertPollingAnswer(@PathVariable("id") final String pollingChoiceId){
		final PojoInsertRes res = postService.insertPollingAnswer(pollingChoiceId);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@DeleteMapping("polling/{id}")
	public ResponseEntity<PojoDeleteRes> deletePollingAnswer(@PathVariable("id") final String pollingAnswerId){
		final PojoDeleteRes res = postService.deletePollingAnswer(pollingAnswerId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("polling/{id}")
	public ResponseEntity<List<PojoPollingAnswerGetCountRes>> getTotalAnswerByChoiceId(@PathVariable("postId") String postId) {
		final List<PojoPollingAnswerGetCountRes> res = postService.getAnswerByChoiceId(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("post-bookmark")
	public ResponseEntity<List<PojoUserBookmarkGetAllByUser>> getAllByUserBookmark(@RequestParam("limit") int limit, @RequestParam("offset") int offset){
		final List<PojoUserBookmarkGetAllByUser> res = postService.getAllByUserBookmark(limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("post-like")
	public ResponseEntity<List<PojoUserLikeGetAllByUser>> getAllByUserLike(@RequestParam("limit") int limit, @RequestParam("offset") int offset){
		final List<PojoUserLikeGetAllByUser> res = postService.getAllByUserLike(limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("post-detail")
	public ResponseEntity<List<PojoPostDetailGetAllRes>> getAllDetailByPostId(@PathVariable("postId") String postId,@RequestParam("limit") int limit, @RequestParam("offset") int offset){
		final List<PojoPostDetailGetAllRes> res = postService.getAllDetailByPostId(postId, limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
