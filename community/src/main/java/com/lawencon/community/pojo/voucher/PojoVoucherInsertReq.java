package com.lawencon.community.pojo.voucher;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PojoVoucherInsertReq {
	private String voucherCode;
	private LocalDateTime expiredDate;
	private BigDecimal amount;
	
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
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
}
