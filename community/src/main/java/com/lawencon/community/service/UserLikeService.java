package com.lawencon.community.service;

import com.lawencon.community.dao.PostDao;
import com.lawencon.community.dao.UserDao;
import com.lawencon.community.dao.UserLikeDao;
import com.lawencon.community.model.UserLike;

public class UserLikeService {
	private final UserLikeDao userLikeDao;
	private final UserDao userDao;
	private final PostDao postDao;
	
	public UserLikeService(final UserLikeDao userLikeDao, final UserDao userDao,final PostDao postDao ) {
		this.userLikeDao = userLikeDao;
		this.userDao = userDao;
		this.postDao = postDao;
	}
	
	public UserLike insertUserLike(final UserLike data) {
		UserLike userLikeInsert = null;
//		try {
//			ConnHandler.begin();
//			industryInsert = industryDao.insert(data);
//			ConnHandler.commit();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return userLikeInsert;
	}
	
}
