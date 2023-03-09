package com.lawencon.community.pojo.membership;

import java.math.BigDecimal;

public class PojoMembershipInsertReq {

	private String membershipName;
	private Long duration;
	private BigDecimal amount;

	public String getMembershipName() {
		return membershipName;
	}

	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}

	public Long getDuration() {
		return duration;
	}

	public void setDuration(Long duration) {
		this.duration = duration;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
