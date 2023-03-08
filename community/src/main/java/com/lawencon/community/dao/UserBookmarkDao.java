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
	public boolean delete(final Long id) {
		return deleteById(UserBookmark.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserBookmark> getAllByUser(final Long userId){
		final String sql = "SELECT * FROM user_bookmark "
				+ "WHERE user_id = :id AND is_active = true ";
		final List<UserBookmark> res = em().createNativeQuery(sql, UserBookmark.class)
				.setParameter("user_id", userId)
				.getResultList();
		return res;
	}

}
