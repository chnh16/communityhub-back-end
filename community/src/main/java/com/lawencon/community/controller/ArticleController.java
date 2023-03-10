package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.article.PojoArticleInsertReq;
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

}
