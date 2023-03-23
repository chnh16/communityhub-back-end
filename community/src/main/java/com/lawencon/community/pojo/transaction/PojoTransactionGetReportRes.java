package com.lawencon.community.pojo.transaction;

public class PojoTransactionGetReportRes {
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
	
	
}
