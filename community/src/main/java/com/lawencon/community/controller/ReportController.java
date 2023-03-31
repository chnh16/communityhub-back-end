package com.lawencon.community.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.pojo.transaction.PojoTransactionGetReportIncomeMemberRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportIncomeSuperAdminRes;
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
	
	@GetMapping("/participant-member/{userId}")
	private ResponseEntity<?> getCourseReport(@PathVariable("userId") String userId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportRes> res = transactionService.getMemberReportParticipantByDate(userId, startDate, endDate);
		final String fileName = "report-member-participant";
		Map<String, Object> map = new HashMap<>();
		map.put("Parameter1", startDate);
		map.put("Parameter2", endDate);
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, map, "report_member_participant");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/participant-super-admin")
	private ResponseEntity<?> getReportParticipantSuperAdmin(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportParticipantSuperAdminRes> res = transactionService.getReportParticipantSuperAdminByDate(startDate, endDate);
		final String fileName = "report-participant-admin";
		Map<String, Object> map = new HashMap<>();
		map.put("Parameter1", startDate);
		map.put("Parameter2", endDate);
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, map, "report-participant-admin");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/income-member/{userId}")
	private ResponseEntity<?> getReportIncomeMember(@PathVariable("userId") String userId,@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportIncomeMemberRes> res = transactionService.getReportIncomeMemberByDate(userId, startDate, endDate);
		final String fileName = "report-income-member";
		Map<String, Object> map = new HashMap<>();
		map.put("Parameter1", startDate);
		map.put("Parameter2", endDate);
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, map, "report-income-member");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/income-super-admin")
	private ResponseEntity<?> getReportIncomeSuperAdmin(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportIncomeSuperAdminRes> res = transactionService.getReportIncomeSuperAdminByDate(startDate, endDate);
		final String fileName = "report-income-super-admin";
		Map<String, Object> map = new HashMap<>();
		map.put("Parameter1", startDate);
		map.put("Parameter2", endDate);
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, map, "report-income-super-admin");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
}
