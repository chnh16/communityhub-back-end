package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PollingAnswer;

@Repository
public class PollingAnswerDao extends BasePostDao<PollingAnswer>{

	@Override
	public PollingAnswer insert(final PollingAnswer data) {
		final PollingAnswer res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(PollingAnswer.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<PollingAnswer> getByMembershipId (final String userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM polling_answer pa ")
		.append("WHERE pa.user_id = :userId");
		final List<PollingAnswer> res = em().createNativeQuery(toStr(str), PollingAnswer.class).setParameter("userId", userId).getResultList();
		return res;
	}
	
	public Long getCount(final String detailId) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(pa.polling_choice_id) FROM polling_answer pa ")
		.append(" INNER JOIN polling_choice pc ON pc.id = pa.polling_choice_id ")
		.append(" WHERE pc.polling_detail_id = :detailId");
		
		Long countPoll = null;
		countPoll = Long.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str).toString())
				.setParameter("detailId", detailId)
				.getSingleResult().toString());
		
		return countPoll;
	}

}
