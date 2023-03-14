package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.RegisterVerification;

@Repository
public class RegisterVerificationDao extends BasePostDao<RegisterVerification>{

	@Override
	RegisterVerification insert(RegisterVerification data) {
		final RegisterVerification res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(String id) {
		return deleteById(RegisterVerification.class, id);
		}
	
}
