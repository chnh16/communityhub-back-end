package com.lawencon.community.pojo.userbookmark;

public class PojoUserBookmarkGetAllRes {

	private String id;
	private String userId;
	private String postId;
	private Long countedBookmark;
	
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

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Long getCountedBookmark() {
		return countedBookmark;
	}

	public void setCountedBookmark(Long countedBookmark) {
		this.countedBookmark = countedBookmark;
	}

}
