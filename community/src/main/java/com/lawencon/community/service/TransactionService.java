package com.lawencon.community.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.dao.TransactionDao;
import com.lawencon.community.model.Transaction;

@Service
public class TransactionService {
	private final TransactionDao transactionDao;
	
	public TransactionService(final TransactionDao transactionDao) {
		this.transactionDao = transactionDao;
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
	
	
}
