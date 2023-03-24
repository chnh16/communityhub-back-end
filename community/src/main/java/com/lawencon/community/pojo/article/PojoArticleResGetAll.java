package com.lawencon.community.pojo.article;

import java.time.LocalDateTime;

public class PojoArticleResGetAll {

	private String id;
	private String articleTitle;
	private String articleContent;
	private String photoId;
	private String userFileId;
	private String createdBy;
	private LocalDateTime createdAt;
	private Integer ver;
	public String getUserFileId() {
		return userFileId;
	}

	public void setUserFileId(String userFileId) {
		this.userFileId = userFileId;
	}

	
	public String getCreatedBy() {
		return createdBy;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}
}
