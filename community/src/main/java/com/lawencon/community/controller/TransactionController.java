package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.transaction.PojoTransactionGetAllRes;
import com.lawencon.community.service.TransactionService;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	private final TransactionService transactionService;
	
	public TransactionController(final TransactionService transactionService){
		this.transactionService = transactionService;
	}
	
	@GetMapping("/{type}")
	public ResponseEntity<List<PojoTransactionGetAllRes>> getAllRes(@PathVariable("type") final String type){
		final List<PojoTransactionGetAllRes> res = transactionService.getAllRes(type);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}