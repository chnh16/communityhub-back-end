package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "post_file", uniqueConstraints = {
		@UniqueConstraint(name = "post_file_ck", columnNames = {"post_id", "file_id"}
)})
public class PostFile extends BaseEntity {
	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;

	public Post getPost() {
		return post;
	}

	public File getFile() {
		return file;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
	
	
}
