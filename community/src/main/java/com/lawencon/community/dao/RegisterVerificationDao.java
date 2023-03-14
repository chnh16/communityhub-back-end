package com.lawencon.community.dao;

import java.sql.Timestamp;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.RegisterVerification;
import com.lawencon.community.model.User;

@Repository
public class RegisterVerificationDao extends BasePostDao<RegisterVerification> {

	@Override
	RegisterVerification insert(RegisterVerification data) {
		final RegisterVerification res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(String id) {
		return deleteById(RegisterVerification.class, id);
	}
	
	public Optional<RegisterVerification> getVerification(String email, String codeVerifcation) {
		RegisterVerification registerVerification = null;
		
		try {
			final String sql = "SELECT rv.id, rv.created_by, rv.updated_by, rv.created_at, rv.updated_at, rv.ver, rv.is_active "
					+ "FROM register_verification rv "
					+ "WHERE email = :email AND code_verifcation = :codeVerifcation AND expired > now() AND is_active = TRUE ";
			
			final Object result = em().createNativeQuery(sql).setParameter("email", email).setParameter("codeVerifcation", codeVerifcation).getSingleResult();
			
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

}
