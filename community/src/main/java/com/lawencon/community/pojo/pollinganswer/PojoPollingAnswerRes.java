package com.lawencon.community.pojo.pollinganswer;

import java.util.List;

public class PojoPollingAnswerRes {
	private String id;
	private String choiceId;
	private List<PojoPollingAnswerGetCountRes> result;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getChoiceId() {
		return choiceId;
	}
	public void setChoiceId(String choiceId) {
		this.choiceId = choiceId;
	}
	public List<PojoPollingAnswerGetCountRes> getResult() {
		return result;
	}
	public void setResult(List<PojoPollingAnswerGetCountRes> result) {
		this.result = result;
	}
}
