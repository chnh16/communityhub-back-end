package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.EventFile;
import com.lawencon.community.model.UserEvent;

@Repository
public class UserEventDao extends BasePostDao<UserEvent>{

	@Override
	UserEvent insert(UserEvent data) {
		final UserEvent res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(Long id) {
		return deleteById(UserEvent.class, id);
	}

	List<UserEvent> getByUserId (Long userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_event ue ")
		.append("WHERE ue.user_id = :userId");
		final List<UserEvent> res = em().createNativeQuery(toStr(str), UserEvent.class).setParameter("userId", userId).getResultList();
		return res;
	}
	

}
