package com.lawencon.community.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.event.PojoEventReqInsert;
import com.lawencon.community.service.EventService;

@RestController
@RequestMapping("events")
public class EventController {
	
private final EventService eventService;
	
	public EventController(EventService eventService) {
		this.eventService = eventService;
	}
	
	@PostMapping("/add")
	private ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoEventReqInsert data){
		final PojoInsertRes res = eventService.insert(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

}
