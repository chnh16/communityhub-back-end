package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.base.ConnHandler;
import com.lawencon.community.model.Course;

@Repository
public class CourseDao extends MasterDao<Course>{

	@Override
	public Optional<Course> getById(final String id) {
		return Optional.ofNullable(super.getById(Course.class, id));
	}

	@Override
	public Course getRefById(final String id) {
		final Course res = getByIdRef(Course.class, id);
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Course> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course")
		.append(" WHERE is_active = true");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).getResultList();
		return res;
	}

	@Override
	public Course update(final Course data) {
		final Course res = saveAndFlush(data);
		return res;
	}

	@Override
	public Course insert(final Course data) {
		final Course res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(Course.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getByCategoryId (final String catgoryId, Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course c ")
		.append("WHERE c.category_id = :catgoryId");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).setParameter("catgoryId", catgoryId).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getByPriceAsc(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course")
		.append(" WHERE is_active = TRUE")
		.append(" ORDER BY price ASC");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getCourse(final Integer limit, final Integer offset) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course c")
			.append(" WHERE c.is_active = true");
		final List<Course> res = em().createNativeQuery(toStr(str).toString(), Course.class)
				.setMaxResults(limit)
				.setFirstResult((offset-1)*limit)
				.getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getByPriceDesc(){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course")
		.append(" WHERE is_active = TRUE")
		.append(" ORDER BY price DESC");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getCoursePage(final Integer limit, final Integer offset) {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course c ")
			.append(" WHERE c.is_active = true");
		final List<Course> res = em().createNativeQuery(toStr(str).toString(), Course.class)
				.setMaxResults(limit)
				.setFirstResult((offset-1)*limit)
				.getResultList();
		return res;
	}
	
	public int getTotalCourse() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT COUNT(c.id) FROM course c")
			.append(" WHERE c.is_active = true");
		
		final int totalCourse = Integer.valueOf(ConnHandler.getManager().createNativeQuery(toStr(str)
				.toString())
				.getSingleResult().toString());
		return totalCourse;
	}
	
	public Optional<Course> getCourseById(final String id) {
		Course res = null;
		try {
			final StringBuilder str = new StringBuilder();
			str.append("SELECT * FROM course c ")
				.append("WHERE c.id = :id AND c.is_active = true");
			final Object result = em().createNativeQuery(toStr(str), Course.class)
					.setParameter("id", id)
					.getSingleResult();
			if(result != null) {
				res = new Course();
				res = (Course) result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return Optional.ofNullable(res);
	}

	@Override
	public Course getByIdAndDetach(final String id) {
		return super.getByIdAndDetach(Course.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getMyCourse(final String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course")
		.append(" WHERE user_id = :userId AND is_active = TRUE");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getByPriceAscMyCourse(String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course")
		.append(" WHERE user_id = :userId AND is_active = TRUE")
		.append(" ORDER BY price ASC");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getByPriceDescMyCourse(String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course")
		.append(" WHERE user_id = :userId AND is_active = TRUE")
		.append(" ORDER BY price DESC");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getByCategoryIdMyCourse (String id, String userId, final Integer limit, final Integer offset){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course c ")
		.append("WHERE c.category_id = :id AND c.user_id = :userId")
		.append(" AND c.is_active = TRUE");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).setParameter("id", id).setParameter("userId", userId).setMaxResults(limit)
				.setFirstResult((offset-1)*limit).getResultList();
		return res;
	}
}
