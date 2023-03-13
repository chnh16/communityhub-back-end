package com.lawencon.community.pojo.article;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoArticleUpdateReq {

	private String id;
	private PojoFileInsertReq photoId;
	private String articleTitle;
	private String articleContent;
	private Integer ver;
	

	public PojoFileInsertReq getPhotoId() {
		return photoId;
	}

	public void setPhotoId(PojoFileInsertReq photoId) {
		this.photoId = photoId;
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

}
