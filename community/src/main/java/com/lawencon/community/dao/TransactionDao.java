package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Transaction;

@Repository
public class TransactionDao extends BasePostDao<Transaction>{

	@Override
	public Transaction insert(final Transaction data) {
		final Transaction res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Transaction.class, id);
	}
	
	public Transaction update(final Transaction data) {
		final Transaction res = saveAndFlush(data);
		return res;
	}
	
	public Transaction getRefById(final String id) {
		final Transaction res = getByIdRef(Transaction.class, id);
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByCourseId (final String courseId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t ")
		.append("WHERE t.course_id = :courseId");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class).setParameter("courseId", courseId).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByEventId (final String eventId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t ")
		.append("WHERE t.event_id = :eventId");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class).setParameter("eventId", eventId).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByMembershipId (final String membershipId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t ")
		.append("WHERE t.membership_id = :membershipId");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class).setParameter("membershipId", membershipId).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getTransaction(final String type){
		List<Transaction> res = new ArrayList<>();
		final StringBuilder str = new StringBuilder();
		if(type.equals(null)) {
			str.append("SELECT * FROM t_transaction t ").append("AND t.is_active = TRUE");
		}
		if(type.equals("event")) {
			str.append("SELECT * FROM t_transaction t ").append("WHERE t.event_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		if(type.equals("course")) {
			str.append("SELECT * FROM t_transaction t ").append("WHERE t.course_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		if(type.equals("membership")) {
			str.append("SELECT * FROM t_transaction t ").append("WHERE t.membership_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		res = em().createNativeQuery(toStr(str), Transaction.class).getResultList();
		return res;
	}
	
	

}
