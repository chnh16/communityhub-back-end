package com.lawencon.community.pojo.membership;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PojoMembershipGetAllRes {

	private String id;
	private String membershipCode;
	private String membershipName;
	private Long duration;
	private BigDecimal amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMembershipCode() {
		return membershipCode;
	}

	public void setMembershipCode(String membershipCode) {
		this.membershipCode = membershipCode;
	}

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
