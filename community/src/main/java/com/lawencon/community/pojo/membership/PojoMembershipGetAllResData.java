package com.lawencon.community.pojo.membership;

import java.util.List;

public class PojoMembershipGetAllResData {
	private List<PojoMembershipGetAllRes> data;
	private Integer total;
	public List<PojoMembershipGetAllRes> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoMembershipGetAllRes> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}	
