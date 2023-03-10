package com.lawencon.community.service;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.TransactionType;
import com.lawencon.community.dao.TransactionDao;
import com.lawencon.community.model.Course;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.Membership;
import com.lawencon.community.model.Transaction;
import com.lawencon.community.model.User;
import com.lawencon.community.model.Voucher;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.transaction.PojoInsertTransactionReq;
import com.lawencon.community.pojo.transaction.PojoTransactionGetAllRes;
import com.lawencon.security.principal.PrincipalService;

@Service
public class TransactionService {
	private final TransactionDao transactionDao;
	private final UserService userService;
	private final EventService eventService;
	private final MembershipService membershipService;
	private final CourseService courseService;
	private final PrincipalService principalService;
	private final VoucherService voucherService;
	
	public TransactionService(final TransactionDao transactionDao, final UserService userService, final EventService eventService, final MembershipService membershipService, final CourseService courseService, final PrincipalService principalService, final VoucherService voucherService) {
		this.transactionDao = transactionDao;
		this.userService = userService;
		this.eventService = eventService;
		this.membershipService = membershipService;
		this.courseService = courseService;
		this.principalService = principalService;
		this.voucherService = voucherService;
	}
	
	private Transaction insert(final Transaction data) {
		Transaction transInsert = null;
		try {
			final Transaction trans = new Transaction();
			ConnHandler.begin();
			trans.setTransactionDate(data.getTransactionDate());
			trans.setGrandTotal(data.getGrandTotal());
			trans.setFile(data.getFile());
			trans.setCreatedAt(LocalDateTime.now());
			trans.setIsApproved(false);
			trans.setUser(data.getUser());
			if(data.getEvent() != null) {
				trans.setEvent(data.getEvent());
			}
			if(data.getCourse() != null) {
				trans.setCourse(data.getCourse());
			}
			if(data.getMembership() != null) {
				trans.setMembership(data.getMembership());
			}
			if(data.getVoucher() != null) {
				trans.setVoucher(data.getVoucher());
			}
			transInsert = transactionDao.insert(trans);
		} catch(final Exception e) {
			e.printStackTrace();
		}
		return transInsert;
	}
	
	private Transaction update(final Transaction data) {
		Transaction transUpdate = null;
		try {
			final Transaction trans = new Transaction();
			ConnHandler.begin();
			trans.setTransactionDate(data.getTransactionDate());
			trans.setGrandTotal(data.getGrandTotal());
			trans.setFile(data.getFile());
			trans.setCreatedAt(LocalDateTime.now());
			trans.setIsApproved(data.getIsActive());
			trans.setUser(data.getUser());
			if(data.getEvent() != null) {
				trans.setEvent(data.getEvent());
			}
			if(data.getCourse() != null) {
				trans.setCourse(data.getCourse());
			}
			if(data.getMembership() != null) {
				trans.setMembership(data.getMembership());
			}
			if(data.getVoucher() != null) {
				trans.setVoucher(data.getVoucher());
			}
			transUpdate = transactionDao.insert(trans);
		} catch(final Exception e) {
			e.printStackTrace();
		}
		return transUpdate;
	}
	
	private boolean delete(final String id) {
		return transactionDao.delete(id);
	}
	
	private List<Transaction> getAll(final String type){
		return transactionDao.getTransaction(type);
	}
	
	public PojoInsertRes insertRes(final PojoInsertTransactionReq data) {
		BigDecimal grandTotal = null;
		final PojoInsertRes pojo = new PojoInsertRes();
		Transaction transactionInsert = null;
		final User user = userService.getByRefId(principalService.getAuthPrincipal());
		final Transaction trans = new Transaction();
		trans.setUser(user);
		trans.setTransactionDate(LocalDateTime.now());
		if(data.getEventId() != null) {
			final Event event = eventService.getRefById(data.getEventId());
			trans.setEvent(event);
			grandTotal = event.getPrice();
		}
		if(data.getMembershipId() != null) {
			final Membership membership = membershipService.getRefById(data.getMembershipId());
			trans.setMembership(membership);
			grandTotal = membership.getAmount();
		}
		if(data.getCourseId() != null) {
			final Course course = courseService.getRefById(data.getCourseId());
			trans.setCourse(course);
			grandTotal = course.getPrice();
		}
		if(data.getVoucherCode() != null) {
			final Voucher voucher = voucherService.getByVoucherCode(data.getVoucherCode()).get();
			trans.setVoucher(voucher);
			grandTotal = grandTotal.subtract(voucher.getAmount());
		}
		trans.setGrandTotal(grandTotal);
		transactionInsert = insert(transactionInsert);
		pojo.setId(transactionInsert.getId());
		pojo.setMessage("Berhasil");
		return pojo;
	}
	
	public List<PojoTransactionGetAllRes> getAllRes(final String type){
		final List<PojoTransactionGetAllRes> pojos = new ArrayList<>();
		List<Transaction> res = new ArrayList<>();
		if(type.equals(null)) {
			res = getAll(type);
		}
		if(type.equals(TransactionType.EVENT.getTypeName())) {
			res = getAll(TransactionType.EVENT.getTypeName());
		}
		if(type.equals(TransactionType.COURSE.getTypeName())) {
			res = getAll(TransactionType.COURSE.getTypeName());
		}
		if(type.equals(TransactionType.MEMBERSHIP.getTypeName())) {
			res = getAll(TransactionType.MEMBERSHIP.getTypeName());
		}
		for(int i = 0; i < res.size(); i++) {
			final PojoTransactionGetAllRes pojo = new PojoTransactionGetAllRes();
			final Transaction transaction = res.get(i);
			final User user = userService.getByRefId(transaction.getUser().getId());
			if(transaction.getEvent() != null) {
				final Event event = eventService.getRefById(transaction.getEvent().getId());
				pojo.setItemName(event.getEventName());
			}
			if(transaction.getCourse() != null) {
				final Course course = courseService.getRefById(transaction.getCourse().getId());
				pojo.setItemName(course.getCourseName());
			}
			if(transaction.getMembership() != null) {
				final Membership membership = membershipService.getRefById(transaction.getMembership().getId());
				pojo.setItemName(membership.getMembershipName());
			}
			pojo.setGrandTotal(transaction.getGrandTotal());
			pojo.setFileId(transaction.getFile().getId());
			pojo.setFullName(user.getProfile().getFullName());
			pojos.add(pojo);
		}
		
		return pojos;
	}
	
	
}
