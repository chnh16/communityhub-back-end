package com.lawencon.community.pojo.pollingchoice;

public class PojoPollingChoiceGetAllRes {
	private String pollingChoiceId;
	private String pollingDetailId;
	private String choiceContent;
	private Integer ver;
	
	public String getPollingChoiceId() {
		return pollingChoiceId;
	}
	public String getPollingDetailId() {
		return pollingDetailId;
	}
	public void setPollingChoiceId(String pollingChoiceId) {
		this.pollingChoiceId = pollingChoiceId;
	}
	public void setPollingDetailId(String pollingDetailId) {
		this.pollingDetailId = pollingDetailId;
	}
	public String getChoiceContent() {
		return choiceContent;
	}
	public void setChoiceContent(String choiceContent) {
		this.choiceContent = choiceContent;
	}
	public Integer getVer() {
		return ver;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	
	
}
