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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.category.PojoCategoryGetAllRes;
import com.lawencon.community.pojo.category.PojoCategoryGetAllResData;
import com.lawencon.community.pojo.category.PojoCategoryInsertReq;
import com.lawencon.community.pojo.category.PojoCategoryUpdateReq;
import com.lawencon.community.service.CategoryService;

@RestController
@RequestMapping("category")
public class CategoryController {
	
	private final CategoryService categoryService;

	public CategoryController(final CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("add")
	public ResponseEntity<PojoInsertRes> insertCategory(@RequestBody PojoCategoryInsertReq data) {
		final PojoInsertRes res = categoryService.insertCategory(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<PojoCategoryGetAllResData>> getAllCategory() {
		final List<PojoCategoryGetAllResData> res = categoryService.getAllCategory();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PojoCategoryGetAllResData> getCategoryById(@PathVariable("id") String id) {
		final PojoCategoryGetAllResData res = categoryService.getCategoryById(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<PojoCategoryGetAllRes> getCategory(@RequestParam("limit") int limit, @RequestParam("offset") int offset) {
		final PojoCategoryGetAllRes res = categoryService.getCategory(limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("edit")
	public ResponseEntity<PojoUpdateRes> updateCategory(@RequestBody PojoCategoryUpdateReq data) {
		final PojoUpdateRes res = categoryService.updateIndustry(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> deleteCategory(@PathVariable("id") String id) {
		final PojoDeleteRes res = categoryService.deleteRes(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
