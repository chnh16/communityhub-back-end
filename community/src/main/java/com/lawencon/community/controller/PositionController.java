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
import com.lawencon.community.pojo.position.PojoPositionGetAllRes;
import com.lawencon.community.pojo.position.PojoPositionInsertReq;
import com.lawencon.community.pojo.position.PojoPositionUpdateReq;
import com.lawencon.community.service.PositionService;

@RestController
@RequestMapping("positions")
public class PositionController {
	
private final PositionService positionService;
	
	public PositionController(PositionService positionService) {
		this.positionService = positionService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoPositionInsertReq data){
		final PojoInsertRes res = positionService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<PojoUpdateRes> update(@RequestBody final PojoPositionUpdateReq data){
		final PojoUpdateRes res = positionService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<PojoPositionGetAllRes>> getAllRes(){
		final List<PojoPositionGetAllRes> res = positionService.getAllRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable final String id){
		final PojoDeleteRes res = positionService.delete(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
