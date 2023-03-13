package com.lawencon.community.pojo.pollingdetail;

public class PojoPollingDetailUpdateReq {

	private String id;
	private String pollingQuestion;
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

}
