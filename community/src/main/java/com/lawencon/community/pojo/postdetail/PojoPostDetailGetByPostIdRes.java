package com.lawencon.community.pojo.postdetail;

public class PojoPostDetailGetByPostIdRes {

	private String id;
	private String postId;
	private String fileId;
	private String detailContent;
	private Long countedCommentar;
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

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public Long getCountedCommentar() {
		return countedCommentar;
	}

	public void setCountedCommentar(Long countedCommentar) {
		this.countedCommentar = countedCommentar;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

}
