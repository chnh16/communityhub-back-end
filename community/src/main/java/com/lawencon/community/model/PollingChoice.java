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
	@JoinColumn(name = "polling_detail_id", nullable = false)
	private PollingDetail pollingDetail;

	public String getChoiceContent() {
		return choiceContent;
	}

	public void setChoiceContent(String choiceContent) {
		this.choiceContent = choiceContent;
	}

	public PollingDetail getPollingDetail() {
		return pollingDetail;
	}

	public void setPollingDetail(PollingDetail pollingDetail) {
		this.pollingDetail = pollingDetail;
	}
	
	

}
