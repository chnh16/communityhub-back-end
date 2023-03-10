package com.lawencon.community.pojo.postdetail;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoPostDetailUpdateReq {

	private String id;
	private String postId;
	private PojoFileInsertReq file;
	private String detailContent;
	private Integer ver;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public PojoFileInsertReq getFile() {
		return file;
	}

	public void setFile(PojoFileInsertReq file) {
		this.file = file;
	}


}
