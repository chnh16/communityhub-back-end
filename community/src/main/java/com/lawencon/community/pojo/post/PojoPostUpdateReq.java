package com.lawencon.community.pojo.post;

public class PojoPostUpdateReq {
	private String id;
	private String postTitle;
	private String postContent;
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
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public void setVer(Integer ver) {
		this.ver = ver;
	}
	
}
