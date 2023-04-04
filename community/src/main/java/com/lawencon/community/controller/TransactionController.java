package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.PojoDeleteRes;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.PojoUpdateRes;
import com.lawencon.community.pojo.transaction.PojoInsertTransactionReq;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllResData;
import com.lawencon.community.pojo.transaction.PojoUpdateTransactionReq;
import com.lawencon.community.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	private final TransactionService transactionService;
	
	public TransactionController(final TransactionService transactionService){
		this.transactionService = transactionService;
	}

	
	@PostMapping("/add")
	private ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoInsertTransactionReq data){
		final PojoInsertRes res = transactionService.insertRes(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@GetMapping
	private ResponseEntity<List<PojoTransactionGetAllRes>> getAllTransaction(@RequestParam(required = false, value = "type") String type){
		final List<PojoTransactionGetAllRes> res = transactionService.getAllTransaction(type);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	private ResponseEntity<PojoTransactionGetAllRes> getTransactionById(@PathVariable("id") String id){
		final PojoTransactionGetAllRes res = transactionService.getTransactionById(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("page")
	private ResponseEntity<PojoTransactionGetAllResData> getAllTransaction(@RequestParam("size") int size, @RequestParam("page") int page, @RequestParam(required = false, value = "type") String type){
		int offset = (page - 1)*size;
		final PojoTransactionGetAllResData res = transactionService.getTransactionPage(size, offset, type);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	

	@PatchMapping
	private ResponseEntity<PojoUpdateRes> updateRes(@RequestBody final PojoUpdateTransactionReq data){
		final PojoUpdateRes res = transactionService.updateRes(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	private ResponseEntity<PojoDeleteRes> delete(@PathVariable("id") final String id){
		final PojoDeleteRes res = transactionService.deleteRes(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
}