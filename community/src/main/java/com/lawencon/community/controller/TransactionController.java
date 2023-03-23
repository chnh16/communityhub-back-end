package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
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
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportRes;
import com.lawencon.community.pojo.transaction.PojoUpdateTransactionReq;
import com.lawencon.community.service.TransactionService;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("transaction")
public class TransactionController {
	private final TransactionService transactionService;
	private final JasperUtil jasperUtil;
	
	public TransactionController(final TransactionService transactionService, final JasperUtil jasperUtil){
		this.transactionService = transactionService;
		this.jasperUtil = jasperUtil;
	}
	
//	@GetMapping("/{type}")
//	public ResponseEntity<List<PojoTransactionGetAllRes>> getAllRes(@PathVariable("type") final String type){
//		final List<PojoTransactionGetAllRes> res = transactionService.getAllRes(type);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
	
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
	
//	@GetMapping("/event/{id}")
//	public ResponseEntity<List<PojoTransactionGetByEventIdRes>> getByEventId(@PathVariable("id") final String id) {
//		final List<PojoTransactionGetByEventIdRes> res = transactionService.getByEventId(id);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
//	
//	@GetMapping("/course/{id}")
//	public ResponseEntity<List<PojoTransactionGetByCourseIdRes>> getByCourseId(@PathVariable("id") final String id) {
//		final List<PojoTransactionGetByCourseIdRes> res = transactionService.getByCourseId(id);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
//	
//	@GetMapping("/membership/{id}")
//	public ResponseEntity<List<PojoTransactionGetByMembershipIdRes>> getByMembershipId(@PathVariable("id") final String id) {
//		final List<PojoTransactionGetByMembershipIdRes> res = transactionService.getByMembershipId(id);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
	
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
	
	@GetMapping("/report")
	private ResponseEntity<?> getCourseReport(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportRes> res = transactionService.getReportByDate(startDate, endDate);
		final String fileName = "report";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report_member_participant");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
}