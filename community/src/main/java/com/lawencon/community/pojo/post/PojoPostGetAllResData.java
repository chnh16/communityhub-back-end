package com.lawencon.community.pojo.post;

import java.util.List;

public class PojoPostGetAllResData {
	private List<PojoPostGetAllRes> data;
	private Integer total;
	
	public List<PojoPostGetAllRes> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoPostGetAllRes> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
