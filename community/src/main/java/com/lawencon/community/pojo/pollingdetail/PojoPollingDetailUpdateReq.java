package com.lawencon.community.pojo.pollingdetail;

import java.util.List;

import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceUpdateReq;

public class PojoPollingDetailUpdateReq {

	private String id;
	private String pollingQuestion;
	private List<PojoPollingChoiceUpdateReq> choices;
	private Integer ver;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPollingQuestion() {
		return pollingQuestion;
	}

	public void setPollingQuestion(String pollingQuestion) {
		this.pollingQuestion = pollingQuestion;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public List<PojoPollingChoiceUpdateReq> getChoices() {
		return choices;
	}

	public void setChoices(List<PojoPollingChoiceUpdateReq> choices) {
		this.choices = choices;
	}
}
