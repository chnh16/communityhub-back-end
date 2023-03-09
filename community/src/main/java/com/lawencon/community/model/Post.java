package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "post", uniqueConstraints = {
		@UniqueConstraint(name = "post_ck", columnNames = {"post_type_id", "category_id"}
)})
public class Post extends BaseEntity {
	
	@Column(length = 100, nullable = false)
	private String postTitle;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String postContent;
	
	@OneToOne
	@JoinColumn(name="post_type_id", nullable = false)
	private PostType postType;
	
	@OneToOne
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	public String getPostTitle() {
		return postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public PostType getPostType() {
		return postType;
	}

	public Category getCategory() {
		return category;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public void setPostType(PostType postType) {
		this.postType = postType;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
}	
