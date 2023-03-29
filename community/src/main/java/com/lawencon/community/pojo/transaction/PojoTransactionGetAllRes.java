package com.lawencon.community.pojo.transaction;

import java.math.BigDecimal;

public class PojoTransactionGetAllRes {
	private String id;
	private String fileId;
	private String fullName;
	private String itemName;
	private BigDecimal grandTotal;
	private Boolean isApproved;
	private String statusTransaction;
	private Integer ver;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(BigDecimal grandTotal) {
		this.grandTotal = grandTotal;
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
	public String getStatusTransaction() {
		return statusTransaction;
	}
	public void setStatusTransaction(String statusTransaction) {
		this.statusTransaction = statusTransaction;
	}
	
}
