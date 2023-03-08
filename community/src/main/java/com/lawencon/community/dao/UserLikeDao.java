package com.lawencon.community.dao;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.UserLike;

@Repository
public class UserLikeDao extends BasePostDao<UserLike>{

	@Override
	public UserLike insert(final UserLike data) {
		final UserLike res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final Long id) {
		return deleteById(UserLike.class, id);
	}
	
}
