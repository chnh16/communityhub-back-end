package com.lawencon.community.pojo.event;

import java.util.List;

public class PojoEventGetAllResData {
	private List<PojoEventResGetAll> data;
	private Integer total;
	public List<PojoEventResGetAll> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoEventResGetAll> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
