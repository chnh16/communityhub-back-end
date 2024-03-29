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
	private ResponseEntity<List<PojoEventResGetAll>> getAllEvent(@RequestParam("category") String category, @RequestParam("price") String price, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset){
		final List<PojoEventResGetAll> res = eventService.getAllEvent(category, price, limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("page")
	public ResponseEntity<PojoEventGetAllResData> getEventPage(@RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
		final PojoEventGetAllResData res = eventService.getEventPage(limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity <PojoEventResGetAll> getByEventId(@PathVariable("id") final String id) {
		final PojoEventResGetAll res = eventService.getByEventId(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PutMapping
	private ResponseEntity<PojoUpdateRes> update(@RequestBody final PojoEventReqUpdate data){
		final PojoUpdateRes res = eventService.update(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
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
	public ResponseEntity<List<PojoUserEventGetByUserIdRes>> getByUserId(@RequestParam("category") String category, @RequestParam("price") String price, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset) {
		final List<PojoUserEventGetByUserIdRes> res = eventService.getByUserId(category, price, limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("/user-event/{id}")
	public ResponseEntity<PojoDeleteRes> deleteUserEvent(@PathVariable final String id){
		final PojoDeleteRes res = eventService.deleteUserEvent(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("my-event")
	private ResponseEntity<List<PojoEventResGetAll>> getMyEvent(@RequestParam("category") String category, @RequestParam("price") String price, @RequestParam("limit") Integer limit, @RequestParam("offset") Integer offset){
		final List<PojoEventResGetAll> res = eventService.getMyEvent(category, price, limit, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	

}
