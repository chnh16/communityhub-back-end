package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.UserCourse;
import com.lawencon.community.model.UserEvent;

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
	public List<UserCourse> getByUserId (final String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_course uc ")
		.append("WHERE uc.user_id = :userId");
		final List<UserCourse> res = em().createNativeQuery(toStr(str), UserCourse.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCourse> getByPriceAsc(String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_course uc")
		.append(" INNER JOIN course c  ON c.id  = uc.course_id")
		.append(" WHERE uc.user_id = :userId AND uc.is_active = TRUE")
		.append(" ORDER BY c.price ASC");
		final List<UserCourse> res = em().createNativeQuery(toStr(str), UserCourse.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCourse> getByPriceDesc(String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_course uc")
		.append(" INNER JOIN course c  ON c.id  = uc.course_id")
		.append(" WHERE uc.user_id = :userId AND uc.is_active = TRUE")
		.append(" ORDER BY c.price DESC");
		final List<UserCourse> res = em().createNativeQuery(toStr(str), UserCourse.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserCourse> getByCategoryId (String id, String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM user_course uc ")
		.append(" INNER JOIN course c  ON c.id  = uc.course_id")
		.append(" WHERE uc.user_id = :userId AND c.category_id = :id AND uc.is_active = TRUE");
		final List<UserCourse> res = em().createNativeQuery(toStr(str), UserCourse.class).setParameter("id", id).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}

}
