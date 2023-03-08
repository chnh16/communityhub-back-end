package com.lawencon.community.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.lawencon.base.BaseEntity;

@Entity
public class PollingAnswer extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name = "polling_choice_id", nullable = false)
	private PollingChoice pollingChoice;
	
	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	

}
