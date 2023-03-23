package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "post_detail",uniqueConstraints = {
		@UniqueConstraint(name = "post_detail_ck", columnNames = {"post_id", "file_id"}
)})
public class PostDetail extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToOne
	@JoinColumn(name = "file_id")
	private File file;
	
	@Column(columnDefinition = "TEXT", nullable = false)
	private String detailContent;

	public Post getPost() {
		return post;
	}

	public File getFile() {
		return file;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
