package com.lawencon.community.pojo.pollingchoice;

public class PojoPollingChoiceUpdateReq {
	private String id;
	private String choiceContent;
	private String pollingDetailId;
	private Integer ver;
	
	public String getId() {
		return id;
	}
	public String getChoiceContent() {
		return choiceContent;
	}
	
	public Integer getVer() {
		return ver;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setChoiceContent(String choiceContent) {
		this.choiceContent = choiceContent;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getPollingDetailId() {
		return pollingDetailId;
	}
	public void setPollingDetailId(String pollingDetailId) {
		this.pollingDetailId = pollingDetailId;
	}
	
	
}
