package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.TransactionDao;
import com.lawencon.community.model.Event;
import com.lawencon.community.model.Membership;
import com.lawencon.community.model.Transaction;
import com.lawencon.community.model.User;
import com.lawencon.community.pojo.PojoInsertRes;
import com.lawencon.community.pojo.transaction.PojoInsertTransactionReq;

@Service
public class TransactionService {
	private final TransactionDao transactionDao;
	private final UserService userService;
	private final EventService eventService;
	private final MembershipService membershipService;
	
	public TransactionService(final TransactionDao transactionDao, final UserService userService, final EventService eventService, final MembershipService membershipService) {
		this.transactionDao = transactionDao;
		this.userService = userService;
		this.eventService = eventService;
		this.membershipService = membershipService;
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
	
	private List<Transaction> getByCoureId(final String id){
		return transactionDao.getByCourseId(id);
	}
	
	private List<Transaction> getByEventId(final String id){
		return transactionDao.getByEventId(id);
	}
	
	private List<Transaction> getByMembershipId(final String id){
		return transactionDao.getByMembershipId(id);
	}
	
	public PojoInsertRes insertRes(final PojoInsertTransactionReq data) {
		final PojoInsertRes pojo = new PojoInsertRes();
		Transaction transactionInsert = null;
		final User user = userService.getByIdAndDetach(data.getUserId());
		final Transaction trans = new Transaction();
		trans.setUser(user);
		if(data.getEventId() != null) {
			final Event event = eventService.getByIdAndDetach(data.getEventId());
			trans.setEvent(event);
		}
		if(data.getMembershipId() != null) {
			final Membership membership = membershipService.getByIdAndDetach(data.getMembershipId());
			trans.setMembership(membership);
		}
		transactionInsert = insert(transactionInsert);
		return null;
	}
}
