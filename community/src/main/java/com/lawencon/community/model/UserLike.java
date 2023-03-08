package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "user_like")
public class UserLike extends BaseEntity {
	
	@OneToOne
	@Column(name = "post_id", nullable = false)
	private Post post;
	
	@OneToOne
	@Column(name = "user_id", nullable = false)
	private User user;

	public Post getPost() {
		return post;
	}

	public User getUser() {
		return user;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
