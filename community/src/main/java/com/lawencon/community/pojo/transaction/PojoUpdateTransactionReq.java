package com.lawencon.community.pojo.transaction;

public class PojoUpdateTransactionReq {
	private String id;
	private Boolean isApproved;

	private String statusTransactionId;
	private Integer ver;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public Integer getVer() {
		return ver;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public String getStatusTransactionId() {
		return statusTransactionId;
	}

	public void setStatusTransactionId(String statusTransactionId) {
		this.statusTransactionId = statusTransactionId;
	}

}
