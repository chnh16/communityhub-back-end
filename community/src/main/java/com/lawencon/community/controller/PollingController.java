package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.pollingdetail.PojoPollingDetailInsertReq;
import com.lawencon.community.service.PollingService;

@RestController
@RequestMapping("polling")
public class PollingController {

	private PollingService pollingService;
	
	public PollingController(PollingService pollingService) {
		this.pollingService = pollingService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoPollingDetailInsertReq data) {
		final PojoInsertRes res = pollingService.insertPollingDetail(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
}
