package com.lawencon.community.pojo.post;

public class PojoPostUpdateReq {
	private String id;
	private String userId;
	private String postTitle;
	private String postContent;
	private String postTypeId;
	private String categoryId;
	private Integer ver;
	
	public String getId() {
		return id;
	}
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
	public Integer getVer() {
		return ver;
	}
	public void setId(String id) {
		this.id = id;
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
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
