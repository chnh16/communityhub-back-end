package com.lawencon.community.pojo.membership;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PojoMembershipInsertReq {

	private String membershipName;
	private LocalDateTime duration;
	private BigDecimal amount;

	public String getMembershipName() {
		return membershipName;
	}

	public void setMembershipName(String membershipName) {
		this.membershipName = membershipName;
	}

	public LocalDateTime getDuration() {
		return duration;
	}

	public void setDuration(LocalDateTime duration) {
		this.duration = duration;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
