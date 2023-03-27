package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "polling_answer_ck", columnNames = {"polling_choice_id", "user_id"}
)})
public class PollingAnswer extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "polling_choice_id", nullable = false)
	private PollingChoice pollingChoice;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public PollingChoice getPollingChoice() {
		return pollingChoice;
	}

	public User getUser() {
		return user;
	}

	public void setPollingChoice(PollingChoice pollingChoice) {
		this.pollingChoice = pollingChoice;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
