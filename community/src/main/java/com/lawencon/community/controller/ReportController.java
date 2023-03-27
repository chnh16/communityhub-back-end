package com.lawencon.community.controller;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/income-member")
	private ResponseEntity<?> getReportIncomeMember(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportIncomeMemberRes> res = transactionService.getReportIncomeMemberByDate(startDate, endDate);
		final String fileName = "report-income-member";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report-income-member");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/income-super-admin")
	private ResponseEntity<?> getReportIncomeSuperAdmin(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportIncomeSuperAdminRes> res = transactionService.getReportIncomeSuperAdminByDate(startDate, endDate);
		final String fileName = "report-income-super-admin";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report-income-super-admin");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/event-participant-member")
	private ResponseEntity<?> getEventReport(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportRes> res = transactionService.getEventReportByDate(startDate, endDate);
		final String fileName = "report-member-participant";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report-event-member-participant");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/event-participant-super-admin")
	private ResponseEntity<?> getEventReportParticipantSuperAdmin(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportParticipantSuperAdminRes> res = transactionService.getEventReportParticipantSuperAdminByDate(startDate, endDate);
		final String fileName = "report-participant-admin";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report-event-participant-admin");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/event-income-member")
	private ResponseEntity<?> getEventReportIncomeMember(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportIncomeMemberRes> res = transactionService.getEventReportIncomeMemberByDate(startDate, endDate);
		final String fileName = "report-income-member";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report-event-income-member");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	@GetMapping("/event-income-super-admin")
	private ResponseEntity<?> getEventReportIncomeSuperAdmin(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
		final List<PojoTransactionGetReportIncomeSuperAdminRes> res = transactionService.getEventReportIncomeSuperAdminByDate(startDate, endDate);
		final String fileName = "report-income-super-admin";
		final byte[] fileBytes = jasperUtil.responseToByteArray(res, null, "report-event-income-super-admin");
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=" + fileName + ".pdf").body(fileBytes);
	}
	
	
//	@GetMapping("/event-income-member")
//	private ResponseEntity<?> getEventReportIncomeMember(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception{
//		final List<PojoTransactionGetReportIncomeSuperAdminRes> res = transactionService.getEventReportIncomeSuperAdminByDate(startDate, endDate);
//		return new ResponseEntity<>(res, HttpStatus.OK);
//	}
	
	
}
