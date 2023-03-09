package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.UserCourse;

@Repository
public class UserCourseDao extends BasePostDao<UserCourse>{

	@Override
	public UserCourse insert(final UserCourse data) {
		final UserCourse res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(UserCourse.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCourse> getByCourseId (final Long userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_course uc ")
		.append("WHERE uc.user_id = :userId");
		final List<UserCourse> res = em().createNativeQuery(toStr(str), UserCourse.class).setParameter("userId", userId).getResultList();
		return res;
	}

	
	
	

}
