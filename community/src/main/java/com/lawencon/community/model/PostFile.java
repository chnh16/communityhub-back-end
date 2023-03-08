package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "post_file")
public class PostFile extends BaseEntity {
	@OneToOne
	@Column(name = "post_id", nullable = false)
	private Post post;
	
	@OneToOne
	@Column(name = "file_id")
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
