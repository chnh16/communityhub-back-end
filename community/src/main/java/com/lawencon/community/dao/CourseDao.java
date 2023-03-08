package com.lawencon.community.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.Course;

@Repository
public class CourseDao extends MasterDao<Course>{

	@Override
	Optional<Course> getById(Long id) {
		final Course res = getById(Course.class, id);
		return Optional.ofNullable(res);
	}

	@Override
	Optional<Course> getRefById(Long id) {
		final Course res = getByIdRef(Course.class, id);
		return Optional.ofNullable(res);
	}

	@SuppressWarnings("unchecked")
	@Override
	List<Course> getAll() {
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course")
		.append(" WHERE is_active = true");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).getResultList();
		return res;
	}

	@Override
	Course update(Course data) {
		final Course res = saveAndFlush(data);
		return res;
	}

	@Override
	Course insert(Course data) {
		final Course res = saveAndFlush(data);
		return res;
	}

	@Override
	boolean delete(Long id) {
		return deleteById(Course.class, id);
	}
	
	@SuppressWarnings("unchecked")
	List<Course> getByCategoryId (Long categoryId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course c ")
		.append("WHERE c.category_id = :categoryId");
		final List<Course> res = em().createNativeQuery(toStr(str), Course.class).setParameter("categoryId", categoryId).getResultList();
		return res;
	}

}
