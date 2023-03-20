package com.lawencon.community.pojo.position;

import java.util.List;

public class PojoPosistionGetAllResData {
	private List<PojoPositionGetAllRes> data;
	private Integer total;
	public List<PojoPositionGetAllRes> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoPositionGetAllRes> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
