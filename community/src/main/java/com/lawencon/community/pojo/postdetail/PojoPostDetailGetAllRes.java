package com.lawencon.community.pojo.postdetail;

public class PojoPostDetailGetAllRes {

	private String id;
	private String fullName;
	private String detailContent;
	private String fileId;
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

}
