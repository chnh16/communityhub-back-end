package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.UserCourse;
import com.lawencon.community.model.UserEvent;

@Repository
public class UserCourseDao extends BasePostDao<UserCourse>{

	@Override
	UserCourse insert(UserCourse data) {
		final UserCourse res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(Long id) {
		return deleteById(UserCourse.class, id);
	}
	
	List<UserCourse> getByCourseId (Long userId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_course uc ")
		.append("WHERE uc.user_id = :userId");
		final List<UserCourse> res = em().createNativeQuery(toStr(str), UserCourse.class).setParameter("userId", userId).getResultList();
		return res;
	}

	
	
	

}
