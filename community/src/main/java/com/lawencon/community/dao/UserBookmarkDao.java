package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.UserBookmark;

@Repository
public class UserBookmarkDao extends BasePostDao<UserBookmark> {

	@Override
	public UserBookmark insert(final UserBookmark data) {
		final UserBookmark res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(UserBookmark.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBookmark> getAllByUser(final String userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_bookmark ")
			.append("WHERE user_id = :userId AND is_active = true ");
		final List<UserBookmark> res = em().createNativeQuery(toStr(str), UserBookmark.class)
				.setParameter("userId", userId)
				.getResultList();
		return res;
	}

}
