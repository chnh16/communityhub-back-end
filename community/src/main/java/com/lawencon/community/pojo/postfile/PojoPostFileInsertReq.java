package com.lawencon.community.pojo.postfile;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoPostFileInsertReq {
	private String postId;
	private PojoFileInsertReq file;
	
	public String getPostId() {
		return postId;
	}
	
	public void setPostId(String postId) {
		this.postId = postId;
	}

	public PojoFileInsertReq getFile() {
		return file;
	}

	public void setFile(PojoFileInsertReq file) {
		this.file = file;
	}
	
	
	
}
