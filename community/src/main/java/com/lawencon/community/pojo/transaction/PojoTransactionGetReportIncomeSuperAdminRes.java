package com.lawencon.community.pojo.transaction;

import java.math.BigDecimal;

public class PojoTransactionGetReportIncomeSuperAdminRes {
	private String fullName;
	private String activityType;
	private BigDecimal totalIncome;
	public String getFullName() {
		return fullName;
	}
	public String getActivityType() {
		return activityType;
	}
	public BigDecimal getTotalIncome() {
		return totalIncome;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public void setTotalIncome(BigDecimal totalIncome) {
		this.totalIncome = totalIncome;
	}
	
}
