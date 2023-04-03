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
		.append("WHERE ue.user_id = :userId AND is_active = TRUE");
		final List<UserEvent> res = em().createNativeQuery(toStr(str), UserEvent.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserEvent> getByPriceAsc(String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_event ue")
		.append(" INNER JOIN t_event te  ON te.id  = ue.event_id")
		.append(" WHERE ue.user_id = :userId AND ue.is_active = TRUE")
		.append(" ORDER BY te.price ASC");
		final List<UserEvent> res = em().createNativeQuery(toStr(str), UserEvent.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserEvent> getByPriceDesc(String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_event ue")
		.append(" INNER JOIN t_event te  ON te.id  = ue.event_id")
		.append(" WHERE ue.user_id = :userId AND ue.is_active = TRUE")
		.append(" ORDER BY te.price DESC");
		final List<UserEvent> res = em().createNativeQuery(toStr(str), UserEvent.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserEvent> getByCategoryId (String id, String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_event ue ")
		.append(" INNER JOIN t_event te  ON te.id  = ue.event_id")
		.append(" WHERE ue.user_id = :userId AND te.category_id = :id AND ue.is_active = TRUE");
		final List<UserEvent> res = em().createNativeQuery(toStr(str), UserEvent.class).setParameter("id", id).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}

	@Override
	UserEvent getByIdAndDetach(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
