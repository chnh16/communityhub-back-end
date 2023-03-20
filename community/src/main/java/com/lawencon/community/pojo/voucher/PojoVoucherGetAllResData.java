package com.lawencon.community.pojo.voucher;

import java.util.List;

public class PojoVoucherGetAllResData {
	private List<PojoVoucherGetAllRes> data;
	private Integer total;
	public List<PojoVoucherGetAllRes> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoVoucherGetAllRes> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
