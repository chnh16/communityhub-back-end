package com.lawencon.community.pojo.voucher;

import java.time.LocalDateTime;

public class PojoVoucherUpdateReq {
	private Long id;
	private String voucherCode;
	private LocalDateTime expiredDate;
	private Integer amount;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getVoucherCode() {
		return voucherCode;
	}
	public void setVoucherCode(String voucherCode) {
		this.voucherCode = voucherCode;
	}
	public LocalDateTime getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(LocalDateTime expiredDate) {
		this.expiredDate = expiredDate;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
