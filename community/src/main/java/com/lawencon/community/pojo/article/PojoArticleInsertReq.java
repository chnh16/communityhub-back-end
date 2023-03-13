package com.lawencon.community.pojo.article;

import com.lawencon.community.pojo.file.PojoFileInsertReq;

public class PojoArticleInsertReq {

	private PojoFileInsertReq photoId;
	private String articleTitle;
	private String articleContent;

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

	public PojoFileInsertReq getPhotoId() {
		return photoId;
	}

	public void setPhotoId(PojoFileInsertReq photoId) {
		this.photoId = photoId;
	}

}
