package com.lawencon.community.pojo.postdetail;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoPostDetailInsertReq {

	private String postId;
	private PojoFileInsertReq file;
	private String detailContent;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}


	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public PojoFileInsertReq getFile() {
		return file;
	}

	public void setFile(PojoFileInsertReq file) {
		this.file = file;
	}

}
