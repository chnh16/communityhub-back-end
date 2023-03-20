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
import com.lawencon.community.pojo.event.PojoEventGetAllResData;
import com.lawencon.community.pojo.event.PojoEventReqInsert;
import com.lawencon.community.pojo.event.PojoEventReqUpdate;
import com.lawencon.community.pojo.event.PojoEventResGetAll;
import com.lawencon.community.pojo.event.PojoEventResGetByCategoryId;
import com.lawencon.community.pojo.userevent.PojoUserEventGetByUserIdRes;
import com.lawencon.community.pojo.userevent.PojoUserEventInsertReq;
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
	
	@GetMapping
	private ResponseEntity<List<PojoEventResGetAll>> getAllRes(){
		final List<PojoEventResGetAll> res = eventService.getAllRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<PojoEventGetAllResData> getByUserId(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
		int offset = (page - 1)* size;
		final PojoEventGetAllResData res = eventService.getEventPage(size, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<List<PojoEventResGetByCategoryId>> getByCategoryId(@PathVariable("id") final String id) {
		final List<PojoEventResGetByCategoryId> res = eventService.getByCategoryId(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping
	private ResponseEntity<PojoUpdateRes> update(@RequestBody final PojoEventReqUpdate data){
		final PojoUpdateRes res = eventService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<PojoDeleteRes> delete(@PathVariable final String id){
		final PojoDeleteRes res = eventService.delete(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("/buy-event")
	private ResponseEntity<PojoInsertRes> insertUserEvent(@RequestBody final PojoUserEventInsertReq data){
		final PojoInsertRes res = eventService.insertUserEvent(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@GetMapping("user-event")
	public ResponseEntity<List<PojoUserEventGetByUserIdRes>> getByUserId(final String id) {
		final List<PojoUserEventGetByUserIdRes> res = eventService.getByUserId(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/user-event/{id}")
	public ResponseEntity<PojoDeleteRes> deleteUserEvent(@PathVariable final String id){
		final PojoDeleteRes res = eventService.deleteUserEvent(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	

}
