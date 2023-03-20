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
import com.lawencon.community.pojo.industry.PojoIndustryGetAllRes;
import com.lawencon.community.pojo.industry.PojoIndustryGetAllResData;
import com.lawencon.community.pojo.industry.PojoIndustryInsertReq;
import com.lawencon.community.pojo.industry.PojoIndustryUpdateReq;
import com.lawencon.community.service.IndustryService;

@RestController
@RequestMapping("industry")
public class IndustryController {

	private final IndustryService industryService;
	
	public IndustryController(final IndustryService industryService) {
		this.industryService = industryService;
	}
	
	@PostMapping("add")
	public ResponseEntity<PojoInsertRes> insertIndustry(@RequestBody PojoIndustryInsertReq data){
		final PojoInsertRes res = industryService.insertIndustry(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<PojoIndustryGetAllRes>> getAllIndustry(){
		final List<PojoIndustryGetAllRes> res = industryService.getAllIndustry();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<PojoIndustryGetAllRes> getIndustryById(@PathVariable("id") String id) {
		final PojoIndustryGetAllRes res = industryService.getIndustryById(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<PojoIndustryGetAllResData> getIndustry(@RequestParam("size") int size, @RequestParam("page") int page) {
		int offset = (page - 1)*size;
		final PojoIndustryGetAllResData res = industryService.getIndustry(size, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping("edit")
	public ResponseEntity<PojoUpdateRes> updateIndustry(@RequestBody PojoIndustryUpdateReq data){
		final PojoUpdateRes res = industryService.updateIndustry(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<PojoDeleteRes> deleteIndustry(@PathVariable("id") String id){
		final PojoDeleteRes res = industryService.deleteRes(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
