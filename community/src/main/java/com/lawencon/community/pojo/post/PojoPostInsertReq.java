package com.lawencon.community.pojo.post;

public class PojoPostInsertReq {
	private String userId;
	private String postTitle;
	private String postContent;
	private String postTypeId;
	private String categoryId;
	public String getPostTitle() {
		return postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public String getPostTypeId() {
		return postTypeId;
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
	public void setPostTypeId(String postTypeId) {
		this.postTypeId = postTypeId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
