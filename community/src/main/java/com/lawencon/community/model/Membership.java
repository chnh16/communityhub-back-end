package com.lawencon.community.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "membership")
public class Membership extends BaseEntity{

	@Column(length = 5, nullable = false)
	private String membershipCode;

	@Column(length = 30, nullable = false)
	private String membershipName;

	@Column (nullable = false)
	private Long duration;
	
	@Column (nullable = false)
	private BigDecimal amount;
	
	@OneToOne
	@JoinColumn(name = "type_product_id", nullable = false)
	private TypeProduct typeProduct;

	public TypeProduct getTypeProduct() {
		return typeProduct;
	}

	public void setTypeProduct(TypeProduct typeProduct) {
		this.typeProduct = typeProduct;
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
