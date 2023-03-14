package com.lawencon.community.pojo.pollingdetail;

import java.util.List;

import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceInsertReq;

public class PojoPollingDetailInsertReq {

	private String postId;
	private String pollingQuestion;
	private List<PojoPollingChoiceInsertReq> choices;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPollingQuestion() {
		return pollingQuestion;
	}

	public void setPollingQuestion(String pollingQuestion) {
		this.pollingQuestion = pollingQuestion;
	}

	public List<PojoPollingChoiceInsertReq> getChoices() {
		return choices;
	}

	public void setChoices(List<PojoPollingChoiceInsertReq> choices) {
		this.choices = choices;
	}

	

}
