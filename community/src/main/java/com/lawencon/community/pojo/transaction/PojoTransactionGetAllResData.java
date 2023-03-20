package com.lawencon.community.pojo.transaction;

import java.util.List;

public class PojoTransactionGetAllResData {
	private List<PojoTransactionGetAllRes> data;
	private Integer total;
	
	public List<PojoTransactionGetAllRes> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoTransactionGetAllRes> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
