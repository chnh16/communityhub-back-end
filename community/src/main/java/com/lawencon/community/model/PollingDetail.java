package com.lawencon.community.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.lawencon.base.BaseEntity;

@Entity
public class PollingDetail extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	@Column(nullable = false)
	private String pollingQuestion;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getPollingQuestion() {
		return pollingQuestion;
	}

	public void setPollingQuestion(String pollingQuestion) {
		this.pollingQuestion = pollingQuestion;
	}
	
	
	
	

}
