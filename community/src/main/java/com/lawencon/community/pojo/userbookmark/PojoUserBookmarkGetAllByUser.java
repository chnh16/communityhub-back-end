package com.lawencon.community.pojo.userbookmark;

import java.time.LocalDateTime;
import java.util.List;

import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerGetCountRes;
import com.lawencon.community.pojo.pollinganswer.PojoPollingAnswerRes;
import com.lawencon.community.pojo.pollingchoice.PojoPollingChoiceGetAllRes;
import com.lawencon.community.pojo.postdetail.PojoPostDetailGetAllRes;
import com.lawencon.community.pojo.userlike.PojoPostLikeRes;

public class PojoUserBookmarkGetAllByUser {

	private String id;
	private String userFileId;
	private String fullName;
	private String postTitle;
	private String postContent;
	private String postTypeId;
	private String categoryName;
	private LocalDateTime postedAt;
	private Integer ver;
	private Long likeCount;
	private Integer detailCount;
	private List<PojoPollingChoiceGetAllRes> pollingChoice;
	private List<PojoPostDetailGetAllRes> postDetail;
	private List<String> fileId;
	private List<PojoPollingAnswerGetCountRes> pollingAnswer;
	private PojoPostLikeRes isLiked;
	private PojoPostBookmarkRes isBookmarked;
	private PojoPollingAnswerRes isAnswered;
	private Boolean isPremium;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserFileId() {
		return userFileId;
	}

	public void setUserFileId(String userFileId) {
		this.userFileId = userFileId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public String getPostTypeId() {
		return postTypeId;
	}

	public void setPostTypeId(String postTypeId) {
		this.postTypeId = postTypeId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public LocalDateTime getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(LocalDateTime postedAt) {
		this.postedAt = postedAt;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getDetailCount() {
		return detailCount;
	}

	public void setDetailCount(Integer detailCount) {
		this.detailCount = detailCount;
	}

	public List<PojoPollingChoiceGetAllRes> getPollingChoice() {
		return pollingChoice;
	}

	public void setPollingChoice(List<PojoPollingChoiceGetAllRes> pollingChoice) {
		this.pollingChoice = pollingChoice;
	}

	public List<PojoPostDetailGetAllRes> getPostDetail() {
		return postDetail;
	}

	public void setPostDetail(List<PojoPostDetailGetAllRes> postDetail) {
		this.postDetail = postDetail;
	}

	public List<String> getFileId() {
		return fileId;
	}

	public void setFileId(List<String> fileId) {
		this.fileId = fileId;
	}

	public List<PojoPollingAnswerGetCountRes> getPollingAnswer() {
		return pollingAnswer;
	}

	public void setPollingAnswer(List<PojoPollingAnswerGetCountRes> pollingAnswer) {
		this.pollingAnswer = pollingAnswer;
	}

	public PojoPostLikeRes getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(PojoPostLikeRes isLiked) {
		this.isLiked = isLiked;
	}

	public PojoPostBookmarkRes getIsBookmarked() {
		return isBookmarked;
	}

	public void setIsBookmarked(PojoPostBookmarkRes isBookmarked) {
		this.isBookmarked = isBookmarked;
	}

	public PojoPollingAnswerRes getIsAnswered() {
		return isAnswered;
	}

	public void setIsAnswered(PojoPollingAnswerRes isAnswered) {
		this.isAnswered = isAnswered;
	}

	public Boolean getIsPremium() {
		return isPremium;
	}

	public void setIsPremium(Boolean isPremium) {
		this.isPremium = isPremium;
	}

}
