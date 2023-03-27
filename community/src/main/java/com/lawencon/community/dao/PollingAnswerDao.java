package com.lawencon.community.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.PollingAnswer;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;

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
	public List<PollingAnswer> getByUserId (final String userId){
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
	
	@SuppressWarnings("unchecked")
	public List<PojoPollingAnswerGetCountRes> getCountByChoiceId(final String detailId){
		final List<PojoPollingAnswerGetCountRes> listPollingAnswer = new ArrayList<>();
		
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT pc.id, COUNT(pa.polling_choice_id) FROM polling_answer pa ")
			.append(" INNER JOIN polling_choice pc ON pc.id = pa.polling_choice_id ")
			.append("GROUP BY pc.id ")
			.append(" HAVING pc.polling_detail_id = :detailId");
			
			final List<Object> result = em().createNativeQuery(toStr(str))
					.setParameter("detailId", detailId)
					.getResultList();
			
			if (result != null) {
				for (final Object objs : result) {
					final Object[] obj = (Object[]) objs;
					
					final PojoPollingAnswerGetCountRes pollingChoice = new PojoPollingAnswerGetCountRes();
					pollingChoice.setPollingChoiceId(obj[0].toString());
					pollingChoice.setCountPollAnswer(Long.valueOf(obj[1].toString()));
					
					listPollingAnswer.add(pollingChoice);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return listPollingAnswer;
	}

}
