package com.lawencon.community.dao;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.constant.TransactionType;
import com.lawencon.community.model.Transaction;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportParticipantSuperAdminRes;
import com.lawencon.community.pojo.transaction.PojoTransactionGetReportRes;
import com.lawencon.community.util.DateUtil;

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
	
	public Optional<Transaction> getById(final String id){
		final Transaction transaction = em().find(Transaction.class, id);
		return Optional.ofNullable(transaction);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Transaction> getTransactionById(final String id){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t ")
			.append(" WHERE t.id = :id AND t.is_active = true");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class)
				.setParameter("id", id)
				.getResultList();
		return Optional.ofNullable(res.get(0));
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getAll(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t")
			.append(" WHERE t.is_active = TRUE");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByCourse(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t")
			.append(" WHERE t.course_id IS NOT NULL")
			.append(" AND t.is_active = TRUE");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class).getResultList();
		return res;
	}

	@SuppressWarnings("unchecked")
	public List<Transaction> getByCoursePage(final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t")
			.append(" WHERE t.course_id IS NOT NULL")
			.append(" AND t.is_active = TRUE");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByEventPage(final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t")
			.append(" WHERE t.event_id IS NOT NULL")
			.append(" AND t.is_active = TRUE");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByEvent(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t")
			.append(" WHERE t.event_id IS NOT NULL")
			.append(" AND t.is_active = TRUE");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByMembership(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t")
			.append(" WHERE t.membership_id IS NOT NULL")
			.append(" AND t.is_active = TRUE");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getByMembershipPage(final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t")
			.append(" WHERE t.membership_id IS NOT NULL")
			.append(" AND t.is_active = TRUE");
		final List<Transaction> res = em().createNativeQuery(toStr(str), Transaction.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
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
	public List<Transaction> getTransaction(final String type, final String id){
		List<Transaction> res = new ArrayList<>();
		final StringBuilder str = new StringBuilder();
		if(type.equals("") && id.equals("")) {
			str.append("SELECT * FROM t_transaction t ").append("AND t.is_active = TRUE");
		}
		if(type.equals(TransactionType.EVENT.getTypeName())) {
			str.append("SELECT * FROM t_transaction t ").append("WHERE t.event_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		if(type.equals(TransactionType.COURSE.getTypeName())) {
			str.append("SELECT * FROM t_transaction t ").append("WHERE t.course_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		if(type.equals(TransactionType.MEMBERSHIP.getTypeName())) {
			str.append("SELECT * FROM t_transaction t ").append("WHERE t.membership_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		res = em().createNativeQuery(toStr(str), Transaction.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Transaction> getAllTransaction(final Integer limit, final Integer offset) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM t_transaction t ")
			.append(" WHERE t.is_active = true");
		final List<Transaction> res = em().createNativeQuery(toStr(str).toString(), Transaction.class)
				.setMaxResults(limit)
				.setFirstResult(offset)
				.getResultList();
		return res;
	} 
	
	public int getTotalTransaction(final String type) {
		final StringBuilder str = new StringBuilder();
		if(type.equals("")) {
			str.append("SELECT COUNT(t.id) FROM t_transaction t ").append("AND t.is_active = TRUE");
		}
		if(type.equals(TransactionType.EVENT.getTypeName())) {
			str.append("SELECT COUNT(t.id) FROM t_transaction t ").append("WHERE t.event_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		if(type.equals(TransactionType.COURSE.getTypeName())) {
			str.append("SELECT COUNT(t.id) FROM t_transaction t ").append("WHERE t.course_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		if(type.equals(TransactionType.MEMBERSHIP.getTypeName())) {
			str.append("SELECT COUNT(t.id) FROM t_transaction t ").append("WHERE t.membership_id IS NOT NULL ").append("AND t.is_active = TRUE");
		}
		
		final int totalTransaction = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalTransaction;
	}
	
	@SuppressWarnings("unchecked")
	public List<PojoTransactionGetReportRes> getCourseReport(final LocalDate startDate, final LocalDate endDate){
		final List<PojoTransactionGetReportRes> transactions = new ArrayList<>();
		
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT 'Course' AS activity_type, c.course_name, DATE(c.start_date), COUNT(tt.user_id) AS total_participants FROM t_transaction tt ")
			.append("INNER JOIN course c ON tt.course_id = c.id ")
			.append("WHERE DATE(c.start_date) >= DATE(:startDate) AND DATE(c.end_date) <= DATE(:endDate) AND tt.is_approved = true ")
			.append("GROUP BY c.course_name, c.start_date ");
			
			final List<Object> result = em().createNativeQuery(toStr(str))
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.getResultList();
			for(final Object objs :  result) {
				final Object[] obj = (Object[]) objs;
				
				final PojoTransactionGetReportRes transactionReport = new PojoTransactionGetReportRes();
				transactionReport.setActivityType(obj[0].toString());
				transactionReport.setItemName(obj[1].toString());
				transactionReport.setStartDate(DateUtil.localDateToStr(Date.valueOf(obj[2].toString()).toLocalDate()));
				transactionReport.setTotalParticipants(Long.valueOf(obj[3].toString()).intValue());
				
				transactions.add(transactionReport);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return transactions;
	}
	
	@SuppressWarnings("unchecked")
	public List<PojoTransactionGetReportParticipantSuperAdminRes> getCourseReportSuperAdmin(final LocalDate startDate, final LocalDate endDate){
		final List<PojoTransactionGetReportParticipantSuperAdminRes> transactions = new ArrayList<>();
		
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT p.full_name, c.provider, 'Course' AS activity_type, c.course_name, DATE(c.start_date), COUNT(tt.user_id) AS total_participants FROM t_transaction tt ")
			.append("INNER JOIN course c ON tt.course_id = c.id ")
			.append("INNER JOIN t_user tu ON tt.user_id = tu.id ")
			.append("INNER JOIN profile p ON tu.profile_id = p.id ")
			.append("WHERE DATE(c.start_date) >= DATE(:startDate) AND DATE(c.end_date) <= DATE(:endDate) AND tt.is_approved = true AND tu.is_verified = true ")
			.append("GROUP BY c.course_name, c.start_date, p.full_name, c.provider ");
			
			final List<Object> result = em().createNativeQuery(toStr(str))
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.getResultList();
			for(final Object objs :  result) {
				final Object[] obj = (Object[]) objs;
				
				final PojoTransactionGetReportParticipantSuperAdminRes reportSuperAdmin = new PojoTransactionGetReportParticipantSuperAdminRes();
				reportSuperAdmin.setMemberName(obj[0].toString());
				reportSuperAdmin.setProviderName(obj[1].toString());
				
				reportSuperAdmin.setActivityType(obj[2].toString());
				reportSuperAdmin.setItemName(obj[3].toString());
				reportSuperAdmin.setStartDate(DateUtil.localDateToStr(Date.valueOf(obj[4].toString()).toLocalDate()));
				reportSuperAdmin.setTotalParticipants(Long.valueOf(obj[5].toString()).intValue());
				
				transactions.add(reportSuperAdmin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return transactions;
	}
	
	@SuppressWarnings("unchecked")
	public List<PojoTransactionGetReportRes> getCourseReportIncomeMember(final LocalDate startDate, final LocalDate endDate){
		final List<PojoTransactionGetReportRes> transactions = new ArrayList<>();
		
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT 'Course' AS activity_type, c.course_name, DATE(c.start_date), COUNT(tt.user_id) AS total_participants FROM t_transaction tt ")
			.append("INNER JOIN course c ON tt.course_id = c.id ")
			.append("WHERE DATE(c.start_date) >= DATE(:startDate) AND DATE(c.end_date) <= DATE(:endDate) AND tt.is_approved = true ")
			.append("GROUP BY c.course_name, c.start_date ");
			
			final List<Object> result = em().createNativeQuery(toStr(str))
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.getResultList();
			for(final Object objs :  result) {
				final Object[] obj = (Object[]) objs;
				
				final PojoTransactionGetReportRes transactionReport = new PojoTransactionGetReportRes();
				transactionReport.setActivityType(obj[0].toString());
				transactionReport.setItemName(obj[1].toString());
				transactionReport.setStartDate(DateUtil.localDateToStr(Date.valueOf(obj[2].toString()).toLocalDate()));
				transactionReport.setTotalParticipants(Long.valueOf(obj[3].toString()).intValue());
				
				transactions.add(transactionReport);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return transactions;
	}

}
