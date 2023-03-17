package com.lawencon.community.pojo.post;

import com.lawencon.community.pojo.userbookmark.PojoPostBookmarkRes;
import com.lawencon.community.pojo.userlike.PojoPostLikeRes;

public class PojoPostGetAllRes {
	private String id;
	private String userId;
	private String fullName;
	private String postTitle;
	private String postContent;
	private String postTypeId;
	private String typeName;
	private String categoryId;
	private String categoryName;
	private PojoPostLikeRes isLiked;
	private PojoPostBookmarkRes isBookmarked;
	
	public String getPostTitle() {
		return postTitle;
	}
	public String getPostContent() {
		return postContent;
	}
	public String getPostTypeId() {
		return postTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public String getCategoryName() {
		return categoryName;
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
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public PojoPostLikeRes getIsLiked() {
		return isLiked;
	}
	public void setIsLiked(PojoPostLikeRes isLiked) {
		this.isLiked = isLiked;
	}
	public PojoPostBookmarkRes getIsBookmarked() {
		return isBookmarked;
	}
	public void setIsBookmarked(PojoPostBookmarkRes isBookmarked) {
		this.isBookmarked = isBookmarked;
	}
}
