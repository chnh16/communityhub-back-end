package com.lawencon.community.pojo.category;

import java.util.List;

public class PojoCategoryGetAllRes {
	private List<PojoCategoryGetAllResData> data;
	private Integer total;
	
	public List<PojoCategoryGetAllResData> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoCategoryGetAllResData> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
