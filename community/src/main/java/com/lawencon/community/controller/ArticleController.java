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
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
import com.lawencon.community.pojo.article.PojoArticleResGetAll;
import com.lawencon.community.pojo.article.PojoArticleUpdateReq;
import com.lawencon.community.service.ArticleService;

@RestController
@RequestMapping("article")
public class ArticleController {
	
private final ArticleService articleService;
	
	public ArticleController(ArticleService articleService) {
		this.articleService = articleService;
	}
	
	@PostMapping("/add")
	private ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoArticleInsertReq data){
		final PojoInsertRes res = articleService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@GetMapping
	private ResponseEntity<List<PojoArticleResGetAll>> getAllRes(){
		final List<PojoArticleResGetAll> res = articleService.getAllRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping
	private ResponseEntity<PojoUpdateRes> update(@RequestBody final PojoArticleUpdateReq data){
		final PojoUpdateRes res = articleService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable final String id){
		final PojoDeleteRes res = articleService.delete(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
