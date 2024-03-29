package com.lawencon.community.pojo.postdetail;

import java.time.LocalDateTime;

public class PojoPostDetailGetAllRes {
	private String id;
	private String userFileId;
	private String fullName;
	private String detailContent;
	private LocalDateTime postedAt;
	private Integer ver;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}

	public String getUserFileId() {
		return userFileId;
	}

	public void setUserFileId(String userFileId) {
		this.userFileId = userFileId;
	}
	
}
