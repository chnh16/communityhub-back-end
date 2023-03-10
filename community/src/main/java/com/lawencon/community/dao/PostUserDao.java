package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.PostUser;

@Repository
public class PostUserDao extends BasePostDao<PostUser>{

	@Override
	PostUser insert(PostUser data) {
		final PostUser res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(String id) {
		return deleteById(PostUser.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<PostUser> getByUserId (final String userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM post_user pu ")
		.append("WHERE pu.user_id = :userId");
		final List<PostUser> res = em().createNativeQuery(toStr(str), PostUser.class).setParameter("userId", userId).getResultList();
		return res;
	}

	
}
