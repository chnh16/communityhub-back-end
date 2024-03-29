package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceGetAllRes;
import com.lawencon.community.service.PollingService;

@RestController
@RequestMapping("polling")
public class PollingController {

	private PollingService pollingService;
	
	public PollingController(final PollingService pollingService) {
		this.pollingService = pollingService;
	}
	

	
	@GetMapping("{postId}")
	public ResponseEntity<List<PojoPollingChoiceGetAllRes>> getAll(@PathVariable("postId") String postId) {
		final List<PojoPollingChoiceGetAllRes> res = pollingService.getAllChoice(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("{pollingId}")
	public ResponseEntity<PojoDeleteRes> deletePolling(@PathVariable("pollingId") String pollingId) {
		final PojoDeleteRes res = pollingService.deletePollingRes(pollingId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("count/{postId}")
	public ResponseEntity<List<PojoPollingAnswerGetCountRes>> getTotalAnswerByChoiceId(@PathVariable("postId") String postId) {
		final List<PojoPollingAnswerGetCountRes> res = pollingService.getAnswerByChoiceId(postId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}
