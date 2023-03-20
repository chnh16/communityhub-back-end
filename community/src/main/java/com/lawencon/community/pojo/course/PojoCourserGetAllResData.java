package com.lawencon.community.pojo.course;

import java.util.List;

public class PojoCourserGetAllResData {
	private List<PojoCourseGetAllRes> data;
	private Integer total;
	public List<PojoCourseGetAllRes> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoCourseGetAllRes> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
