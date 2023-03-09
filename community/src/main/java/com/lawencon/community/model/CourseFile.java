package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "course_file_ck", columnNames = {"file_id", "course_id"}
)})
public class CourseFile extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "file_id", nullable = false)
	private File file;
	
	@OneToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	


}
