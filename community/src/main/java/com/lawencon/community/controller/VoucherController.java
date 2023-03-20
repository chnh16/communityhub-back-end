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
import com.lawencon.community.pojo.voucher.PojoVoucherGetAllRes;
import com.lawencon.community.pojo.voucher.PojoVoucherGetAllResData;
import com.lawencon.community.pojo.voucher.PojoVoucherInsertReq;
import com.lawencon.community.pojo.voucher.PojoVoucherUpdateReq;
import com.lawencon.community.service.VoucherService;

@RestController
@RequestMapping("voucher")
public class VoucherController {
	private final VoucherService voucherService;
	
	public VoucherController(final VoucherService voucherService) {
		this.voucherService = voucherService;
	}
	
	@GetMapping()
	private ResponseEntity<List<PojoVoucherGetAllRes>> getAll(){
		final List<PojoVoucherGetAllRes> res = voucherService.getAllRes();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	private ResponseEntity<PojoVoucherGetAllRes> getVoucherById(@PathVariable("id") String id){
		final PojoVoucherGetAllRes res = voucherService.getVoucherById(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@GetMapping("/page")
	public ResponseEntity<PojoVoucherGetAllResData> getVoucher(@RequestParam("size") int size, @RequestParam("page") int page) {
		int offset = (page - 1)*size;
		final PojoVoucherGetAllResData res = voucherService.getVoucherGetAllResData(size, offset);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@PostMapping("add")
	private ResponseEntity<PojoInsertRes> insert(@RequestBody final PojoVoucherInsertReq data){
		final PojoInsertRes res = voucherService.insertRes(data);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}
	
	@PutMapping("edit")
	private ResponseEntity<PojoUpdateRes> update(@RequestBody final PojoVoucherUpdateReq data){
		final PojoUpdateRes res = voucherService.updateRes(data);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	private ResponseEntity<PojoDeleteRes> delete(@PathVariable final String id){
		final PojoDeleteRes res = voucherService.deleteRes(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
}
