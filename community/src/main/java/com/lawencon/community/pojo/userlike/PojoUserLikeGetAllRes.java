package com.lawencon.community.pojo.userlike;

public class PojoUserLikeGetAllRes {
	private String id;
	private String userId;
	private String postId;
	private Long countedLike;
	
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getPostId() {
		return postId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setPostId(String postId) {
		this.postId = postId;
	}
	public Long getCountedLike() {
		return countedLike;
	}
	public void setCountedLike(Long countedLike) {
		this.countedLike = countedLike;
	}
	
	
}
