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
import com.lawencon.community.pojo.membership.PojoMembershipGetAllRes;
import com.lawencon.community.pojo.membership.PojoMembershipInsertReq;
import com.lawencon.community.pojo.membership.PojoMembershipUpdateReq;
import com.lawencon.community.service.MembershipService;



@RestController
@RequestMapping("memberships")
public class MembershipController {
	
	private final MembershipService membershipService;
	
	public MembershipController(MembershipService membershipService) {
		this.membershipService = membershipService;
	}
	
	@PostMapping("/add")
	private ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoMembershipInsertReq data){
		final PojoInsertRes res = membershipService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PutMapping("/{id}")
	private ResponseEntity<PojoUpdateRes> update(@RequestBody final PojoMembershipUpdateReq data){
		final PojoUpdateRes res = membershipService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping
	private ResponseEntity<List<PojoMembershipGetAllRes>> getAllRes(){
		final List<PojoMembershipGetAllRes> res = membershipService.getAllRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	private ResponseEntity<PojoDeleteRes> delete(@PathVariable final String id){
		final PojoDeleteRes res = membershipService.delete(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	

}
