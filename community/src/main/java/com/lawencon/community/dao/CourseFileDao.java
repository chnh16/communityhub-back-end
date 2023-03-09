package com.lawencon.community.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lawencon.community.model.CourseFile;

@Repository
public class CourseFileDao extends BasePostDao<CourseFile>{

	@Override
	public CourseFile insert(final CourseFile data) {
		final CourseFile res = saveAndFlush(data);
		return res;
	}

	@Override
	public boolean delete(final String id) {
		return deleteById(CourseFile.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public List<CourseFile> getByCourseId (final Long courseId){
		final StringBuilder str = new StringBuilder();
		str.append("SELECT * FROM course_file cf ")
		.append("WHERE cf.course_id = :courseId");
		final List<CourseFile> res = em().createNativeQuery(toStr(str), CourseFile.class).setParameter("courseId", courseId).getResultList();
		return res;
		
		
	}

	
}
