package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "user_bookmark")
public class UserBookmark extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
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
