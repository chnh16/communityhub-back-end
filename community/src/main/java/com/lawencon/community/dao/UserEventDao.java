package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.UserEvent;

@Repository
public class UserEventDao extends BasePostDao<UserEvent>{

	@Override
	public UserEvent insert(final UserEvent data) {
		final UserEvent res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(UserEvent.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<UserEvent> getByUserId (final String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_event ue ")
		.append("WHERE ue.user_id = :userId");
		final List<UserEvent> res = em().createNativeQuery(toStr(str), UserEvent.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	

}
