package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Course;

@Repository
public class CourseDao extends MasterDao<Course>{

	@Override
	public Optional<Course> getById(final Long id) {
		final Course res = getById(Course.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	public Optional<Course> getRefById(final Long id) {
		final Course res = getByIdRef(Course.class, id);
		return Optional.ofNullable(res);
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
	public boolean delete(final Long id) {
		return deleteById(Course.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<Course> getByCategoryId (final Long categoryId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course c ")
		.append("WHERE c.category_id = :categoryId");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).setParameter("categoryId", categoryId).getResultList();
		return res;
	}

}
