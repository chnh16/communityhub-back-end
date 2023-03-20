package com.lawencon.community.pojo.post;

import java.util.List;

import com.lawencon.community.pojo.file.PojoFileInsertReq;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceInsertReq;

public class PojoPostInsertReq {
	private String postTitle;
	private String postContent;
	private String categoryId;
	private Boolean isPremium;
	private List<PojoFileInsertReq> file;
	private List<PojoPollingChoiceInsertReq> polling;
	
	public String getPostTitle() {
		return postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}
	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public List<PojoFileInsertReq> getFile() {
		return file;
	}
	public void setFile(List<PojoFileInsertReq> file) {
		this.file = file;
	}
	public List<PojoPollingChoiceInsertReq> getPolling() {
		return polling;
	}
	public void setPolling(List<PojoPollingChoiceInsertReq> polling) {
		this.polling = polling;
	}
	public Boolean getIsPremium() {
		return isPremium;
	}
	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}
	
}
