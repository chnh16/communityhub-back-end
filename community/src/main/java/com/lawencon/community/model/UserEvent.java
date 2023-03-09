package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.lawencon.base.BaseEntity;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "user_event_ck", columnNames = {"user_id", "event_id"}
)})
public class UserEvent extends BaseEntity {
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	@OneToOne
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	
	
	

}
