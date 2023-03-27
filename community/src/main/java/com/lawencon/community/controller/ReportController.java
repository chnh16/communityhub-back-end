package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.transaction.PojoTransactionGetReportParticipantSuperAdminRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportRes;
import com.lawencon.community.service.TransactionService;
import com.lawencon.util.JasperUtil;

@RestController
@RequestMapping("report")
public class ReportController {
	
	private final TransactionService transactionService;
	private final JasperUtil jasperUtil;
	
	public ReportController(final TransactionService transactionService, final JasperUtil jasperUtil){
		this.transactionService = transactionService;
		this.jasperUtil = jasperUtil;
	}
	
	@GetMapping("/participant-member")
	private ResponseEntity<?> getCourseReport(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportRes> res = transactionService.getReportByDate(startDate, endDate);
		final String fileName = "report-member-participant";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report_member_participant");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/participant-super-admin")
	private ResponseEntity<?> getReportParticipantSuperAdmin(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportParticipantSuperAdminRes> res = transactionService.getReportParticipantSuperAdminByDate(startDate, endDate);
		final String fileName = "report-participant-admin";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report-participant-admin");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
//	@GetMapping("/participant-super-admin")
//	private ResponseEntity<?> getReportParticipantSuperAdmin(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
//		final List<PojoTransactionGetReportParticipantSuperAdminRes> res = transactionService.getReportParticipantSuperAdminByDate(startDate, endDate);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
	
	
}
