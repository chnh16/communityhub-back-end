package com.lawencon.community.pojo.pollingdetail;

import java.util.List;

import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceGetAllRes;

public class PojoPollingDetailGetAllRes {
	private String id;
	private String postId;
	private String pollingQuestion;
	private List<PojoPollingChoiceGetAllRes> listChoice;
	
	public String getId() {
		return id;
	}
	public String getPostId() {
		return postId;
	}
	public String getPollingQuestion() {
		return pollingQuestion;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public void setPollingQuestion(String pollingQuestion) {
		this.pollingQuestion = pollingQuestion;
	}
	public List<PojoPollingChoiceGetAllRes> getListChoice() {
		return listChoice;
	}
	public void setListChoice(List<PojoPollingChoiceGetAllRes> listChoice) {
		this.listChoice = listChoice;
	}
}
