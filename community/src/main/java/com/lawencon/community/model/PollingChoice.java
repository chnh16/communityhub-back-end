package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.lawencon.base.BaseEntity;

@Entity
public class PollingChoice extends BaseEntity{
	
	@Column(nullable = false)
	private String choiceContent;
	
	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	public String getChoiceContent() {
		return choiceContent;
	}

	public void setChoiceContent(String choiceContent) {
		this.choiceContent = choiceContent;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
}
