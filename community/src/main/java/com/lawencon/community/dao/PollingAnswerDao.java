package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PollingAnswer;

@Repository
public class PollingAnswerDao extends BasePostDao<PollingAnswer>{

	@Override
	PollingAnswer insert(PollingAnswer data) {
		final PollingAnswer res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(Long id) {
		return deleteById(PollingAnswer.class, id);
	}
	
	@SuppressWarnings("unchecked")
	List<PollingAnswer> getByMembershipId (Long userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_answer pa ")
		.append("WHERE pa.user_id = :userId");
		final List<PollingAnswer> res = em().createNativeQuery(toStr(str), PollingAnswer.class).setParameter("userId", userId).getResultList();
		return res;
	}
	
	

}
