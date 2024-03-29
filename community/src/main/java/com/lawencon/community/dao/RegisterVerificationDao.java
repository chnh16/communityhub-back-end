package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.RegisterVerification;

@Repository
public class RegisterVerificationDao extends BasePostDao<RegisterVerification> {

	@Override
	public RegisterVerification insert(final RegisterVerification data) {
		final RegisterVerification res = saveAndFlush(data);
		return res;
	}

	public RegisterVerification update(final RegisterVerification data) {
		final RegisterVerification res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(String id) {
		return deleteById(RegisterVerification.class, id);
	}
	
	public Optional<RegisterVerification> getIdByEmail(final String email) {
		RegisterVerification res = null;
		
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT rv.id, rv.created_by, rv.updated_by, rv.created_at, rv.updated_at, rv.ver, rv.is_active ")
			.append("FROM register_verification rv ")
			.append("WHERE rv.email = :email AND rv.is_active = TRUE ");
			
			final Object result = em().createNativeQuery(toStr(str)).setParameter("email", email).getSingleResult();
			
			if(result != null) {
				
				res = new RegisterVerification();
				
				final Object[] objArr = (Object[]) result;

				res.setId(objArr[0].toString());
				
				res.setCreatedBy(objArr[1].toString());
				if (objArr[2] != null) {
					res.setUpdatedBy(objArr[2].toString());
				}

				res.setCreatedAt(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime());

				if (objArr[4] != null) {
					res.setUpdatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				}

				res.setVersion(Integer.valueOf(objArr[5].toString()));
				res.setIsActive(Boolean.valueOf(objArr[6].toString()));
			}
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(res);
	}
	
	public Optional<RegisterVerification> getVerification(String email, String codeVerification) {
		RegisterVerification registerVerification = null;
		
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT rv.id, rv.created_by, rv.updated_by, rv.created_at, rv.updated_at, rv.ver, rv.is_active ")
			.append("FROM register_verification rv ")
			.append("WHERE email = :email AND code_verifcation = :codeVerification AND expired > now() AND is_active = TRUE ");
			
			final Object result = em().createNativeQuery(toStr(str)).setParameter("email", email).setParameter("codeVerification", codeVerification).getSingleResult();
			
			if (result != null) {

				registerVerification = new RegisterVerification();
				final Object[] objArr = (Object[]) result;

				registerVerification.setId(objArr[0].toString());

				registerVerification.setCreatedBy(objArr[1].toString());
				if (objArr[2] != null) {
					registerVerification.setUpdatedBy(objArr[2].toString());
				}

				registerVerification.setCreatedAt(Timestamp.valueOf(objArr[3].toString()).toLocalDateTime());

				if (objArr[4] != null) {
					registerVerification.setUpdatedAt(Timestamp.valueOf(objArr[4].toString()).toLocalDateTime());
				}

				registerVerification.setVersion(Integer.valueOf(objArr[5].toString()));
				registerVerification.setIsActive(Boolean.valueOf(objArr[6].toString()));
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Optional.ofNullable(registerVerification);
	}

	@Override
	RegisterVerification getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(RegisterVerification.class, id);
	}

}
