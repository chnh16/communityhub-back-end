package com.lawencon.community.pojo.transaction;

import java.math.BigDecimal;

public class PojoTransactionGetReportIncomeMemberRes {
	private String activityType;
	private String itemName;
	private BigDecimal totalIncomes;
	public String getActivityType() {
		return activityType;
	}
	public String getItemName() {
		return itemName;
	}
	public BigDecimal getTotalIncomes() {
		return totalIncomes;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setTotalIncomes(BigDecimal totalIncomes) {
		this.totalIncomes = totalIncomes;
	}
	
	
}
