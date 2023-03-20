package com.lawencon.community.pojo.article;

import java.util.List;

public class PojoArticleGetAllResData {
	private List<PojoArticleResGetAll> data;
	private Integer total;
	public List<PojoArticleResGetAll> getData() {
		return data;
	}
	public Integer getTotal() {
		return total;
	}
	public void setData(List<PojoArticleResGetAll> data) {
		this.data = data;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	
}
