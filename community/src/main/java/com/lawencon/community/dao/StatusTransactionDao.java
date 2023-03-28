package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.StatusTransaction;

@Repository
public class StatusTransactionDao extends MasterDao<StatusTransaction> {

	@Override
	Optional<StatusTransaction> getById(String id) {
		final StatusTransaction res = getById(StatusTransaction.class, id);
		return Optional.ofNullable(res);

	}

	@Override
	StatusTransaction getRefById(String id) {
		final StatusTransaction res = getById(StatusTransaction.class, id);
		return res;
	}

	@Override
	StatusTransaction getByIdAndDetach(String id) {
		return super.getByIdAndDetach(StatusTransaction.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	List<StatusTransaction> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM status_transaction ").append("WHERE is_active = true");
		final List<StatusTransaction> res = em().createNativeQuery(toStr(str), StatusTransaction.class).getResultList();
		return res;
	}

	@Override
	StatusTransaction update(StatusTransaction data) {
		final StatusTransaction res = saveAndFlush(data);
		return res;
	}

	@Override
	StatusTransaction insert(StatusTransaction data) {
		final StatusTransaction res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(String id) {
		return deleteById(StatusTransaction.class, id);
	}
	
//	public Optional<StatusTransaction> getByStatusCode(final String statusCode) {
//		final StringBuilder str = new StringBuilder();
//		StatusTransaction status = null;
//		try {
//			str.append("SELECT r.id, r.created_by, r.updated_by, r.created_at, r.updated_at, r.ver, r.is_active FROM status_transaction r")
//				.append(" WHERE r.status_code = :statusCode")
//				.append(" AND r.is_active = TRUE");
//			final Object result = em().createNativeQuery(toStr(str)).setParameter("statusCode", statusCode).getSingleResult();
//			if (result != null) {
//
//				status = new StatusTransaction();
//				final Object[] objArr = (Object[]) result;
//
//				status.setId(objArr[0].toString());
//
//				status.setCreatedBy(objArr[1].toString());
//				if (objArr[2] != null) {
//					status.setUpdatedBy(objArr[2].toString());
//				}
//
//				status.setCreatedAt(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime());
//
//				if (objArr[4] != null) {
//					status.setUpdatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
//				}
//
//				status.setVersion(Integer.valueOf(objArr[5].toString()));
//				status.setIsActive(Boolean.valueOf(objArr[6].toString()));
//			}
//		} catch (final Exception e) {
//			e.printStackTrace();
//		}
//		return Optional.ofNullable(status);
//	}
//	
	public Optional<StatusTransaction> getByStatusCode(final String statusCode) {
		StatusTransaction status = null;
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT r.id FROM status_transaction r")
			.append(" WHERE r.status_code = :statusCode")
			.append(" AND r.is_active = TRUE");
			final Object res = em().createNativeQuery(toStr(str)).setParameter("statusCode", statusCode)
					.getSingleResult();
			if (res != null) {
				status = new StatusTransaction();
				final String objArr = (String) res;
				status.setId(objArr);
			}
		} catch(final Exception e){
			e.printStackTrace();
		}
		return Optional.ofNullable(status);
	}
	

}
