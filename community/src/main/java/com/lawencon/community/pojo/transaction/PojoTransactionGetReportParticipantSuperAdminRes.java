package com.lawencon.community.pojo.transaction;

public class PojoTransactionGetReportParticipantSuperAdminRes {
	private String memberName;
	private String providerName;
	private String activityType;
	private String itemName;
	private String startDate;
	private Integer totalParticipants;
	
	public String getActivityType() {
		return activityType;
	}
	public String getItemName() {
		return itemName;
	}
	public String getStartDate() {
		return startDate;
	}
	public Integer getTotalParticipants() {
		return totalParticipants;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setTotalParticipants(Integer totalParticipants) {
		this.totalParticipants = totalParticipants;
	}
	public String getMemberName() {
		return memberName;
	}
	public String getProviderName() {
		return providerName;
	}
	
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	
	
	
}
